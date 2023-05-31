package com.crickex.india.crickex.btcwithmvvm.ui.login.ui


import android.content.pm.PackageManager
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.ActivityAccessGalleryRvscreenBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.AdapterRVImage
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.images.GalleryImagesModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppUtils
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.GlideHelper


class AccessGalleryRVScreen : AppCompatActivity() {
    lateinit var binding: ActivityAccessGalleryRvscreenBinding
    var listImages = ArrayList<GalleryImagesModel>()
    var IMAGE_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccessGalleryRvscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = GridLayoutManager(this, 3)
        binding.imageRV.layoutManager = layoutManager
        if (ContextCompat.checkSelfPermission(
                this@AccessGalleryRVScreen,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,

                    ),
                IMAGE_CODE
            )
        } else {
            listImages = AppUtils.fetchImages(this)
            GlideHelper.LoadImageSimple(
                this@AccessGalleryRVScreen,
                listImages[0].images,
                R.drawable.background_profile,
                binding.selectedImage
            )
            val adapterRVImage =
                AdapterRVImage(this, listImages, object : AdapterRVImage.CallGridClick {
                    override fun onClick(position: Int) {

                        GlideHelper.LoadImageSimple(
                            this@AccessGalleryRVScreen,
                            listImages[position].images,
                            R.drawable.background_profile,
                            binding.selectedImage
                        )
                    }

                })
            binding.imageRV.adapter = adapterRVImage

        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            IMAGE_CODE ->                 // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listImages = AppUtils.fetchImages(this)
                    GlideHelper.LoadImageSimple(
                        this,
                        listImages[0].images,
                        R.drawable.background_profile,
                        binding.selectedImage
                    )
                    val adapterRVImage =
                        AdapterRVImage(this, listImages, object : AdapterRVImage.CallGridClick {
                            override fun onClick(position: Int) {
                                GlideHelper.LoadImageSimple(
                                    this@AccessGalleryRVScreen,
                                    listImages[position].images,
                                    R.drawable.background_profile,
                                    binding.selectedImage
                                )
                            }

                        })
                    binding.imageRV.adapter = adapterRVImage

                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
        }
    }
}