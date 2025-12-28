package com.edwardalarik.app.modules.pokemon

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
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
import com.edwardalarik.app.modules.pokemon.adapters.AdpType
import com.edwardalarik.app.modules.pokemon.tabs.TabsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FrgPokemon : Fragment() {
    private lateinit var binding: FrgPokemonBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val graphNavigation by lazy { NavHostFragment.findNavController(this) }
    private var checkFavorite: Int = 0
    private var audioUrl: String = ""
    lateinit var mediaPlayer: MediaPlayer

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

        binding.btnFav.onClickDisableListener {
            viewModel.source.objPokemon.checkFav(
                viewModel.source.objVariable.idPokemon,
                checkFavorite
            )
            getData()
        }

        mediaPlayer = MediaPlayer()

        binding.btnPlay.onClickDisableListener {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(audioUrl)

                setOnPreparedListener {
                    it.start()
                }

                setOnCompletionListener {
                    it.release()
                }
                prepareAsync()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.source.objObserver.clear()
        mediaPlayer.release()
    }

    private fun setInitValues() {
        binding.tabPokemon.setSelectedTabIndicatorColor(
            ContextCompat.getColor(requireContext(), android.R.color.black)
        )

        binding.tabPokemon.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.white)
        )

        binding.tabPokemon.setTabTextColors(
            ContextCompat.getColor(requireContext(), android.R.color.black),
            ContextCompat.getColor(requireContext(), android.R.color.black)
        )

        binding.tabPokemon.tabMode = TabLayout.MODE_FIXED
        binding.tabPokemon.isInlineLabel = true

        val adapter = TabsPagerAdapter(
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )

        binding.viewpagerPokemon.adapter = adapter
        binding.viewpagerPokemon.isUserInputEnabled = true

        TabLayoutMediator(binding.tabPokemon, binding.viewpagerPokemon) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "About"
                    //tab.setIcon(R.drawable.ic_home)
                }

                1 -> {
                    tab.text = "Stats"
                    //tab.setIcon(R.drawable.ic_home)
                }

                /*2 -> {
                    tab.text = "Evolution"
                    //tab.setIcon(R.drawable.ic_home)
                }

                3 -> {
                    tab.text = "Moves"
                    //tab.setIcon(R.drawable.ic_home)
                }

                4 -> {
                    tab.text = "Varieties"
                    //tab.setIcon(R.drawable.ic_home)
                }*/
            }

            tab.icon?.colorFilter =
                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    ContextCompat.getColor(requireContext(), android.R.color.white),
                    BlendModeCompat.SRC_ATOP
                )
        }.attach()
    }

    private fun getData() {
        if (viewModel.source.objVariable.existDataPokemon(viewModel.source.objVariable.idPokemon)) {
            viewModel.source.objPokemon.getPokemon(viewModel.source.objVariable.idPokemon)
            viewModel.source.objPokemon.getTypesPokemon(viewModel.source.objVariable.idPokemon)
        } else {
            viewModel.source.objNetwork.dataPokemon(viewModel.source.objVariable.idPokemon) {
                viewModel.source.objNetwork.dataPokemonSpecies(viewModel.source.objVariable.idPokemon) {
                    viewModel.source.objPokemon.getPokemon(viewModel.source.objVariable.idPokemon)
                    viewModel.source.objPokemon.getTypesPokemon(viewModel.source.objVariable.idPokemon)
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.source.objObserver.dataPokemon.observe(
            viewLifecycleOwner, ::DataPokemon
        )

        viewModel.source.objObserver.dataTypePokemon.observe(
            viewLifecycleOwner, ::DataTypesPokemon
        )
    }

    private fun DataPokemon(data: KModels.Pokemon) {
        if (data.order != 0) {
            binding.headerTitle.setBackgroundColor(
                requireContext().typeColor(
                    data.types.lowercase()
                )
            )

            binding.txtName.text = data.name.toCapital()

            val pokebola = ContextCompat.getDrawable(requireContext(), R.drawable.pokebola)
            val urlImagePokemon =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${data.id}.png"

            Glide.with(binding.imagePokemon.context)
                .load(urlImagePokemon)
                .placeholder(pokebola)
                .error(pokebola)
                .into(binding.imagePokemon)

            checkFavorite = data.fav
            if (data.fav == 1) {
                binding.btnFav.setImageResource(R.drawable.ic_fav_enable)
            } else {
                binding.btnFav.setImageResource(R.drawable.ic_fav_disable)
            }
            audioUrl = data.cries_latest
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun DataTypesPokemon(list: List<KModels.ListTypes>) {
        val typePokemon = list.map { it.name }

        binding.recyclerType.adapter = AdpType(
            typePokemon
        )

        binding.recyclerType.adapter?.notifyDataSetChanged()
    }
}