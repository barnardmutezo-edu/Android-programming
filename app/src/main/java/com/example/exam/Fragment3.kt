package com.example.exam

import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Fragment3(val dataBase: DBManager) : Fragment() {
    var itemAdapter: ItemAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment3, container, false)

        val data = dataBase.getData();



        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_viewDB)

        recyclerView.layoutManager = GridLayoutManager(this.context, 2)


        var onItemClickListener = object : View.OnClickListener {
            override fun onClick(view: View?) {
                val position: Int = view?.tag.toString().toInt()

                val url = data.get(position).url

                val intent = Intent(activity, ImageViewActivity::class.java);

                intent.putExtra("URL", url)
                intent.putExtra("TYPE", "DELETE")

                startActivity(intent)

                //  val position: Int = view?.tag.toString().toInt()
                //studentsInfo.removeAt(position)


                /* if (view != null) {
                     zoomImageFromThumb(fragView,view )
                 }*/
                itemAdapter?.notifyDataSetChanged()
            }
        }




        Log.i("IN DB size; ", data.size.toString())



        itemAdapter = ItemAdapter(data, onItemClickListener)
        recyclerView.setAdapter(itemAdapter)

        return view
    }


}