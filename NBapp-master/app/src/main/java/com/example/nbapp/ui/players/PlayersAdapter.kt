package com.example.nbapp.ui.players

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbapp.Player
import com.example.nbapp.R
import com.example.nbapp.ui.home.GamesViewHolder
import kotlinx.android.synthetic.main.player_item.view.*

class PlayersAdapter(val players: Array<Player>, val context: Context): RecyclerView.Adapter<PlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.player_item, parent, false)
        return PlayersViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val player = players.get(position)
        holder.view.playerName.text = (player.last_name + " " + player.first_name[0] + ".").toString()
        holder.view.playerClub.text = player.team.city.toString()
        holder.view.playerPosition.text = player.position.toString()
    }

    override fun getItemCount(): Int {
        return players.size
    }
}

class PlayersViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}
