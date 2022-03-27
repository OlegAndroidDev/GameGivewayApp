package com.example.gamegivewayapp.views

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gamegivewayapp.R
import com.example.gamegivewayapp.adapter.GiveawaysAdapter
import com.example.gamegivewayapp.viewmodel.GiveawaysViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class BaseFragment : Fragment() {

    protected val viewModel : GiveawaysViewModel by sharedViewModel()

    protected val giveawaysAdapter by lazy {
        GiveawaysAdapter(onGiveawayClicked ={
            viewModel.giveaway = it
            findNavController().navigate(R.id.details_navigation)
        })
    }
}