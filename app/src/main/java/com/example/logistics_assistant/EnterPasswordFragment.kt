package com.example.logistics_assistant

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.logistics_assistant.databinding.FragmentPasswordBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener

class EnterPasswordFragment : Fragment() {

    private lateinit var binding: FragmentPasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()

    }

    private fun goBack(){
        binding.ibBack.setOnClickListener {
            findNavController().navigate(R.id.action_enterPasswordFragment_to_enterPhoneFragment)
            Log.d("MyTag", "Pass: ${binding.etPassword.text}")
        }
    }

}