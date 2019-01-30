package com.example.guidebook.core

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.guidebook.interfaces.TaskDao
import com.example.guidebook.models.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(
    TaskConverter::class
)
abstract class GuideBook : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    // Get a database instance
    companion object {
        var sInstance: GuideBook? = null
        @Synchronized
        fun getDatabaseInstance(context: Context): GuideBook {
            if (sInstance == null) {
                sInstance = create(context)
            }
            return sInstance as GuideBook
        }

        // Create the database
        fun create(context: Context): GuideBook {
            val builder = Room.databaseBuilder(
                context.applicationContext,
                GuideBook::class.java,
                "GuideBook"
            ).allowMainThreadQueries()// todo added addCallback
            /*  .addCallback(object : RoomDatabase.Callback() {
              override fun onCreate(db: SupportSQLiteDatabase) {
                  super.onCreate(db)
                  val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                  WorkManager.getInstance()?.enqueue(request)
              }
          })*/
            return builder.build()
        }
    }
}