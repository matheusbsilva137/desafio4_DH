package com.digitalhouse.desafio4_dh.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.TextView
import android.widget.Toast
import com.digitalhouse.desafio4_dh.R
import com.digitalhouse.desafio4_dh.service.cr
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_game.*
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.layout_cadastro_game.view.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tv_descricao.isSingleLine = false
        tv_titulo_img.text = intent.getStringExtra("name")
        tv_titulo.setText(intent.getStringExtra("name"), TextView.BufferType.EDITABLE)
        tv_data_pub.setText(intent.getStringExtra("creationDate"), TextView.BufferType.EDITABLE)
        tv_descricao.setText(intent.getStringExtra("description"), TextView.BufferType.EDITABLE)

        tv_data_pub.isEnabled = false
        tv_descricao.isEnabled = false

        Picasso.with(this).load(intent.getStringExtra("imageUrl")).into(imv_bkg)

        ib_return.setOnClickListener {
            finish()
        }

        ib_edit.setOnClickListener {
            if (tv_data_pub.isEnabled){
                tv_data_pub.isEnabled = false
                tv_descricao.isEnabled = false
                sendGame(getData())
            }else{
                tv_data_pub.isEnabled = true
                tv_descricao.isEnabled = true
            }
        }
    }

    fun getData(): MutableMap<String, Any>{
        val game: MutableMap<String, Any> = HashMap()

        game["name"] = intent.getStringExtra("name")!!
        game["creationDate"] = tv_data_pub.text.toString()
        game["description"] = tv_descricao.text.toString()
        game["urlImg"] = intent.getStringExtra("imageUrl")!!

        return game
    }

    fun sendGame(game: MutableMap<String, Any>){
        val name = tv_titulo.text.toString()

        cr.document(name).update(game).addOnSuccessListener {
            Toast.makeText(this, "Jogo atualizado com sucesso!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Falha ao cadastrar o jogo! Tente novamente.", Toast.LENGTH_SHORT).show()
        }
    }
}