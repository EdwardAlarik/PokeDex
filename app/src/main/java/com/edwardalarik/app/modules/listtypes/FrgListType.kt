package com.edwardalarik.app.modules.listtypes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.FrgListTypeBinding

class FrgListType : Fragment() {
    private lateinit var binding: FrgListTypeBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val graphNavigation by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgListTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitValues()
        getData()
        initObservers()
    }

    private fun setInitValues() {
        binding.recycler.adapter = AdpListType(
            viewModel.source.objObserver.listTypes.value ?: ArrayList()
        )
    }

    private fun getData() {
        viewModel.source.objPokemon.getListType()
    }

    private fun initObservers() {
        viewModel.source.objObserver.listTypes.observe(
            viewLifecycleOwner, ::DataListType
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun DataListType(list: List<KModels.ListTypes>) {
        if (list.size > 0) {
            binding.recycler.adapter?.notifyDataSetChanged()
        }
    }
}