package com.himanshurawat.testingwatson.toneanalyzer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.himanshurawat.testingwatson.R
import com.himanshurawat.testingwatson.toneanalyzer.fragment.AnalyzerDatabaseFragment
import com.himanshurawat.testingwatson.toneanalyzer.fragment.AnalyzerFragment

class ToneAnalyzer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tone_analyzer)

        title = "Tone Analysis"

        val fragment = AnalyzerFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.tone_analyzer_frame_layout,fragment)
                .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tone_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.tone_menu -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.tone_analyzer_frame_layout, AnalyzerDatabaseFragment())
                        .commit()
            }

        }

        return super.onOptionsItemSelected(item)
    }
}
