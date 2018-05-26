package com.himanshurawat.testingwatson.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "personalityInsight")
data class PersonalityInsightEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var timestamp: Long = 0,
        var userInput: String = "",
        var watsonOutput: String = "",
        var watsonOutputData: String = "",
        var feedback: String = ""
)