package com.example.nbapp.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbapp.R
import kotlinx.android.synthetic.main.game_row.view.*
import java.io.File
import java.text.SimpleDateFormat


class GamesAdapter(val games: Array<Game>, val context: Context) : RecyclerView.Adapter<GamesViewHolder>() {

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.game_row, parent, false)
        return GamesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val game = games.get(position)

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val output: String = formatter.format(parser.parse(game.date))
        holder.view.textView_date.text = output
        holder.view.textView_homeTeamScore.text = game.home_team_score.toString()
        holder.view.textView_visitorTeamScore.text = game.visitor_team_score.toString()

        var imgName = game.home_team.full_name.replace(" ", "_").toLowerCase()
        var img = context.resources.getIdentifier(imgName, "drawable", context.packageName)
        holder.view.imageView_homeTeamLogo.setImageResource(img)
        imgName = game.visitor_team.full_name.replace(" ", "_").toLowerCase()
        println(imgName)
        img = context.resources.getIdentifier(imgName, "drawable", context.packageName)
        holder.view.imageView_visitorTeamLogo.setImageResource(img)

        var homeTeamName = game.home_team.full_name
        holder.view.textView_homeTeamName.setText(homeTeamName)
        var visitorTeamName = game.visitor_team.full_name
        holder.view.textView_visitorTeamName.setText(visitorTeamName)
    }


}

class GamesViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}