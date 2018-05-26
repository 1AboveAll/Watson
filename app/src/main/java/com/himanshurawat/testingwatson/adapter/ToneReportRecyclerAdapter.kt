package com.himanshurawat.testingwatson.adapter

import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity
import com.himanshurawat.testingwatson.database.entity.ToneAnalyzerEntity

class ToneReportRecyclerAdapter(var reportList: List<ToneAnalyzerEntity>,var listener: OnItemClickListener):
        RecyclerView.Adapter<ToneReportRecyclerAdapter.ToneReportViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToneReportViewHolder {

        return ToneReportViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.insight_recycler_item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ToneReportViewHolder, position: Int) {
        val tone = reportList[position]
        holder.idTextView.text = tone.id.toString()
        holder.timeTextView.text = tone.timestamp.toString()
        holder.inputTextView.text = tone.userInput
        holder.outputTextView.text = tone.watsonOutput
        holder.bind(tone,listener)
    }


    class ToneReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var idTextView = itemView.findViewById<TextView>(R.id.insight_id_text_view)
        var timeTextView = itemView.findViewById<TextView>(R.id.insight_time_text_view)
        var inputTextView = itemView.findViewById<TextView>(R.id.insight_input_text_view)
        var outputTextView = itemView.findViewById<TextView>(R.id.insight_output_text_view)

        fun bind(insight: ToneAnalyzerEntity, listener: OnItemClickListener){
            itemView.setOnClickListener {
                listener.onItemClicked(insight)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(tone:ToneAnalyzerEntity)
    }

    fun addList(tone :List<ToneAnalyzerEntity>){
        this.reportList = tone
        notifyDataSetChanged()
    }



}

