package com.himanshurawat.testingwatson.personalityinsight.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.adapter.InsightReportRecyclerAdapter
import com.himanshurawat.testingwatson.database.Database
import com.himanshurawat.testingwatson.utils.Constant
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile
import org.jetbrains.anko.support.v4.act

/**
 * Created by andro on 24-05-2018.
 */
class InsightViewFragment: Fragment() {

    private lateinit var linearLayoutMain: LinearLayout
    private lateinit var wordCountTextView: TextView
    private lateinit var processedLanguage: TextView
    private lateinit var profileString: String
    private lateinit var text: String
    private lateinit var database: Database
    private var profile: Profile? = null

    private lateinit var needsRecyclerView: RecyclerView
    private lateinit var personalityRecyclerView: RecyclerView
    private lateinit var valuesRecyclerView: RecyclerView

    private lateinit var needsRecyclerAdapter: InsightReportRecyclerAdapter
    private lateinit var personalityRecyclerAdapter: InsightReportRecyclerAdapter
    private lateinit var valuesRecyclerAdapter: InsightReportRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_insight_view,container,false)

        wordCountTextView = view.findViewById(R.id.fragment_insight_view_word_count_text_view)
        processedLanguage = view.findViewById(R.id.fragment_insight_view_processed_language_text_view)
        needsRecyclerView = view.findViewById(R.id.fragment_insight_needs_recycler_view)
        personalityRecyclerView = view.findViewById(R.id.fragment_insight_personality_recycler_view)
        valuesRecyclerView = view.findViewById(R.id.fragment_insight_values_recycler_view)

        needsRecyclerAdapter = InsightReportRecyclerAdapter(arrayListOf())
        personalityRecyclerAdapter = InsightReportRecyclerAdapter(arrayListOf())
        valuesRecyclerAdapter = InsightReportRecyclerAdapter(arrayListOf())

        needsRecyclerView.adapter = needsRecyclerAdapter
        personalityRecyclerView.adapter = personalityRecyclerAdapter
        valuesRecyclerView.adapter = valuesRecyclerAdapter

        needsRecyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
        personalityRecyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
        valuesRecyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)


        val bundle = arguments
        if(bundle != null && bundle.containsKey(Constant.PI_RESPONSE)) {
            profile = Gson().fromJson(bundle.getString(Constant.PI_RESPONSE), Profile::class.java)
        }

        database = Database.getInstance(act)

        profileString = bundle?.getString(Constant.PI_RESPONSE).toString()
        text = bundle?.getString(Constant.PI_TEXT).toString()

        linearLayoutMain = view.findViewById(R.id.fragment_insight_view_linear_layout)

        createUI(profile)
        return view
    }

    private fun createUI(response: Profile?) {
        if(response != null) {
            val factor = act.resources.displayMetrics.density
            //Params
            val cardViewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val parentTextViewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val childrenTextViewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val linearLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

            val margin8dp = pxFromDp(factor, 8).toInt()
            val margin16dp = pxFromDp(factor, 16).toInt()
            cardViewParams.setMargins(margin8dp, margin8dp, margin8dp, margin8dp)
            childrenTextViewParams.setMargins(margin16dp, margin8dp, margin16dp, margin8dp)
            parentTextViewParams.setMargins(margin8dp, margin8dp, margin8dp, margin8dp)

            wordCountTextView.text = "${response.wordCount}"
            processedLanguage.text = response.processedLanguage

            needsRecyclerAdapter.addList(response.needs)
            personalityRecyclerAdapter.addList(response.personality)
            valuesRecyclerAdapter.addList(response.values)



//            for (item in response.needs) {
//                val cardView = CardView(act)
//                val linearLayout = LinearLayout(act)
//
//                cardView.layoutParams = cardViewParams
//                linearLayout.layoutParams = linearLayoutParams
//                linearLayout.orientation = LinearLayout.VERTICAL
//
//                cardView.addView(linearLayout)
//
//                val textView = TextView(act)
//                textView.layoutParams = parentTextViewParams
//                val percentile = (item.percentile * 100).toInt().toString()
//                textView.text = "${item.name} - $percentile% "
//                linearLayout.addView(textView)
//
//                linearLayoutMain.addView(cardView)
//
//            }
//
//
//            for (item in response.personality) {
//                val cardView = CardView(act)
//                val linearLayout = LinearLayout(act)
//
//                cardView.layoutParams = cardViewParams
//                linearLayout.layoutParams = linearLayoutParams
//                linearLayout.orientation = LinearLayout.VERTICAL
//
//                cardView.addView(linearLayout)
//
//                val textView = TextView(act)
//                val percentile = (item.percentile * 100).toInt().toString()
//                textView.text = "${item.name} - $percentile%"
//                linearLayout.addView(textView)
//
//                if (item.children != null) {
//                    if (item.children.size > 0) {
//                        for (child in item.children) {
//                            val text = TextView(act)
//                            Log.i("Personality", child.name)
//                            text.layoutParams = childrenTextViewParams
//                            val percent = (child.percentile * 100).toInt().toString()
//                            text.text = "${child.name} - $percent%"
//                            linearLayout.addView(text)
//                        }
//                    }
//                }
//
//                linearLayoutMain.addView(cardView)
//            }
//
//            for (item in response.values) {
//                val cardView = CardView(act)
//                val linearLayout = LinearLayout(act)
//
//                cardView.layoutParams = cardViewParams
//                linearLayout.layoutParams = linearLayoutParams
//                linearLayout.orientation = LinearLayout.VERTICAL
//
//                cardView.addView(linearLayout)
//
//                val textView = TextView(act)
//                val percentile = (item.percentile * 100).toInt().toString()
//                textView.text = "${item.name} - $percentile% "
//                linearLayout.addView(textView)
//
//                linearLayoutMain.addView(cardView)
//            }

        }
    }

    fun pxFromDp(factor: Float, dp: Int): Float {
        return dp * factor
    }
}