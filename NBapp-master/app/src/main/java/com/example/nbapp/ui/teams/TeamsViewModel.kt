package com.example.nbapp.ui.teams

import android.app.Application
import androidx.lifecycle.*
import com.example.nbapp.MainActivity
import com.example.nbapp.Team
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamsViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = Retrofit.Builder()
        .baseUrl(MainActivity.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val _teamsData = MutableLiveData<Array<Team>>()
    val teamsData: LiveData<Array<Team>> = _teamsData

    fun fetchTeams() {
        viewModelScope.launch {
            try {
                val response = apiService.getTeams()
                _teamsData.value = response.data
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}