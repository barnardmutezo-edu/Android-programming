package com.example.exam

import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import okhttp3.*


import java.io.ByteArrayOutputStream

import kotlin.concurrent.thread

import android.graphics.Bitmap
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import okhttp3.RequestBody

import okhttp3.MultipartBody

import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class Fragment1(val results: ArrayList<Result>) : Fragment() {

    lateinit var image: CropImageView2

    var imageUri: String? = null

    public var actualCropRect: Rect? = null


    interface OnImageSizeChangedListener {
        fun onImageSizeChanged(rec: Rect)
    }


    private fun getURLForResource(resourceId: Int): String? {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://" + android.R::class.java.getPackage().name + "/" + resourceId)
            .toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var uploadImgBtn: Button? = null

        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()

        var view = inflater.inflate(R.layout.fragment1, container, false)


        uploadImgBtn = view.findViewById(R.id.upload_img_btn);
        uploadImgBtn.setOnClickListener { fethData() }

        image = view.findViewById<CropImageView2>(R.id.image)

        image.setListeners(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var i = Intent()
                Log.i(Globals.TAG, "seleckt image box")

                i.action = Intent.ACTION_GET_CONTENT
                i.type = "*/*"

                startForResult.launch(i)
            }
        }, object : OnImageSizeChangedListener {
            override fun onImageSizeChanged(rec: Rect) {
                actualCropRect = rec

                Log.i(Globals.TAG, imageUri!!)

            }

        })

        return view
    }


    private fun fethData() {
        results.clear()

        val imgOutputStream = ByteArrayOutputStream()

       // if (imageUri.isNullOrBlank()) return;

        val bitmap: Bitmap = this.getBitmap()

        bitmap.compress(Bitmap.CompressFormat.PNG, 50, imgOutputStream)
        val mediaType = MediaType.parse("image/png")
        val req = MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(
            "image",
            imageUri.toString(),
            RequestBody.create(mediaType, imgOutputStream.toByteArray())
        ).build()
        val urlBuilder =
            HttpUrl.parse("http://api-edu.gtl.ai/api/v1/imagesearch/upload")!!.newBuilder()
        val url = urlBuilder.build().toString()
        val client = OkHttpClient()


        thread {
            val request = Request.Builder().url(url).post(req).build()
            val response = client.newCall(request).execute()
            val responseBody = response.body()?.string()


            val imageUrl = responseBody.toString()
            Log.i("Res: ", imageUrl)

           if (imageUrl.isBlank()) return@thread;

            val page =
                "http://api-edu.gtl.ai/api/v1/imagesearch/bing?url=${imageUrl}";
            try {
                val res = URL(page).readText()

                val json = JSONArray(res)


                for (index in 0 until json.length()) {

                    val rawURL = (json.get(index) as JSONObject).getString("image_link")

                    val pngURL = rawURL.plus(".png")


                    results.add(
                        Result(
                            pngURL,
                            -1, -1, -1, -1, -1, -1
                        )
                    )
                    Log.i(Globals.TAG, url)

                }

                Log.i(Globals.TAG, "ALL GOOD")


            } catch (ex: Exception) {
                Log.i(Globals.TAG, "ERROR MESSAGE: ")

                Log.i(Globals.TAG, ex.message.toString())

            }


        }


    }

    private fun getBitmap(): Bitmap {
      val bitmap:  Bitmap = if (imageUri != null) getBitmap(
            this.requireContext(),
            null,
            imageUri,
            ::UriToBitmap
        ) else getBitmap(this.requireContext(), R.drawable.flower, null, ::VectorDrawableToBitmap)

        return bitmap;
    }

    var startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            imageUri = it.data?.data.toString()


            var bitmap_image = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)

            image.layoutParams = image.layoutParams.apply {

                width = bitmap_image.width
                height = bitmap_image.height
            }

            image.setImageBitmap(bitmap_image)
            image.background = BitmapDrawable(resources, bitmap_image)
        }


}

