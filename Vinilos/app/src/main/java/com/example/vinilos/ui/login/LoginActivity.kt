package com.example.vinilos.ui.login

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.vinilos.databinding.ActivityLoginBinding
import com.example.vinilos.ui.album.AlbumesActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var ingresarColeccionistaButton: Button
    private lateinit var ingresarVisitanteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ingresarColeccionistaButton = binding.ingresarColeccionista
        ingresarVisitanteButton = binding.ingresarVisitante
        val loading = binding.loading

        val albumesIntent = Intent(this, AlbumesActivity::class.java)
        ingresarColeccionistaButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            albumesIntent.putExtra("COLECCIONISTA", true)
            try {
                ActivityCompat.startActivity(this, albumesIntent, null)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user

            }
            loading.visibility = View.GONE
        }

        ingresarVisitanteButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            albumesIntent.putExtra("COLECCIONISTA", false)
            try {
                ActivityCompat.startActivity(this, albumesIntent, null)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user

            }
            loading.visibility = View.GONE
        }
    }
}