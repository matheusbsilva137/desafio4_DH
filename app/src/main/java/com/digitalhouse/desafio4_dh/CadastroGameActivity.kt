package com.digitalhouse.desafio4_dh

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_cadastro_game.*
import kotlinx.android.synthetic.main.layout_cadastro_game.view.*

class CadastroGameActivity : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private lateinit var db: FirebaseFirestore
    private lateinit var cr: CollectionReference
    private val CODE_IMG = 1000
    lateinit var urlImg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_game)
        config()

        ib_camera_game_register.setOnClickListener{
            setIntent()
        }

        inc_cadastro_game.btn_cadastro.setOnClickListener {
            var game = getData()
            sendGame(game)
        }
    }

    fun config(){
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        db = FirebaseFirestore.getInstance()
        cr = db.collection("games")
    }

    fun getData(): MutableMap<String, Any>{
        val game: MutableMap<String, Any> = HashMap()

        game["name"] = inc_cadastro_game.tiet_name_game.text.toString()
        game["creationDate"] = inc_cadastro_game.tiet_created_at_game.text.toString()
        game["description"] = inc_cadastro_game.tiet_description_game.text.toString()
        game["urlImg"] = urlImg

        return game
    }

    fun sendGame(game: MutableMap<String, Any>){
        val name = inc_cadastro_game.tiet_name_game.text.toString()

        cr.document(name).set(game).addOnSuccessListener {
            Toast.makeText(this, "Jogo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()

            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }.addOnFailureListener{
            Toast.makeText(this, "Falha ao cadastrar o jogo! Tente novamente.", Toast.LENGTH_SHORT).show()
        }
    }

    //Configura a intent para obter a imagem da galeria
    fun setIntent(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura de imagem"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG){
            alertDialog.show()
            data!!.dataString

            storageReference = FirebaseStorage.getInstance().getReference(data!!.dataString!!)
            val uploadTask = storageReference.putFile(data!!.data!!)
            uploadTask.continueWithTask { task->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Imagem enviada com sucesso!", Toast.LENGTH_SHORT).show()
                }
                storageReference!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    urlImg = url
                    alertDialog.dismiss()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}