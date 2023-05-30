package com.example.nbapp.ui.teams

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbapp.R
import com.example.nbapp.Team
//import com.example.nbapp.ui.players.TeamsViewHolder
import kotlinx.android.synthetic.main.game_row.view.*
import kotlinx.android.synthetic.main.team_item.view.*

class TeamsAdapter(val teams: Array<Team>, val context: Context): RecyclerView.Adapter<TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_item, parent, false)
        return TeamsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = teams.get(position)

        holder.view.textView_team_name.text = team.full_name.toString()

        var imgName = team.full_name.replace(" ", "_").toLowerCase()
        var img = context.resources.getIdentifier(imgName, "drawable", context.packageName)
        holder.view.image_view_team_logo.setImageResource(img)
    }

    override fun getItemCount(): Int {
        return teams.size
    }
}

class TeamsViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}
