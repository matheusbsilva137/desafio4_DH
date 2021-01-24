package com.digitalhouse.desafio4_dh.ui

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.desafio4_dh.entities.Game
import com.digitalhouse.desafio4_dh.service.cr

class MainViewModel() : ViewModel() {

    val listGames = MutableLiveData<ArrayList<Game>>()

    fun popListGames() {
        val list = ArrayList<Game>()
        cr.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        list.add(Game(document.data["name"].toString(), document.data["creationDate"].toString(), document.data["description"].toString(), document.data["urlImg"].toString()))
                    }
                    listGames.value = list
                } else {
                    Log.w("MainViewModel", "Error getting documents.", task.exception)
                }
            }
    }
}