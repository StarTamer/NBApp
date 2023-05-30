package com.example.nbapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        var apiUrl: String ? = "https://www.balldontlie.io/api/v1/"
        var playerStatsBySeason: String = "/season_averages?season={{season}}&player_ids[]={{playerId}}"
        var playerInfos: String = "/players?search={{firstName}}+{{lastName}}"
        var allTeams: String = "/teams"
        var team: String = "/teams/{{teamId}}"
        var gamesWithDate: String = "/games?start_date={{YYYY-MM-DD}}&end_date={{YYYY-MM-DD}}"
        var gameById: String = "/games/{{id}}"
        var playerStatsByGame: String = "/stats?player_ids[]={{playerId}}&game_ids[]={{gameId}}"//117709
        var playersForTeam: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        println("ee")
        super.onCreate(savedInstanceState)
        getPlayerInfo()
        setContentView(R.layout.activity_main)
    }

    fun getPlayerInfo() {
        val serverLocation = apiUrl;
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(serverLocation.plus("players?search=zach+lavine"))
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val code = response.code
                runOnUiThread {
                    when {
                        code >= 400 -> {
                            Toast.makeText(getContext(), "error 400", Toast.LENGTH_SHORT).show()
                        }
                        code >= 200 -> {
                            val resp = GsonBuilder().create().fromJson(body, Resp::class.java)
                            val player: Player = resp.data[0]

                            println(player.first_name + " " + player.last_name)
                            println(player.team.full_name)
                        }
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
                println(e)
                if (e.toString() == "java.net.SocketTimeoutException: timeout" || e.toString() == "java.net.SocketTimeoutException: SSL handshake timed out") {
                    runOnUiThread {
                        Toast.makeText(getContext(), "Timeout, server didn't respond", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
    /**
     * Returns the current context
     */
    fun getContext(): Context? {
        return this
    }
}

class Team(val id: Int, val full_name: String, val name: String, val city: String)
class Player(val id: Int, val first_name: String, val height_feet: Int, val height_inches: Int, val last_name: String, val position: String, val team: Team)
class Resp(val data: Array<Player>)
class RespT(val data: Array<Team>)