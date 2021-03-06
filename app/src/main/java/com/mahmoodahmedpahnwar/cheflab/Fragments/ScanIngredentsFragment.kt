package com.mahmoodahmedpahnwar.cheflab.Fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.mahmoodahmedpahnwar.cheflab.Adapters.ListScannedIngredentsAdapter
import com.mahmoodahmedpahnwar.cheflab.MainActivity
import com.mahmoodahmedpahnwar.cheflab.Models.IngredentsModel
import com.mahmoodahmedpahnwar.cheflab.R
import com.mahmoodahmedpahnwar.cheflab.toastlong
import com.mahmoodahmedpahnwar.cheflab.toastshort
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



        callCamera()


        btn_ScanMore.setOnClickListener {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //
//            startActivityForResult(cameraIntent, 200)
            callCamera()
        }

        btn_ScanFinish.setOnClickListener {

            context?.toastlong(lablesList.toString())
            if (lablesList.size == 0) {
                context?.toastshort("Select Any One Of The Ingredients")
            } else {
                (activity as MainActivity).openMatchingDishes()
            }
        }

        btn_ClearList.setOnClickListener {
            lablesList.clear()
            labelListModel.clear()
            rv_scanedResult
            rv_scanedResult.setHasFixedSize(true)
            rv_scanedResult.layoutManager = GridLayoutManager(context, 1)
            rv_scanedResult.adapter =
                ListScannedIngredentsAdapter(requireContext(), labelListModel, this)

            context?.toastshort("List Cleared")
        }

    }

    private fun callCamera() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 200)

            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    201
                )

                callCamera()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200 && data != null) {

//            iv_scanned_img.setImageBitmap(data.extras?.get("data") as Bitmap)

            val image = FirebaseVisionImage.fromBitmap(data.extras?.get("data") as Bitmap)

            val labeler = FirebaseVision.getInstance().cloudImageLabeler

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
                    rv_scanedResult.layoutManager = GridLayoutManager(context, 1)
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