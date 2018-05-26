package com.himanshurawat.testingwatson.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity
import org.jetbrains.anko.find

class InsightRecyclerAdapter(var insightList:List<PersonalityInsightEntity>,var listener: OnItemClickListener): RecyclerView.Adapter<InsightRecyclerAdapter.InsightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsightViewHolder {

        return InsightViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.insight_recycler_item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return insightList.size
    }

    override fun onBindViewHolder(holder: InsightViewHolder, position: Int) {
        val insight = insightList[position]

        holder.idTextView.text = insight.id.toString()
        holder.timeTextView.text = insight.timestamp.toString()
        holder.inputTextView.text = insight.userInput
        holder.outputTextView.text = insight.watsonOutput
        holder.bind(insight,listener)

    }


    class InsightViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var idTextView = itemView.findViewById<TextView>(R.id.insight_id_text_view)
        var timeTextView = itemView.findViewById<TextView>(R.id.insight_time_text_view)
        var inputTextView = itemView.findViewById<TextView>(R.id.insight_input_text_view)
        var outputTextView = itemView.findViewById<TextView>(R.id.insight_output_text_view)

        fun bind(insight: PersonalityInsightEntity,listener: OnItemClickListener){
            itemView.setOnClickListener {
                listener.onItemClicked(insight)
            }
        }


    }

    fun addList(insightList: List<PersonalityInsightEntity>){
        this.insightList = insightList
        notifyDataSetChanged()
    }



    interface OnItemClickListener{

        fun onItemClicked(insight: PersonalityInsightEntity)

    }


}