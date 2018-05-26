package com.himanshurawat.testingwatson.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.himanshurawat.testingwatson.R
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore

class ToneRecyclerAdapter(var toneList: List<ToneScore>) : RecyclerView.Adapter<ToneRecyclerAdapter.ToneViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToneViewHolder {

        return ToneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.analyzer_document_item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return  toneList.size
    }

    override fun onBindViewHolder(holder: ToneViewHolder, position: Int) {
        val tone = toneList[position]
        holder.nameTextView.text = tone.toneName
        val score = (tone.score * 100).toInt()
        holder.valueTextView.text = "$score %"
    }


    class ToneViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameTextView = itemView.findViewById<TextView>(R.id.analyzer_document_name_text_view)
        val valueTextView = itemView.findViewById<TextView>(R.id.analyzer_document_value_text_view)
    }

    fun addList(list: List<ToneScore>){
        this.toneList = list
        notifyDataSetChanged()
    }


}
