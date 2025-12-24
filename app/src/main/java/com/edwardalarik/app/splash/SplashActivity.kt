package com.edwardalarik.app.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.R
import com.edwardalarik.app.databinding.ActivitySplashBinding
import com.edwardalarik.app.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private fun requireActivity(): Activity = this
    private fun requiereContext(): Context = this
    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lottieAnimationView: LottieAnimationView = binding.lottieAnimationView
        lottieAnimationView.setAnimation(R.raw.animation_splash)
        lottieAnimationView.repeatMode = LottieDrawable.RESTART
        lottieAnimationView.repeatCount = LottieDrawable.INFINITE
        lottieAnimationView.playAnimation()

        viewModel.source.objNetwork.listAbility(0, 1000) {
            viewModel.source.objNetwork.listMove(0, 1000) {
                viewModel.source.objNetwork.listType(0, 1000) {
                    viewModel.source.objNetwork.listPokemon(0, 1000) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}