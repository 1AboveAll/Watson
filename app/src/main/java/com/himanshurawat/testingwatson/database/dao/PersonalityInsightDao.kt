package com.himanshurawat.testingwatson.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity

@Dao
interface PersonalityInsightDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInsight(insight: PersonalityInsightEntity)

    @Query("SELECT * FROM personalityInsight")
    fun allInsights(): List<PersonalityInsightEntity>


}