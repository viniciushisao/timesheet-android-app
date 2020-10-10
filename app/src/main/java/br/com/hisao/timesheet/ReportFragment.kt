package br.com.hisao.timesheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import br.com.hisao.timesheet.databinding.FragmentReportBinding
import java.util.*


class ReportFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReportBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val reportViewModelFactory = ReportViewModelFactory(application)

        val viewModel: ReportViewModel by lazy {
            ViewModelProvider(this, reportViewModelFactory).get(ReportViewModel::class.java)
        }

        setObservables(viewModel, binding)
        setListeners(viewModel, binding)
        openDayPicker(viewModel)

        return binding.root
    }


    private fun setObservables(viewModel: ReportViewModel, binding: FragmentReportBinding) {
        viewModel.currentDate.observe(viewLifecycleOwner) { calendar: Calendar ->
            binding.lblSelecteddate.text = calendar.getFormattedDate()
        }

    }

    private fun setListeners(viewModel: ReportViewModel, binding: FragmentReportBinding) {
        binding.btnSelectdate.setOnClickListener(View.OnClickListener {
            openDayPicker(viewModel)
        })
    }


    private fun openDayPicker(viewModel: ReportViewModel) {

        val listener: OnDateSetListener = object : OnDateSetListener {
            override fun onDateSet(calendar: Calendar) {
                viewModel.onPickDate(calendar)
            }
        }
        val newFragment = DatePickerFragment(listener)
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        newFragment.show(fragmentManager, "datePicker")
    }

}