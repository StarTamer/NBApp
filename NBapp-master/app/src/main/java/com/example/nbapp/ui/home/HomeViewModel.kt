package com.example.nbapp.ui.home

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.*
import com.example.nbapp.MainActivity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.AccessController.getContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val appContext = application.applicationContext
    private val handler = Handler(Looper.getMainLooper())
    //val gamesData = MutableLiveData<GamesData>()
    private val apiService = Retrofit.Builder()
        .baseUrl(MainActivity.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val _gamesData = MutableLiveData<GamesData>()
    val gamesData: LiveData<GamesData> = _gamesData

    fun fetchLastGames(nbGames: Int) {
        viewModelScope.launch {
            try {
                val startDate = "2023-06-01"
                val endDate = "2023-09-02"
                val response = apiService.getGames(startDate, endDate)
                _gamesData.value = response
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
/*
    fun getLastGames(nbGames: Int) {
        val serverLocation = MainActivity.apiUrl;
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(serverLocation.plus("games?start_date=2023-06-01&end_date=2023-09-02"))
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val code = response.code
                handler.post {
                    when {
                        code >= 400 -> {
                            Toast.makeText(appContext, "error 400", Toast.LENGTH_SHORT).show()
                        }
                        code >= 200 -> {
                            val resp = GsonBuilder().create().fromJson(body, GamesData::class.java)
                            gamesData.value = resp
                        }
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
                println(e)
                if (e.toString() == "java.net.SocketTimeoutException: timeout" || e.toString() == "java.net.SocketTimeoutException: SSL handshake timed out") {
                    handler.post {
                        Toast.makeText(appContext, "Timeout, server didn't respond", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

 */
}
