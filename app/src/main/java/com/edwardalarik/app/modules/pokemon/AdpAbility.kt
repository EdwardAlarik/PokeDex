package com.edwardalarik.app.modules.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edwardalarik.app.api.extensions.toCapital
import com.edwardalarik.app.databinding.ItemAbilityPokemonBinding

class AdpAbility(private val dataSet: List<String>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewAbility: Int): ViewHolder {
        return viewAbility(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int ) {
        listenerAbility(holder as ViewHolderAbility, position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun viewAbility(viewGroup: ViewGroup): ViewHolder {
        val binding = ItemAbilityPokemonBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ViewHolderAbility(binding)
    }

    class ViewHolderAbility(private val binding: ItemAbilityPokemonBinding) :
        ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: String) {
            binding.nameType.text = item.toCapital()
        }
    }

    private fun listenerAbility(viewHolder: ViewHolderAbility, position: Int) {
        viewHolder.bind(dataSet[position])

        viewHolder.itemView.setOnClickListener {

        }
    }
}