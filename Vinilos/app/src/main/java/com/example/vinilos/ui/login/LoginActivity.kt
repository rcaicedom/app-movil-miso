package com.example.vinilos.ui.login

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.vinilos.databinding.ActivityLoginBinding
import com.example.vinilos.ui.album.AlbumesActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var usuarioEdit: EditText
    private lateinit var contrasenaEdit: EditText
    private lateinit var ingresarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuarioEdit = binding.usuarioEdit
        contrasenaEdit = binding.contrasenaEdit
        ingresarButton = binding.ingresarButton
        val loading = binding.loading

        val albumesIntent = Intent(this, AlbumesActivity::class.java)
        ingresarButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            try {
                ActivityCompat.startActivity(this, albumesIntent, null)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user

            }
            loading.visibility = View.GONE
        }
    }
}