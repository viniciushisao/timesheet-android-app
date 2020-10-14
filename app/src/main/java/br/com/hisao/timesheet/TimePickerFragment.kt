package br.com.hisao.timesheet

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment(private val listener: OnTimeSetListener) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(requireContext(), this, hour, minute, false)

    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        listener.onTimeSet(p1, p2)
    }
}

interface OnTimeSetListener {
    fun onTimeSet(hour24: Int, minute: Int)
}