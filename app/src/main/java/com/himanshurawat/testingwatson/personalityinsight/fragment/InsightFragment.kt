package com.himanshurawat.testingwatson.personalityinsight.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity
import com.himanshurawat.testingwatson.utils.Constant
import com.ibm.watson.developer_cloud.http.ServiceCallback
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
import java.lang.Exception
import android.net.ConnectivityManager
import org.jetbrains.anko.support.v4.toast


/**
 * Created by andro on 24-05-2018.
 */
class InsightFragment: Fragment() {


    private lateinit var database: com.himanshurawat.testingwatson.database.Database

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_insight,container,false)

        val service = PersonalityInsights("2017-10-13",Constant.PI_USERNAME,Constant.PI_PASS)
        database = com.himanshurawat.testingwatson.database.Database.getInstance(act)


        val analyzeButton = view.findViewById<Button>(R.id.fragment_insight_analyze_button)
        val editText = view.findViewById<EditText>(R.id.fragment_insight_edit_text)
        analyzeButton.setOnClickListener {

            if(editText.length() > 1000) {

                val options = ProfileOptions
                        .Builder()
                        .text(editText.text.trim().toString())
                        .build()
                if (isNetworkAvailable()) {
                    toast("Analyzing Content :D")

                    service.profile(options).enqueue(object : ServiceCallback<Profile> {
                        override fun onFailure(e: Exception?) {
                            doAsync {
                                val insightEntity = PersonalityInsightEntity(0, System.currentTimeMillis() / 1000,
                                        editText.text.trim().toString(), Constant.API_FAILURE, "")
                                database.getPersonalityInsightDao().insertInsight(insightEntity)
                                Log.i("Personality", "Added to Database, Data Not Available")

                            }
                            toast("Something Bad Happened")

                        }

                        override fun onResponse(response: Profile?) {
                            if (response != null) {

                                val bundle = Bundle()
                                bundle.putString(Constant.PI_RESPONSE, response.toString())
                                bundle.putString(Constant.PI_TEXT, editText.text.trim().toString())

                                doAsync {
                                    val insightEntity = PersonalityInsightEntity(0, System.currentTimeMillis() / 1000,
                                            editText.text.trim().toString(), Constant.AVAILABLE, response.toString())
                                    database.getPersonalityInsightDao().insertInsight(insightEntity)
                                    Log.i("Personality", "Added to Database")
                                }

                                val fragment = InsightViewFragment()
                                fragment.arguments = bundle

                                act.supportFragmentManager
                                        .beginTransaction()
                                        .replace(R.id.personality_insight_frame_layout, fragment)
                                        .commit()

                            }
                        }

                    })
                } else {
                    doAsync {
                        val insightEntity = PersonalityInsightEntity(0, System.currentTimeMillis() / 1000,
                                editText.text.trim().toString(), Constant.NO_NETWORK, "")
                        database.getPersonalityInsightDao().insertInsight(insightEntity)
                        Log.i("Personality", "Added to Database, Data Not Available")
                    }

                    toast("Connect to the Internet")

                }
            }else{
                editText.error = "Add More Text to Analyze Personality"
                toast("Insufficient Content for Analysis")
                doAsync {
                    val insightEntity = PersonalityInsightEntity(0, System.currentTimeMillis() / 1000,
                            Constant.INSUFFICIENT_DATA, Constant.NOT_PROCESSED, "")
                    database.getPersonalityInsightDao().insertInsight(insightEntity)
                    Log.i("Personality", "Added to Database, Data Not Available")
                }

            }
        }

        return view
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = act.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


}