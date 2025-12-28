package com.edwardalarik.app.modules.pokemon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.FrgStatsPokemonBinding
import com.edwardalarik.app.modules.pokemon.adapters.AdpStat
import kotlin.getValue

class FrgStatsPokemon : Fragment() {
    private lateinit var binding: FrgStatsPokemonBinding
    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgStatsPokemonBinding.inflate(inflater, container, false)
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
        binding.recyclerStat.adapter = AdpStat(
            viewModel.source.objObserver.dataStatsPokemon.value ?: ArrayList()
        )
    }

    private fun getData() {
        viewModel.source.objPokemon.getStatsPokemon(viewModel.source.objVariable.idPokemon)
    }

    private fun initObservers() {
        viewModel.source.objObserver.dataStatsPokemon.observe(
            viewLifecycleOwner, ::DataStatsPokemon
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun DataStatsPokemon(list: List<KModels.ListStats>) {
        if (list.size > 0) {
            binding.recyclerStat.adapter?.notifyDataSetChanged()
        }
    }
}