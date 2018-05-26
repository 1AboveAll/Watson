package com.himanshurawat.testingwatson.database

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.himanshurawat.testingwatson.database.dao.PersonalityInsightDao
import com.himanshurawat.testingwatson.database.dao.ToneAnalyzerDao
import com.himanshurawat.testingwatson.database.entity.PersonalityInsightEntity
import com.himanshurawat.testingwatson.database.entity.ToneAnalyzerEntity

@android.arch.persistence.room.Database(entities = [(PersonalityInsightEntity::class),(ToneAnalyzerEntity::class)],version = 3,exportSchema = false)
abstract class Database: RoomDatabase() {

    abstract fun getPersonalityInsightDao(): PersonalityInsightDao
    abstract fun getToneAnalyzerDao(): ToneAnalyzerDao


    companion object {
        private var INSTANCE:Database? = null

        fun getInstance(context: Context):Database{
            if(INSTANCE == null){
                synchronized(Database::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            Database::class.java,"note_db").build()
                }
            }
            return INSTANCE as Database
        }

        private fun destroyInstance(){
            INSTANCE = null
        }
    }


}