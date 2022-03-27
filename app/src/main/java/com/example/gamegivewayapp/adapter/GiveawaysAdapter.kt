package com.example.gamegivewayapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamegivewayapp.R
import com.example.gamegivewayapp.databinding.GiveawayItemBinding
import com.example.gamegivewayapp.model.Giveaways
import com.squareup.picasso.Picasso

class GiveawaysAdapter(
    private val giveaways: MutableList<Giveaways> = mutableListOf(),
    private val onGiveawayClicked: (Giveaways) -> Unit
) : RecyclerView.Adapter<GiveawayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveawayViewHolder {
        val view = GiveawayItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
            )
        return GiveawayViewHolder(view, onGiveawayClicked)
    }

    override fun onBindViewHolder(holder: GiveawayViewHolder, position: Int) {
        holder.bind(giveaways[position])
    }

    override fun getItemCount(): Int = giveaways.size

    fun setNewGiveaways(newGiveaways: List<Giveaways>) {
        giveaways.clear()
        giveaways.addAll(newGiveaways)
        notifyDataSetChanged()
    }
}

class GiveawayViewHolder(
    private val binding: GiveawayItemBinding,
    private val onGiveawayClicked: (Giveaways) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(giveaways: Giveaways) {
        binding.date.text = giveaways.endDate
        binding.platform.text = giveaways.platforms
        binding.status.text = giveaways.status
        binding.title.text = giveaways.title
        binding.price.text = giveaways.worth

        Picasso.get()
            .load(giveaways.thumbnail)
            .resize(100, 100)
            .into(binding.logo)

        binding.giveawayItem.setOnClickListener {
            onGiveawayClicked.invoke(giveaways)
        }

    }
}