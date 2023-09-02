package com.example.nbapp.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbapp.MainActivity
import com.example.nbapp.Player
import com.example.nbapp.R
import com.example.nbapp.Resp
import com.example.nbapp.ui.home.GamesAdapter
import com.example.nbapp.ui.home.HomeViewModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_players.*
import okhttp3.*
import java.io.IOException

class PlayersFragment : Fragment() {
    private val viewModel: PlayersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_players, container, false)

        val recyclerviewPlayers : RecyclerView = root.findViewById(R.id.recyclerView_players)
        recyclerviewPlayers.layoutManager = LinearLayoutManager(activity)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchPlayers()

        viewModel.playersData.observe(viewLifecycleOwner, Observer { players ->
            recyclerView_players.adapter = PlayersAdapter(players, requireContext())
        })
    }
/*
    fun getPlayerInfo() {
        val serverLocation = MainActivity.apiUrl;
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(serverLocation.plus("players"))
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
                            val players: Array<Player> = resp.data
                            activity?.runOnUiThread {
                                recyclerView_players.adapter = PlayersAdapter(players, context!!)
                            }

                            println(players[0].first_name + " " + players[0].last_name)
                            println(players[0].team.city)
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

 */
}





class Team(val id: Int, val full_name: String, val name: String, val city: String)
class Game(val date: String, val home_team: Team, val visitor_team: Team, val home_team_score: Int, val visitor_team_score: Int, val status: String)
