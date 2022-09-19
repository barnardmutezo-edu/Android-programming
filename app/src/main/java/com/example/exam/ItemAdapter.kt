package com.example.exam

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.net.URL
import kotlin.math.log
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import com.squareup.picasso.Picasso


class ItemAdapter(
    val thumbnail: ArrayList<Result>,
     val onItemClickListener: View.OnClickListener,
    //  val onItemEditListener: View.OnClickListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var thumbNailIMG: ImageView = view.findViewById(R.id.thumbImageView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_view,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentIMG: Result = thumbnail.get(position)
        val imageUri = currentIMG.url

        Picasso.get().load(imageUri).into(holder.thumbNailIMG);

       holder.itemView.setTag(position)

        holder.thumbNailIMG.setOnClickListener{onItemClickListener.onClick(holder.itemView)}


    }

    override fun getItemCount(): Int {
        return thumbnail.size
    }

}