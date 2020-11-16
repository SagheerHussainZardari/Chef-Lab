package com.sagheerhussainzardari.cheflab.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.sagheerhussainzardari.cheflab.MainActivity
import com.sagheerhussainzardari.cheflab.R
import com.sagheerhussainzardari.cheflab.toastlong
import com.sagheerhussainzardari.cheflab.toastshort


class ScanIngredentsFragment : Fragment() {
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


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200 && data != null) {
            Toast.makeText(context, "got image", Toast.LENGTH_SHORT).show()
//            iv_scanned_img.setImageBitmap(data.extras?.get("data") as Bitmap)

            val image = FirebaseVisionImage.fromBitmap(data.extras?.get("data") as Bitmap)

            val labeler = FirebaseVision.getInstance().onDeviceImageLabeler

            labeler.processImage(image)
                .addOnSuccessListener { labels ->
                    var lablesList = ArrayList<String>()

                    for (label in labels) {
                        val text = label.text
                        val entityId = label.entityId
                        val confidence = label.confidence
                        lablesList.add("$text")
                    }


//                    lablesList.add("Tea")

                    context?.toastlong(lablesList.toString())

                    HomeFragment.currentIngredents = lablesList


                    if (lablesList.size == 0) {
                        context?.toastshort("Nothing Scanned Please ReScan")
                    } else {
                        (activity as MainActivity).openMatchingDishes()
                    }


//                    tv_result.text = lables.toString()


//                    rv_scanedResult.setHasFixedSize(true)
//                    rv_scanedResult.layoutManager = GridLayoutManager(context, 2)
//                    rv_scanedResult.adapter =
//                        DishesScannedAdapter(requireContext(), lables, HomeFragment())


                }
                .addOnFailureListener { e ->
//                    tv_result.text = e.localizedMessage
                }

        }
    }

}