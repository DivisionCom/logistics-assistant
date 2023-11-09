package com.example.logistics_assistant.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentPhoneBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn


class EnterPhoneFragment : Fragment() {

    private lateinit var binding: FragmentPhoneBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPhoneSample()
        goNext()
        val btn = binding.btnContinue
        btn.isEnabled = false
        changeBtnColors(btn.isEnabled)
    }

    private fun goNext(){
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_enterPhoneFragment_to_enterPasswordFragment)
        }
    }

    private fun changeBtnColors(enabled: Boolean){
        val btn = binding.btnContinue
        if(!enabled){
            btn.setBackgroundColor(resources.getColor(R.color.btnDisBack))
            btn.setTextColor(resources.getColor(R.color.btnDisText))
        } else {
            btn.setBackgroundColor(resources.getColor(R.color.mainBlack))
            btn.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun setupPhoneSample() {
        val listener: MaskedTextChangedListener = installOn(
            binding.etPhone,
            "+7 ([000]) [000]-[00]-[00]",
            object : MaskedTextChangedListener.ValueListener {

                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    val btn = binding.btnContinue
                    btn.isEnabled = maskFilled
                    changeBtnColors(btn.isEnabled)
                }
            }
        )
        binding.etPhone.hint =  listener.placeholder()
    }

}