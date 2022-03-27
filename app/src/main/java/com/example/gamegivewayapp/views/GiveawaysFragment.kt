package com.example.gamegivewayapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamegivewayapp.R
import com.example.gamegivewayapp.adapter.GiveawaysAdapter
import com.example.gamegivewayapp.databinding.FragmentGiveawaysBinding
import com.example.gamegivewayapp.model.Giveaways
import com.example.gamegivewayapp.utils.GiveawayState
import com.example.gamegivewayapp.utils.NewPlatformType
import com.example.gamegivewayapp.utils.PlatformType

class GiveawaysFragment : BaseFragment() {

    private val binding by lazy {
        FragmentGiveawaysBinding.inflate(layoutInflater)
    }

//    private val giveawaysAdapter by lazy {
//        GiveawaysAdapter(onGiveawayClicked ={
//            viewModel.giveaway = it
//        })
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.giveawaysRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            adapter = giveawaysAdapter
        }


//        binding.goToPs4.setBackgroundColor(resources.getColor(R.color.blue))

        viewModel.giveaways.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GiveawayState.LOADING -> {
                    Toast.makeText(
                        requireContext(), "Loading ....", Toast.LENGTH_LONG
                    ).show()
                }
                is GiveawayState.SUCCESS<*> -> {
                    val giveaways = state.giveaways as List<Giveaways>
                    giveawaysAdapter.setNewGiveaways(giveaways)
                }
                is GiveawayState.ERROR -> {
                    Toast.makeText(
                        requireContext(), state.error.localizedMessage, Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.getSortedGiveaways()

        binding.PC.setOnClickListener {
            viewModel.platform = PlatformType.PC
            viewModel.databasePlatform = NewPlatformType.PC
            findNavController().navigate(R.id.action_giveaways_navigation_to_pc_navigation)
        }

        binding.PS.setOnClickListener {
            viewModel.platform = PlatformType.PS4
            viewModel.databasePlatform = NewPlatformType.PS4
            findNavController().navigate(R.id.action_giveaways_navigation_to_ps4_navigation)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}