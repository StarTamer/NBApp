package com.example.nbapp.ui.players

import android.app.Application
import androidx.lifecycle.*
import com.example.nbapp.MainActivity
import com.example.nbapp.Player
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayersViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = Retrofit.Builder()
        .baseUrl(MainActivity.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val _playersData = MutableLiveData<Array<Player>>()
    val playersData: LiveData<Array<Player>> = _playersData

    fun fetchPlayers() {
        viewModelScope.launch {
            try {
                val response = apiService.getPlayers()
                _playersData.value = response.data
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}
