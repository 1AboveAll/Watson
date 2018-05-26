package com.himanshurawat.testingwatson.toneanalyzer.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.database.Database
import com.himanshurawat.testingwatson.database.entity.ToneAnalyzerEntity
import com.himanshurawat.testingwatson.utils.Constant
import com.ibm.watson.developer_cloud.http.ServiceCallback
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast
import java.lang.Exception


class AnalyzerFragment: Fragment(), View.OnClickListener {


    private lateinit var editText: EditText
    private lateinit var analyzeButton: Button
    private lateinit var database: Database

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_analyzer,container,false)

        editText = view.findViewById(R.id.fragment_analyzer_edit_text)
        analyzeButton = view.findViewById(R.id.fragment_analyzer_analyze_button)
        database = Database.getInstance(act)

        analyzeButton.setOnClickListener(this)


        return view
    }

    override fun onClick(v: View?) {
       if(v != null && v.id == R.id.fragment_analyzer_analyze_button){
           if(editText.length() > 600){
               if(isNetworkAvailable()){
                   val service = ToneAnalyzer("2017-09-21",Constant.TA_USERNAME,Constant.TA_PASS)
                   val toneOption = ToneOptions
                           .Builder()
                           .text(editText.text.trim().toString())
                           .build()
                   service.tone(toneOption).enqueue(object: ServiceCallback<ToneAnalysis>{
                       override fun onFailure(e: Exception?) {

                           doAsync {
                               val toneEntity = ToneAnalyzerEntity(0, System.currentTimeMillis() / 1000,
                                       editText.text.trim().toString(), Constant.API_FAILURE, "")

                               database.getToneAnalyzerDao().insertTone(toneEntity)
                               Log.i("Personality", "Added to Database")
                           }

                       }

                       override fun onResponse(response: ToneAnalysis?) {

                           val bundle = Bundle()
                           val fragment = AnalyzerViewFragment()
                           bundle.putString(Constant.TA_RESPONSE, response.toString())
                           fragment.arguments = bundle

                           doAsync {
                               val toneEntity = ToneAnalyzerEntity(0, System.currentTimeMillis() / 1000,
                                       editText.text.trim().toString(), Constant.AVAILABLE, response.toString())

                               database.getToneAnalyzerDao().insertTone(toneEntity)
                               Log.i("Personality", "Added to Database")
                           }

                           act.supportFragmentManager
                                   .beginTransaction()
                                   .replace(R.id.tone_analyzer_frame_layout, fragment)
                                   .commit()


                       }

                   })

               }else{
                   toast("No Internet Detected")
                   doAsync {
                       val toneEntity = ToneAnalyzerEntity(0, System.currentTimeMillis() / 1000,
                               Constant.NO_NETWORK, Constant.NOT_PROCESSED, "")

                       database.getToneAnalyzerDao().insertTone(toneEntity)
                       Log.i("Personality", "Added to Database")
                   }
               }


           }else{
               editText.error = "Insufficient Information"
               toast("Add More Content")
               doAsync {
                   val toneEntity = ToneAnalyzerEntity(0, System.currentTimeMillis() / 1000,
                           Constant.INSUFFICIENT_DATA, Constant.NOT_PROCESSED, "")

                   database.getToneAnalyzerDao().insertTone(toneEntity)
                   Log.i("Personality", "Added to Database")
               }
           }
       }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = act.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}