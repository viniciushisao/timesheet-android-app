package br.com.hisao.timesheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.com.hisao.timesheet.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainBinding.inflate(inflater, container, false)
        setListeners(binding);


        return binding.root
    }

    private fun setListeners(binding: FragmentMainBinding) {
        binding.openCalendar.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_reportFragment)
        }
    }

}