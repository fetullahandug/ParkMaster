package com.example.parkmaster.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.parkmaster.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private val viewmodel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnLogin.setOnClickListener {
            if(binding.etUsername.text.toString() == "" || binding.etPassword.text.toString() == "") {
                Toast.makeText(requireContext(), "Bitte gebe deine Logindaten ein!", Toast.LENGTH_LONG).show()
            }else {
                viewmodel.addLoginData(binding.etUsername.text.toString(), binding.etPassword.text.toString())
                lifecycleScope.launch {
                    viewmodel.login()
                }
            }
        }

        viewmodel.loggedInUser.observe(viewLifecycleOwner) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMenuFragment())
            Log.i("LoginFragment", "User logged in!")
        }
    }

}