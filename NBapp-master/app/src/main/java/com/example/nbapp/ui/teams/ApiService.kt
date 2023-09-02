package com.example.nbapp.ui.teams

import com.example.nbapp.RespT
import retrofit2.http.GET

interface ApiService {
    @GET("teams")
    suspend fun getTeams(): RespT
}