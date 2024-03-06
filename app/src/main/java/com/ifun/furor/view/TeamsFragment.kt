package com.ifun.furor.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ifun.furor.R
import com.ifun.furor.databinding.TeamsFragmentBinding
import com.ifun.furor.viewmodel.TeamsViewModel

class TeamsFragment: Fragment() {

    private lateinit var binding: TeamsFragmentBinding
    private lateinit var teamsViewModel: TeamsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamsFragmentBinding.inflate(layoutInflater)
        teamsViewModel = ViewModelProvider(requireActivity())[TeamsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.team1TitleTv.text = getString(R.string.team1_name_example)
        binding.team2TitleTv.text = getString(R.string.team2_name_example)
        initListeners()
    }

    private fun initListeners() {
        binding.teamSwitch.setOnClickListener {
            if (binding.teamSwitch.isChecked) {
                binding.teamSwitch.text = getString(R.string.team2_name_example)
            } else {
                binding.teamSwitch.text = getString(R.string.team1_name_example)
            }
        }

        binding.addPlayerBtn.setOnClickListener {
            val name = binding.playerNameEt.text.toString()
            if (name.isNotBlank()) {
                teamsViewModel.addPlayer(!binding.teamSwitch.isChecked, name)
            }
        }
    }

}