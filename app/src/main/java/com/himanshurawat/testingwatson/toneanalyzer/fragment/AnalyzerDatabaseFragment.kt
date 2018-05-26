package com.himanshurawat.testingwatson.toneanalyzer.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.adapter.ToneReportRecyclerAdapter
import com.himanshurawat.testingwatson.database.Database
import com.himanshurawat.testingwatson.database.entity.ToneAnalyzerEntity
import com.himanshurawat.testingwatson.utils.Constant
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.uiThread

class AnalyzerDatabaseFragment: Fragment(),ToneReportRecyclerAdapter.OnItemClickListener {
    override fun onItemClicked(tone: ToneAnalyzerEntity) {
        if(tone.watsonOutput == Constant.AVAILABLE){

            val bundle = Bundle()
            bundle.putString(Constant.TA_RESPONSE,tone.watsonOutputData)
            val fragment = AnalyzerViewFragment()
            fragment.arguments = bundle
            act.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.tone_analyzer_frame_layout, fragment)
                    .commit()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: ToneReportRecyclerAdapter
    private lateinit var database: Database

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_analyzer_database,container,false)
        database = Database.getInstance(act)

        recyclerView = view.findViewById(R.id.fragment_analyzer_database_recycler_view)
        recyclerAdapter = ToneReportRecyclerAdapter(arrayListOf(),this)

        this.doAsync {
            val list = database.getToneAnalyzerDao().allTones()
            uiThread {
                recyclerAdapter.addList(list)
            }
        }
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)

        return view
    }


}