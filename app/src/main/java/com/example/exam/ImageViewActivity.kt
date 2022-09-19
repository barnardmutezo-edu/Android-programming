package com.example.exam

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.nio.file.Files.delete


class ImageViewActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btn: Button
    private lateinit var data: DBManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_view)

        imageView = findViewById<ImageView>(R.id.enlarged_img)
        btn = findViewById(R.id.type_btn)


        val url: String = intent.getStringExtra("URL").toString()
        val typeOfBtn: String = intent.getStringExtra("TYPE").toString()

        Picasso.get().load(url).into(imageView);

        data = DBManager(this)


        if (typeOfBtn == "SAVE") btn.text = "SAVE" else btn.text = "DELETE"



        btn.setOnClickListener {

            if (typeOfBtn == "SAVE") save(url) else delete(url)

        }

    }

    private fun delete(url: String) {
        Log.i("DELETETED ", "deleted")

        data.deleteEntry(url)

    }

    private fun save(url: String) {

        data.add(url)

        Log.i("IN save size; ", data.getData().size.toString())

    }


}