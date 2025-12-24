package com.edwardalarik.app.modules.listpokemon

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwardalarik.app.R
import com.edwardalarik.app.api.extensions.toCapital
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.ItemPokemonBinding

class AdpListPokemon(private val dataSet: ArrayList<KModels.ListPokemon>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClick: (KModels.ListPokemon) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return viewListPokemon(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listenerPokemon(holder as ViewHolderPokemon, position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun viewListPokemon(viewGroup: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ViewHolderPokemon(binding)
    }

    class ViewHolderPokemon(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        @SuppressLint("DefaultLocale")
        fun bind(item: KModels.ListPokemon) {
            val pokebola = ContextCompat.getDrawable(context, R.drawable.pokebola)
            val urlImagePokemon =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${item.id}.png"

            Glide.with(binding.imagePokemon.context)
                .load(urlImagePokemon)
                .placeholder(pokebola)
                .error(pokebola)
                .into(binding.imagePokemon)

            if (item.order == 0) {
                val colorMatrix = ColorMatrix()
                colorMatrix.setSaturation(0f)
                val filter = ColorMatrixColorFilter(colorMatrix)

                binding.imagePokemon.colorFilter = filter
            }

            val numberFormatted = String.format("#%04d", item.id)

            binding.numberPokemon.text = numberFormatted
            binding.namePokemon.text = item.name.toCapital()
        }
    }

    private fun listenerPokemon(viewHolder: ViewHolderPokemon, position: Int) {
        viewHolder.bind(dataSet[position])

        viewHolder.itemView.setOnClickListener {
            onClick.invoke(dataSet[position])
        }
    }
}