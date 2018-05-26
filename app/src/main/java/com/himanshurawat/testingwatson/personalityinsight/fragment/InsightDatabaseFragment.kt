package com.himanshurawat.testingwatson.personalityinsight.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.adapter.InsightRecyclerAdapter
import com.himanshurawat.testingwatson.database.Database
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity
import com.himanshurawat.testingwatson.utils.Constant
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.uiThread

/**
 * Created by andro on 24-05-2018.
 */
class InsightDatabaseFragment: Fragment(), InsightRecyclerAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: InsightRecyclerAdapter
    private lateinit var database: Database

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_insight_database,container,false)
        database = Database.getInstance(act)

        recyclerView = view.findViewById(R.id.fragment_insight_database_recycler_view)
        recyclerAdapter = InsightRecyclerAdapter(arrayListOf(),this)

        this.doAsync {
            val list = database.getPersonalityInsightDao().allInsights()
            uiThread {
                recyclerAdapter.addList(list)
            }
        }
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)

        return view
    }


    override fun onItemClicked(insight: PersonalityInsightEntity) {
        if(insight.watsonOutput == Constant.AVAILABLE){

            val bundle = Bundle()
            bundle.putString(Constant.PI_RESPONSE,insight.watsonOutputData)
            val fragment = InsightViewFragment()
            fragment.arguments = bundle
            act.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.personality_insight_frame_layout, fragment)
                    .commit()
        }

    }

}