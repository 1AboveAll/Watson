package com.himanshurawat.testingwatson.personalityinsight

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.himanshurawat.testingwatson.personalityinsight.fragment.InsightDatabaseFragment
import com.himanshurawat.testingwatson.personalityinsight.fragment.InsightFragment
import com.himanshurawat.testingwatson.R

class PersonalityInsight : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personality_insight)

        title = "Personality Insight"

        val fragment = InsightFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.personality_insight_frame_layout,fragment)
                .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.insight_dashboard ->{
                supportFragmentManager.beginTransaction()
                        .replace(R.id.personality_insight_frame_layout,InsightDatabaseFragment())
                        .commit()
            }

        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.insight_menu,menu)

        return true
    }
}

