package br.com.hisao.timesheet.edit

//TODO
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import br.com.hisao.timesheet.*
import br.com.hisao.timesheet.databinding.FragmentEditBinding
import br.com.hisao.timesheet.model.Status
import br.com.hisao.timesheet.model.TimeSheetData
import br.com.hisao.timesheet.model.TimeSheetDataType
import br.com.hisao.timesheet.util.getFormattedDate
import br.com.hisao.timesheet.util.getFormattedTime

class EditFragment : DialogFragment(), AdapterView.OnItemSelectedListener {
    private val args: EditFragmentArgs by navArgs()
    private var newTimeSheetData = TimeSheetData.getDummy()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val editViewModelFactory = EditViewModelFactory(application)

        val viewModel: EditViewModel by lazy {
            ViewModelProvider(this, editViewModelFactory).get(EditViewModel::class.java)
        }
        val itemId = args.id

        setObservers(viewModel, binding)
        setListeners(binding, viewModel)

        viewModel.getTimeSheetData(itemId)

        binding.spnType.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, TimeSheetDataType.values())
        binding.spnType.onItemSelectedListener = this

        return binding.root
    }


    private fun setListeners(binding: FragmentEditBinding, viewModel: EditViewModel) {
        binding.btnDate.setOnClickListener {
            openDayPicker(binding)
        }
        binding.btnTime.setOnClickListener {
            openTimePicker(binding)
        }

        binding.btnSave.setOnClickListener {
            viewModel.updateTimeSheet(newTimeSheetData)
            activity?.onBackPressed()
        }
        binding.btnCancel.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun openTimePicker(binding: FragmentEditBinding) {
        val listener: OnTimeSetListener = object : OnTimeSetListener {

            override fun onTimeSet(hour24: Int, minute: Int) {
                newTimeSheetData.hour24 = hour24
                newTimeSheetData.minute = minute
                binding.lblTime.text = newTimeSheetData.getFormattedTime()
            }
        }
        val newFragment = TimePickerFragment(listener)
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        newFragment.show(fragmentManager, "timePicker")
    }

    private fun openDayPicker(binding: FragmentEditBinding) {

        val listener: OnDateSetListener = object : OnDateSetListener {
            override fun onDateSet(year: Int, month: Int, day: Int) {
                newTimeSheetData.day = day
                newTimeSheetData.month = month
                newTimeSheetData.year = year
                binding.lblDate.text = newTimeSheetData.getFormattedDate()
            }
        }
        val newFragment = DatePickerFragment(listener)
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        newFragment.show(fragmentManager, "datePicker")
    }

    private fun setObservers(viewModel: EditViewModel, binding: FragmentEditBinding) {
        viewModel.currentTimeSheetData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.let {
                        newTimeSheetData = it.data!!.copy()
                        binding.lblDate.text = it.data.getFormattedDate()
                        binding.lblTime.text = it.data.getFormattedTime()
                        binding.spnType.setSelection(TimeSheetDataType.getId(it.data.type))
                    }
                }
                Status.ERROR -> {
                    //TODO
                }
                Status.LOADING -> {
                    //TODO
                }
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, p3: Long) {
        newTimeSheetData.type = TimeSheetDataType.getName(id)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}