package com.edwardalarik.app.modules.listpokemon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.R
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.FrgListPokemonBinding
import kotlin.getValue

class FrgListPokemon : Fragment() {
    private lateinit var binding: FrgListPokemonBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val graphNavigation by lazy { NavHostFragment.Companion.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgListPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitValues()
        getData()
        initObservers()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.source.objPokemon.getListPokemonSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.source.objPokemon.getListPokemonSearch(it)
                }
                return true
            }
        })
    }

    private fun setInitValues() {
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recycler.adapter = AdpListPokemon(
            viewModel.source.objObserver.listPokemon.value ?: ArrayList()
        ).apply {
            onClick = ::pokemonDetails
        }
    }

    private fun getData() {
        viewModel.source.objPokemon.getListPokemon()
    }

    private fun initObservers() {
        viewModel.source.objObserver.listPokemon.observe(
            viewLifecycleOwner, ::DataListPokemon
        )
    }

    private fun pokemonDetails(model: KModels.ListPokemon) {
        viewModel.source.objVariable.idPokemon = model.id
        graphNavigation.navigate(R.id.actionPokemon)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun DataListPokemon(list: List<KModels.ListPokemon>) {
        if (list.size > 0) {
            binding.recycler.adapter?.notifyDataSetChanged()
        }
    }
}