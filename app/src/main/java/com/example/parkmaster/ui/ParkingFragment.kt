package com.example.parkmaster.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.parkmaster.adapter.ViolationAdapter
import com.example.parkmaster.databinding.FragmentParkingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ParkingFragment : Fragment() {

    private val viewmodel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentParkingBinding

    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParkingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvUsername.text = viewmodel.loggedInUser.value!!.username

        lifecycleScope.launch {
            viewmodel.loadViolations()
            binding.rvViolations.adapter = ViolationAdapter(viewmodel.violations.value!!)
        }
    }

}