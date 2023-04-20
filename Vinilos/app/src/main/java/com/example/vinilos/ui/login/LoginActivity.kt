package com.example.vinilos.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.databinding.ActivityLoginBinding

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

        ingresarButton.setOnClickListener {
            loading.visibility = View.VISIBLE
        }
    }
}