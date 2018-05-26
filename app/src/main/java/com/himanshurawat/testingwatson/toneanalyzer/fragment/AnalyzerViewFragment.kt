package com.himanshurawat.testingwatson.toneanalyzer.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.adapter.ToneRecyclerAdapter
import com.himanshurawat.testingwatson.utils.Constant
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis


class AnalyzerViewFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: ToneRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =layoutInflater.inflate(R.layout.fragment_analyzer_view,container,false)
        recyclerView = view.findViewById(R.id.fragment_analyzer_view_recycler_view)
        recyclerAdapter = ToneRecyclerAdapter(arrayListOf())
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)

        val bundle = arguments
        if(bundle != null && bundle.containsKey(Constant.TA_RESPONSE)){
            val toneAnalysis = Gson().fromJson(bundle.getString(Constant.TA_RESPONSE),ToneAnalysis::class.java)
            createUI(toneAnalysis)
        }

        return view
    }

    private fun createUI(toneAnalysis: ToneAnalysis?) {
        if(toneAnalysis != null){
            recyclerAdapter.addList(toneAnalysis.documentTone.tones)
        }
    }

}