package com.example.nbapp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbapp.MainActivity
import com.example.nbapp.Player
import com.example.nbapp.R
import com.example.nbapp.Resp
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        getPlayerInfo()
        getLastGames(1)

        val recyclerviewGames : RecyclerView = root.findViewById(R.id.recyclerView_games)
        recyclerviewGames.layoutManager = LinearLayoutManager(activity)

        return root
    }

    fun getLastGames(nbGames: Int) {
        val serverLocation = MainActivity.apiUrl;
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(serverLocation.plus("games?start_date=2023-05-15&end_date=2023-05-29"))
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val code = response.code
                activity?.runOnUiThread {
                    when {
                        code >= 400 -> {
                            Toast.makeText(getContext(), "error 400", Toast.LENGTH_SHORT).show()
                        }
                        code >= 200 -> {
                            val resp = GsonBuilder().create().fromJson(body, GamesData::class.java)
                            val games: Array<Game> = resp.data
                            activity?.runOnUiThread {
                                recyclerView_games.adapter = GamesAdapter(games, context!!)
                            }
                            println(games[0].home_team.full_name)
                            println(games[0].visitor_team.full_name)
                        }
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
                println(e)
                if (e.toString() == "java.net.SocketTimeoutException: timeout" || e.toString() == "java.net.SocketTimeoutException: SSL handshake timed out") {
                    activity?.runOnUiThread {
                        Toast.makeText(getContext(), "Timeout, server didn't respond", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    fun getPlayerInfo() {
        val serverLocation = MainActivity.apiUrl;
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(serverLocation.plus("players?search=zach+lavine"))
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val code = response.code
                activity?.runOnUiThread {
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
                    activity?.runOnUiThread {
                        Toast.makeText(getContext(), "Timeout, server didn't respond", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}

class Team(val id: Int, val full_name: String, val name: String, val city: String)
class Game(val date: String, val home_team: Team, val visitor_team: Team, val home_team_score: Int, val visitor_team_score: Int, val status: String)
class GamesData(val data: Array<Game>)