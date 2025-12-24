package com.edwardalarik.app.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.databinding.FrgHomeBinding

class FrgHome : Fragment() {
    private lateinit var binding: FrgHomeBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val graphNavigation by lazy { NavHostFragment.Companion.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}