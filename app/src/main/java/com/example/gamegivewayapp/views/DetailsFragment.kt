package com.example.gamegivewayapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gamegivewayapp.R
import com.example.gamegivewayapp.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso

class DetailsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private val giveaway by lazy {
        viewModel.giveaway
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.title.text = giveaway?.title
        binding.platform.text = "Platforms: ${giveaway?.platforms}"
        binding.price.text = "Worth: ${giveaway?.worth}"
        binding.status.text = "Status: ${giveaway?.status}"

        Picasso.get()
            .load(giveaway?.image)
            .resize(1000,1000)
            .into(binding.logo)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}