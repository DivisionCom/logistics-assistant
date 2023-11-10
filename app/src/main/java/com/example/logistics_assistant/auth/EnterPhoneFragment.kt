package com.example.logistics_assistant.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.logistics_assistant.models.MainViewModel
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentPhoneBinding
import com.example.logistics_assistant.main.chat.User
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn


class EnterPhoneFragment : Fragment() {

    private lateinit var binding: FragmentPhoneBinding
    private val model: MainViewModel by activityViewModels()

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
            saveUserPhone()
            findNavController().navigate(R.id.action_enterPhoneFragment_to_enterPasswordFragment)
        }
    }

    private fun saveUserPhone(){
        val user = User(
            phone = binding.etPhone.text.toString()
        )
        model.liveDataCurrent.value = user
    }

    private fun changeBtnColors(enabled: Boolean){
        val btn = binding.btnContinue
        if(!enabled){
            btn.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.btnDisBack, null))
            btn.setTextColor(ResourcesCompat.getColor(resources, R.color.btnDisText, null))
        } else {
            btn.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.mainBlack, null))
            btn.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
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