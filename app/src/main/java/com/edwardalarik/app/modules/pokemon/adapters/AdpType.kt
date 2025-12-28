package com.edwardalarik.app.modules.pokemon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edwardalarik.app.api.extensions.toCapital
import com.edwardalarik.app.api.extensions.typeColor
import com.edwardalarik.app.databinding.ItemTypePokemonBinding

class AdpType(private val dataSet: List<String>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return viewType(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int ) {
        listenerTypes(holder as ViewHolderTypes, position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun viewType(viewGroup: ViewGroup): ViewHolder {
        val binding = ItemTypePokemonBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ViewHolderTypes(binding)
    }

    class ViewHolderTypes(private val binding: ItemTypePokemonBinding) :
        ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: String) {
            binding.nameType.text = item.toCapital()

            binding.containerType.setCardBackgroundColor(
                context.typeColor(
                    item
                )
            )
        }
    }

    private fun listenerTypes(viewHolder: ViewHolderTypes, position: Int) {
        viewHolder.bind(dataSet[position])
    }
}