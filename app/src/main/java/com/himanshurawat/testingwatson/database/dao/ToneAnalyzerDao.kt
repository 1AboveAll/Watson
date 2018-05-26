package com.himanshurawat.testingwatson.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity
import com.himanshurawat.testingwatson.database.entity.ToneAnalyzerEntity

@Dao
interface ToneAnalyzerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTone(insight: ToneAnalyzerEntity)

    @Query("SELECT * FROM toneAnalyzer")
    fun allTones(): List<ToneAnalyzerEntity>
}