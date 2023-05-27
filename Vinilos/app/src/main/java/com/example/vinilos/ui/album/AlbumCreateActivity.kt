package com.example.vinilos.ui.album

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.data.album.Album
import com.example.vinilos.databinding.ActivityAlbumCreateBinding
import com.example.vinilos.viewmodel.album.AlbumCreateViewModel


class AlbumCreateActivity : AppCompatActivity(), DatePickerFragment.dateSelectedListener {

    private lateinit var binding: ActivityAlbumCreateBinding
    private lateinit var vm: AlbumCreateViewModel
    private lateinit var editNombre: EditText
    private lateinit var editCover: EditText
    private lateinit var textFecha: TextView
    private lateinit var editDescripcion: EditText
    private lateinit var spinnerGenero: Spinner
    private lateinit var spinnerCasa: Spinner
    private lateinit var buttonCrear: Button
    private lateinit var newAlbum: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filledAllFields = IntArray(4);
        for (field in filledAllFields){
            filledAllFields[field] = 0
        }
        binding = ActivityAlbumCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editNombre = binding.editCrearNombre
        editCover = binding.editCrearCover
        textFecha = binding.editCrearFecha
        textFecha.setOnClickListener {
            val newFragment = DatePickerFragment()
            var args : Bundle = Bundle()
            args.putInt("year", 2023)
            args.putInt("month", 5)
            args.putInt("day", 25)
            newFragment.arguments = args
            newFragment.show(supportFragmentManager, "datePicker")
        }

        editDescripcion = binding.editCrearDescripcion

        spinnerGenero = binding.spinnerCrearGenero
        ArrayAdapter.createFromResource(
            this,
            R.array.generos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinnerGenero.adapter = adapter
        }
        spinnerCasa = binding.spinnerCrearCasa
        ArrayAdapter.createFromResource(
            this,
            R.array.casas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinnerCasa.adapter = adapter
        }
        buttonCrear = binding.buttonCrearAlbum

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

        editCover.addTextChangedListener(object: TextWatcher {
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

        textFecha.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty())
                {
                    filledAllFields[2] = 1;
                }
                else{
                    filledAllFields[2] = 0;
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

        editDescripcion.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty())
                {
                    filledAllFields[3] = 1
                }
                else{
                    filledAllFields[3] = 0
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

        buttonCrear.setOnClickListener {
            if(!filledAllFields.contains(0)){

                val nombre = editNombre.text.toString()
                val cover = editCover.text.toString()
                val fecha = textFecha.text.toString()
                val descripcion = editDescripcion.text.toString()
                val genero = spinnerGenero.selectedItem.toString()
                val casa = spinnerCasa.selectedItem.toString()

                newAlbum = Album()

                newAlbum.name = nombre
                newAlbum.cover = cover
                newAlbum.releaseDate = fecha
                newAlbum.description = descripcion
                newAlbum.genre = genero
                newAlbum.recordLabel = casa

                createAlbum(newAlbum)
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

    private fun createAlbum(album: Album){
        vm = ViewModelProvider(this, AlbumCreateViewModel.Factory(application, album))[AlbumCreateViewModel::class.java]
        vm.albumCreado.observe(this){
            if (it) {
                Toast.makeText(
                    applicationContext,
                    "El album fue creado exitosamente!",
                    Toast.LENGTH_LONG
                ).show()
                val returnIntent = Intent()
                returnIntent.putExtra("NEW-ALBUM", album)
                setResult(RESULT_OK, returnIntent)
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error creando el album, vuelva a intentar.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        vm.eventNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun dateSelected(year: Int, month: Int, day: Int) {
        var month1 = ""
        if(month < 10){
            month1="0"
        }
        var day1 = ""

        if(day < 10){
            day1="0"
        }
        textFecha.text = "$year-$month1$month-$day1$day"
    }

    private fun onNetworkError() {
        if (!vm.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            vm.onNetworkErrorShown()
        }
    }
}