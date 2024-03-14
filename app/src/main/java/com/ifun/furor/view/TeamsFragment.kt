package com.ifun.furor.view

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.contains
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.ifun.furor.R
import com.ifun.furor.databinding.TeamsFragmentBinding
import com.ifun.furor.utils.ViewUtils
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
        teamsViewModel.getTeam1().observe(viewLifecycleOwner, Observer {
            binding.layoutTeam1Players.removeAllViews()
            val team1players = teamsViewModel.getTeam1().value
            for (player in team1players!!) {
                val playerBtn = createPlayerButton(player)
                binding.layoutTeam1Players.addView(playerBtn)
            }
        })

        teamsViewModel.getTeam2().observe(viewLifecycleOwner, Observer {
            binding.layoutTeam2Players.removeAllViews()
            val team2players = teamsViewModel.getTeam2().value
            for (player in team2players!!) {
                val playerBtn = createPlayerButton(player)
                binding.layoutTeam2Players.addView(playerBtn)
            }
        })

        initListeners()
    }

    private fun initListeners() {
        binding.switchTeams.setOnClickListener {
            if (binding.switchTeams.isChecked) {
                binding.team1SwitchText.visibility = View.GONE
                binding.team2SwitchText.visibility = View.VISIBLE
            } else {
                binding.team2SwitchText.visibility = View.GONE
                binding.team1SwitchText.visibility = View.VISIBLE
            }
        }

        binding.addBtn.setOnClickListener {
            val name = binding.playerNameEt.text.toString()
            if (name.isNotBlank()) {
                if (binding.switchTeams.isChecked) {
                    teamsViewModel.addPlayer(false, name)
                } else {
                    teamsViewModel.addPlayer(true, name)
                }
                binding.playerNameEt.text.clear()
                ViewUtils.hideSoftKeyboard(requireActivity())
            }
        }

        binding.startBtn.setOnClickListener {
            val action = TeamsFragmentDirections.actionTeamsFragmentToTestFragment(
                teamsViewModel.getTeam1().value!!.toTypedArray(),
                teamsViewModel.getTeam2().value!!.toTypedArray(),
                getString(R.string.team1_name_example),
                getString(R.string.team2_name_example)
            )
            findNavController().navigate(action)
        }
    }

    private fun createPlayerButton(name: String): LinearLayout {
        val context = requireActivity().applicationContext

        var btnLayout = LinearLayout(requireActivity())
        btnLayout.orientation = LinearLayout.HORIZONTAL
        btnLayout.gravity = Gravity.CENTER
        btnLayout.layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        btnLayout.setPadding(
            ViewUtils.getDpFromResources(context, context.resources.getDimension(R.dimen.players_layout_padding)),
            ViewUtils.getDpFromResources(context, context.resources.getDimension(R.dimen.players_layout_padding)),
            0,
            0
        )

        var removeImage = ImageView(requireActivity())
        removeImage.setImageResource(R.drawable.icon_remove)
        removeImage.setOnClickListener {
            if (binding.layoutTeam1Players.contains(btnLayout)) {
                teamsViewModel.removePlayer(true, name)
            } else {
                teamsViewModel.removePlayer(false, name)
            }
        }

        var nameTv = TextView(requireActivity())
        nameTv.text = name
        nameTv.textSize = 16F
        nameTv.setPadding(
            ViewUtils.getDpFromResources(context, context.resources.getDimension(R.dimen.player_text_padding_left)),
            0,
            0,
            0
        )

        btnLayout.addView(removeImage)
        btnLayout.addView(nameTv)

        return btnLayout
    }

}