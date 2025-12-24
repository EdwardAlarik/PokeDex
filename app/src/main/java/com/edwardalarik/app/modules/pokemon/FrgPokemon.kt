package com.edwardalarik.app.modules.pokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.R
import com.edwardalarik.app.api.extensions.toCapital
import com.edwardalarik.app.api.extensions.typeColor
import com.edwardalarik.app.api.logic.onClickDisableListener
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.FrgPokemonBinding

class FrgPokemon : Fragment() {
    private lateinit var binding: FrgPokemonBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val graphNavigation by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitValues()
        getData()
        initObservers()

        binding.btnBack.onClickDisableListener {
            graphNavigation.navigate(R.id.actionListPokemon)
        }
    }

    private fun setInitValues() {

    }

    private fun getData() {
        if (viewModel.source.objVariable.existDataPokemon(viewModel.source.objVariable.idPokemon)) {
            viewModel.source.objPokemon.getPokemon(viewModel.source.objVariable.idPokemon)
        } else {
            viewModel.source.objNetwork.dataPokemon(viewModel.source.objVariable.idPokemon) {
                viewModel.source.objPokemon.getPokemon(viewModel.source.objVariable.idPokemon)
            }
        }
    }

    private fun initObservers() {
        viewModel.source.objObserver.dataPokemon.observe(
            viewLifecycleOwner, ::DataPokemon
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun DataPokemon(data: KModels.Pokemon) {
        binding.txtName.text = data.name.toCapital()

        val pokebola = ContextCompat.getDrawable(requireContext(), R.drawable.pokebola)
        val urlImagePokemon = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${data?.id}.png"

        Glide.with(binding.imagePokemon.context)
            .load(urlImagePokemon)
            .placeholder(pokebola)
            .error(pokebola)
            .into(binding.imagePokemon)

        val abilityPokemon = data.abilities.split(",").toMutableList()
        val typePokemon = data.types.split(",").toMutableList()
        val movePokemon = data.moves.split(",").toMutableList()

        for (i in abilityPokemon.indices) {
            val id = abilityPokemon[i].toInt()
            abilityPokemon[i] = viewModel.source.objPokemon.getAbility(id).lowercase()
        }

        for (i in typePokemon.indices) {
            val id = typePokemon[i].toInt()
            typePokemon[i] = viewModel.source.objPokemon.getType(id).lowercase()
        }

        binding.recyclerAbility.adapter = AdpAbility(
            abilityPokemon
        )
        binding.recyclerAbility.adapter?.notifyDataSetChanged()

        binding.recyclerType.adapter = AdpType(
            typePokemon
        )
        binding.recyclerType.adapter?.notifyDataSetChanged()

        binding.headerTitle.setBackgroundColor(
            requireContext().typeColor(
                typePokemon[0].lowercase()
            )
        )
    }
}