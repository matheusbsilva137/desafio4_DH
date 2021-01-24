package com.digitalhouse.desafio4_dh.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.desafio4_dh.R
import com.digitalhouse.desafio4_dh.entities.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game.view.*

class AdapterGames(var context: Context, var listGames : ArrayList<Game>): RecyclerView.Adapter<AdapterGames.GamesViewHolder>() {

    class GamesViewHolder(gameView: View): RecyclerView.ViewHolder(gameView){
        var view = itemView
        var ivCapa : ImageView = gameView.findViewById(R.id.iv_game)
        var tvTitulo : TextView = gameView.findViewById(R.id.tv_titulo_game)
        var tvDataPub : TextView = gameView.findViewById(R.id.tv_data_pub_game)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterGames.GamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterGames.GamesViewHolder, position: Int) {
        var item = listGames[position]
        holder.tvTitulo.text = item.name
        holder.tvDataPub.text = item.creationDate

        Picasso.with(this.context).load(item.imgUrl).into(holder.ivCapa)

        holder.view.setOnClickListener{
            var intent = Intent(it.context, GameActivity::class.java)
            intent.putExtra("imageUrl", item.imgUrl)
            intent.putExtra("creationDate", item.creationDate)
            intent.putExtra("name", item.name)
            intent.putExtra("description", item.description)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listGames.size

}