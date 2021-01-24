package com.digitalhouse.desafio4_dh.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalhouse.desafio4_dh.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapterGames: AdapterGames
    private lateinit var gridLayoutManager: GridLayoutManager

    val viewModel: MainViewModel by viewModels<MainViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayoutManager = GridLayoutManager(this, 2)
        rv_games.layoutManager = gridLayoutManager

        rv_games.hasFixedSize()

        viewModel.listGames.observe(this){
            adapterGames = AdapterGames(rv_games.context, it)
            rv_games.adapter = adapterGames
        }

        viewModel.popListGames()

        fab_add_game.setOnClickListener {
            finish()
            val intent = Intent(this, CadastroGameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}