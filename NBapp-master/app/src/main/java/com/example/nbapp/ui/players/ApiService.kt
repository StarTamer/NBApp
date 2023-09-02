package com.example.nbapp.ui.players

import com.example.nbapp.Resp
import retrofit2.http.GET

interface ApiService {
    @GET("players")
    suspend fun getPlayers(): Resp
}