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
import kotlin.math.log


class Fragment2(val results: ArrayList<Result>) : Fragment() {
    var itemAdapter: ItemAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2, container, false)




        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = GridLayoutManager(this.context, 2)

        var onItemClickListener = object : View.OnClickListener {
            override fun onClick(view: View?) {
                val position: Int = view?.tag.toString().toInt()

                val url = results.get(position).url

                val intent = Intent(activity, ImageViewActivity::class.java);

                intent.putExtra("URL", url)
                intent.putExtra("TYPE", "SAVE")


                startActivity(intent)

                itemAdapter?.notifyDataSetChanged()
            }
        }





        itemAdapter = ItemAdapter(results, onItemClickListener)
        recyclerView.setAdapter(itemAdapter)

        return view
    }


}