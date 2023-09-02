package com.example.nbapp.ui.home

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): GamesData
}