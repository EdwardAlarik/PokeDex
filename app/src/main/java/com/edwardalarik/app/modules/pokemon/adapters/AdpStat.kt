package com.edwardalarik.app.modules.pokemon.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edwardalarik.app.api.extensions.typeColor
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.ItemStatPokemonBinding

class AdpStat(private val dataSet: ArrayList<KModels.ListStats>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return viewStat(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int ) {
        listenerStat(holder as ViewHolderTypes, position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun viewStat(viewGroup: ViewGroup): ViewHolder {
        val binding = ItemStatPokemonBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ViewHolderTypes(binding)
    }

    class ViewHolderTypes(private val binding: ItemStatPokemonBinding) :
        ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: KModels.ListStats) {
            binding.name.text = item.name
            binding.stat.text = item.base_stat.toString()
            binding.progressStat.progress = item.base_stat
            binding.progressStat.progressTintList =
                ColorStateList.valueOf(
                    context.typeColor(
                        item.type
                    )
                )
        }
    }

    private fun listenerStat(viewHolder: ViewHolderTypes, position: Int) {
        viewHolder.bind(dataSet[position])
    }
}