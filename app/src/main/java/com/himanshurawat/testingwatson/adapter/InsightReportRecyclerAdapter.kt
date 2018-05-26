package com.himanshurawat.testingwatson.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait

class InsightReportRecyclerAdapter(var reportList: List<Trait>) : RecyclerView.Adapter<InsightReportRecyclerAdapter.InsightReportViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsightReportViewHolder {

        return InsightReportViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.insight_report_item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: InsightReportViewHolder, position: Int) {
        val report = reportList[position]
        holder.nameTextView.text = report.name
        val value = (report.percentile * 100).toInt()
        holder.valueTextView.text = "$value %"
    }


    class InsightReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nameTextView = itemView.findViewById<TextView>(R.id.insight_report_name_text_view)
        var valueTextView = itemView.findViewById<TextView>(R.id.insight_report_value_text_view)
    }

    fun addList(reportList: List<Trait>){
        this.reportList = reportList
        notifyDataSetChanged()
    }






}