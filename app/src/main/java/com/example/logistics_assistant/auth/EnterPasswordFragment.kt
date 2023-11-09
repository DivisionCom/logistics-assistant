package com.example.logistics_assistant.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.logistics_assistant.MainViewModel
import com.example.logistics_assistant.main.MainActivity
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentPasswordBinding
import com.example.logistics_assistant.main.MenuActivity

const val PASSWORD = "123456"
class EnterPasswordFragment : Fragment() {

    private lateinit var binding: FragmentPasswordBinding
    private val model: MainViewModel by activityViewModels()


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
        disableBtn()
        validating()
        binding.btnContinue.setOnClickListener{
            if(checkPassword()){
                val intent = Intent (activity, MenuActivity::class.java)
                intent.putExtra("save_phone", model.liveDataCurrent.value?.phone)
                activity?.startActivity(intent)
                activity?.finish()
            } else {
                binding.layoutPassword.boxStrokeColor = ResourcesCompat.getColor(resources, R.color.error, null)
                binding.tvError.visibility = View.VISIBLE
                disableBtn()
            }
        }
    }

    private fun checkPassword() : Boolean{
        val text = binding.etPassword.text.toString()
        return text == PASSWORD
    }

    private fun validating(){
        binding.etPassword.doOnTextChanged { _, _, _, _ ->
            val text = binding.etPassword.text
            if(text!!.length < 6){
                disableBtn()
            } else {
                enableBtn()
            }
        }
    }

    private fun disableBtn(){
        val btn = binding.btnContinue
        btn.isEnabled = false
        btn.setBackgroundColor(resources.getColor(R.color.btnDisBack))
        btn.setTextColor(resources.getColor(R.color.btnDisText))
    }

    private fun enableBtn(){
        val btn = binding.btnContinue
        btn.isEnabled = true
        btn.setBackgroundColor(resources.getColor(R.color.mainBlack))
        btn.setTextColor(resources.getColor(R.color.white))
    }

    private fun goBack(){
        binding.ibBack.setOnClickListener {
            findNavController().navigate(R.id.action_enterPasswordFragment_to_enterPhoneFragment)
        }
    }

}