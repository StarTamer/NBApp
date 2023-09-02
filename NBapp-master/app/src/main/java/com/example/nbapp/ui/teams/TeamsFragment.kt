package com.example.nbapp.ui.teams

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
import com.example.nbapp.*
import com.example.nbapp.ui.home.Game
import com.example.nbapp.ui.home.GamesAdapter
import com.example.nbapp.ui.home.GamesData
import com.example.nbapp.ui.home.HomeViewModel

import com.example.nbapp.ui.teams.TeamsAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_teams.*
import okhttp3.*
import java.io.IOException

class TeamsFragment : Fragment() {
    private val viewModel: TeamsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_teams, container, false)
        //getTeams()

        val recyclerviewTeams : RecyclerView = root.findViewById(R.id.recyclerView_teams)
        recyclerviewTeams.layoutManager = LinearLayoutManager(activity)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchTeams()

        viewModel.teamsData.observe(viewLifecycleOwner, Observer { teams ->
            recyclerView_teams.adapter = TeamsAdapter(teams, requireContext())
        })
    }

/*
    fun getTeams() {
        val serverLocation = MainActivity.apiUrl;
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(serverLocation.plus("teams"))
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
                            val resp = GsonBuilder().create().fromJson(body, RespT::class.java)
                            val teams: Array<Team> = resp.data
                            activity?.runOnUiThread {
                                recyclerView_teams.adapter = TeamsAdapter(teams, context!!)
                            }
//                            println(games[0].home_team.full_name)
//                            println(games[0].visitor_team.full_name)
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


class Game(val date: String, val home_team: Team, val visitor_team: Team, val home_team_score: Int, val visitor_team_score: Int, val status: String)
