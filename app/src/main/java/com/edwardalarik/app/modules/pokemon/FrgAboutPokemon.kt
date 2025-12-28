package com.edwardalarik.app.modules.pokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.FrgAboutPokemonBinding
import com.edwardalarik.app.modules.pokemon.adapters.AdpAbility
import kotlin.getValue

class FrgAboutPokemon : Fragment() {
    private lateinit var binding: FrgAboutPokemonBinding
    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgAboutPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitValues()
        getData()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.source.objObserver.clear()
    }

    private fun setInitValues() {

    }

    private fun getData() {
        viewModel.source.objPokemon.getPokemon(viewModel.source.objVariable.idPokemon)
        viewModel.source.objPokemon.getAbilysPokemon(viewModel.source.objVariable.idPokemon)
    }

    private fun initObservers() {
        viewModel.source.objObserver.dataPokemon.observe(
            viewLifecycleOwner, ::DataPokemon
        )

        viewModel.source.objObserver.dataAbilitysPokemon.observe(
            viewLifecycleOwner, ::DataAbilitysPokemon
        )
    }

    @SuppressLint("SetTextI18n")
    private fun DataPokemon(data: KModels.Pokemon) {
        if (data.order != 0) {
            val weightKg = data.weight / 10.0
            binding.txtWeight.text = "$weightKg kg"

            val heightMeters = data.height / 10.0
            binding.txtHeight.text = "$heightMeters m"

            binding.txtSpecies.text = data.species

            binding.txtDescription.text = data.description
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun DataAbilitysPokemon(list: List<KModels.ListAbilitys>) {
        val abilityPokemon = list.map { it.name }

        binding.recyclerAbility.adapter = AdpAbility(
            abilityPokemon
        )

        binding.recyclerAbility.adapter?.notifyDataSetChanged()
    }
}