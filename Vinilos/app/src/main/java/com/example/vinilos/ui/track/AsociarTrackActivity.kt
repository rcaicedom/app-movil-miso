package com.example.vinilos.ui.track

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.track.Track
import com.example.vinilos.databinding.ActivityAsociarTrackBinding
import com.example.vinilos.viewmodel.album.AlbumCreateViewModel
import com.example.vinilos.viewmodel.track.TrackViewModel

class AsociarTrackActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAsociarTrackBinding
    private lateinit var vm: TrackViewModel
    private lateinit var editNombre: EditText
    private lateinit var editDuracion: EditText
    private lateinit var textAlbum: TextView
    private lateinit var buttonAsociar: Button
    private lateinit var newTrack: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filledAllFields = IntArray(2);
        for (field in filledAllFields){
            filledAllFields[field] = 0
        }

        binding = ActivityAsociarTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editNombre = binding.editAsociarNombre
        editDuracion = binding.editAsociarDuracion
        textAlbum = binding.labelAsociarAlbumNombre
        textAlbum.text = intent.getStringExtra("ALBUM-NAME")
        val idAlbum = intent.getIntExtra("ALBUM-ID", 0)
        buttonAsociar = binding.buttonAsociar

        editNombre.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty())
                {
                    filledAllFields[0] = 1;
                }
                else{
                    filledAllFields[0] = 0;
                }
            }
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int,
                                           after:Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        editDuracion.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty())
                {
                    filledAllFields[1] = 1;
                }
                else{
                    filledAllFields[1] = 0;
                }
            }
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int,
                                           after:Int) {
                // TODO Auto-generated method stub
            }
            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        buttonAsociar.setOnClickListener {
            if(!filledAllFields.contains(0)){

                val nombre = editNombre.text.toString()
                val duracion = editDuracion.text.toString().toInt()

                newTrack = Track()

                newTrack.name = nombre
                val minutos = "" + if(duracion / 60 >= 10) duracion / 60 else "0" + duracion / 60
                val segundos = ":" + if(duracion % 60 >= 10) duracion % 60 else "0" + duracion % 60
                newTrack.duration = minutos + segundos

                addTrackToAlbum(idAlbum, newTrack)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "Por favor llene todos los campos.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun addTrackToAlbum(idAlbum: Int, track: Track){
        vm = ViewModelProvider(this, TrackViewModel.Factory(application, idAlbum, track))[TrackViewModel::class.java]
        vm.trackCreado.observe(this){
            if (it) {
                Toast.makeText(
                    applicationContext,
                    "El track fue asociado exitosamente!",
                    Toast.LENGTH_LONG
                ).show()
                val returnIntent = Intent()
                returnIntent.putExtra("NEW-TRACK", track)
                setResult(RESULT_OK, returnIntent)
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error creando el track, vuelva a intentar.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        vm.eventNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    private fun onNetworkError() {
        if (!vm.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            vm.onNetworkErrorShown()
        }
    }
}