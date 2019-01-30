package com.example.guidebook.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import android.os.AsyncTask
import android.util.Log
import com.example.guidebook.core.GuideBook
import com.example.guidebook.core.GuideBook.Companion.getDatabaseInstance
import com.example.guidebook.models.Task
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class TaskListViewModel(mApplication: Application) : ViewModel() {

    var mTasks: LiveData<MutableList<Task>>
    var isLoaded = false

    init {
        MyAsynTask(mApplication).execute()
        mTasks = GuideBook.getDatabaseInstance(mApplication).taskDao().allGuide
    }

    inner class MyAsynTask(application: Application) : AsyncTask<String, String, String>() {

        var urlConnection: HttpURLConnection? = null
        var guide: GuideBook = getDatabaseInstance(application)
        val CONNECTON_TIMEOUT_MILLISECONDS = 60000


        override fun doInBackground(vararg params: String?): String? {

            try {
                val url = URL("https://guidebook.com/service/v2/upcomingGuides/")

                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection!!.connectTimeout = CONNECTON_TIMEOUT_MILLISECONDS
                urlConnection!!.readTimeout = CONNECTON_TIMEOUT_MILLISECONDS

                var inString = streamToString(urlConnection!!.inputStream)
                return inString
            } catch (ex: Exception) {
                Log.e("exce:", ex.localizedMessage)
                return null
            } finally {
                if (urlConnection != null) {
                    urlConnection!!.disconnect()
                }
            }
        }

        fun streamToString(inputStream: InputStream): String {

            val bufferReader = BufferedReader(InputStreamReader(inputStream))
            var result = ""

            try {
                var lines: List<String> = bufferReader.readLines()
                for (line in lines) {
                    result += line
                }
                inputStream.close()
            } catch (ex: Exception) {

            }

            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                Log.e("response:", result.toString())
                var jsonParsor = JsonParser()

                var jo: JsonObject = jsonParsor.parse(result) as JsonObject
                var ja: JsonArray = jo.get("data").asJsonArray

                val turnsType = object : TypeToken<List<Task>>() {}.type
                val list = Gson().fromJson<List<Task>>(ja, turnsType)

                guide.taskDao().deleteAll()
                guide.taskDao().insertGuides(list)
                isLoaded = true
            }
        }
    }

    fun getTasks(): LiveData<MutableList<Task>> {
        return mTasks
    }

}