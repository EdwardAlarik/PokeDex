package com.edwardalarik.app.modules.pokemon.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edwardalarik.app.modules.home.FrgHome
import com.edwardalarik.app.modules.pokemon.FrgAboutPokemon
import com.edwardalarik.app.modules.pokemon.FrgStatsPokemon

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FrgAboutPokemon().withArgs("About")
            1 -> FrgStatsPokemon().withArgs("Stats")
            /*2 -> FrgHome().withArgs("Evolution")
            3 -> FrgHome().withArgs("Moves")
            4 -> FrgHome().withArgs("Varieties")*/
            else -> FrgHome()
        }
}

private fun Fragment.withArgs(name: String): Fragment {
    arguments = Bundle().apply {
        putString("fragmentName", name)
    }
    return this
}
