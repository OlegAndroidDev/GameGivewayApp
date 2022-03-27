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
import com.example.gamegivewayapp.databinding.FragmentPCGiveawaysBinding
import com.example.gamegivewayapp.model.Giveaways
import com.example.gamegivewayapp.utils.GiveawayState
import com.example.gamegivewayapp.utils.PlatformType

class PCGiveawaysFragment : BaseFragment() {

    private val binding by lazy {
        FragmentPCGiveawaysBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.giveawaysRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = giveawaysAdapter
        }

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
        viewModel.getGiveawaysByPlatform()
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}