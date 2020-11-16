package com.sagheerhussainzardari.cheflab.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.sagheerhussainzardari.cheflab.Adapters.ListScannedIngredentsAdapter
import com.sagheerhussainzardari.cheflab.MainActivity
import com.sagheerhussainzardari.cheflab.Models.IngredentsModel
import com.sagheerhussainzardari.cheflab.R
import com.sagheerhussainzardari.cheflab.toastlong
import com.sagheerhussainzardari.cheflab.toastshort
import kotlinx.android.synthetic.main.fragment_scan_ingredents.*


class ScanIngredentsFragment : Fragment() {

    companion object {
        var lablesList = ArrayList<String>()
        var labelListModel = ArrayList<IngredentsModel>()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_ingredents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 200)


        btn_ScanMore.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 200)

        }

        btn_ScanFinish.setOnClickListener {

            context?.toastlong(lablesList.toString())
            if (lablesList.size == 0) {
                context?.toastshort("Nothing Scanned Please ReScan")
            } else {
                (activity as MainActivity).openMatchingDishes()
            }
        }

        btn_ClearList.setOnClickListener {
            lablesList.clear()
            labelListModel.clear()
            rv_scanedResult
            rv_scanedResult.setHasFixedSize(true)
            rv_scanedResult.layoutManager = GridLayoutManager(context, 2)
            rv_scanedResult.adapter =
                ListScannedIngredentsAdapter(requireContext(), labelListModel, this)

            context?.toastshort("List Cleared")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200 && data != null) {

//            iv_scanned_img.setImageBitmap(data.extras?.get("data") as Bitmap)

            val image = FirebaseVisionImage.fromBitmap(data.extras?.get("data") as Bitmap)

            val labeler = FirebaseVision.getInstance().onDeviceImageLabeler

            labeler.processImage(image)
                .addOnSuccessListener { labels ->

                    for (label in labels) {
                        val text = label.text
                        val entityId = label.entityId
                        val confidence = label.confidence

                        if (confidence > 0.60) {
                            if (!(labelListModel.contains(IngredentsModel("$text")))) {
//                                lablesList.add("$text")
                                labelListModel.add(IngredentsModel("$text"))
                            }
                        }
                    }



                    HomeFragment.currentIngredents = lablesList



                    rv_scanedResult
                    rv_scanedResult.setHasFixedSize(true)
                    rv_scanedResult.layoutManager = GridLayoutManager(context, 2)
                    rv_scanedResult.adapter =
                        ListScannedIngredentsAdapter(requireContext(), labelListModel, this)

                }
                .addOnFailureListener { e ->
                    context?.toastlong(e.localizedMessage)
                }

        }
    }

    fun onItemChecked(ingredentName: String) {
        if (lablesList.contains(ingredentName)) {
            lablesList.remove(ingredentName)
        } else {
            lablesList.add(ingredentName)
        }
    }

}