package com.example.vinilos.ui.album

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    var listener: dateSelectedListener? = null

    interface dateSelectedListener {
        fun dateSelected(year: Int, month: Int, day:Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? dateSelectedListener
        if (listener == null) {
            throw ClassCastException("$context must implement dateSelectedListener")
        }

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val year = arguments?.getInt("year")!!
        val month = arguments?.getInt("month")!!
        val day = arguments?.getInt("day")!!




        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(),
            DatePickerDialog.THEME_HOLO_DARK, this, year, month, day)
    }




    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {

        listener?.dateSelected(year, month+1, day)


    }
}