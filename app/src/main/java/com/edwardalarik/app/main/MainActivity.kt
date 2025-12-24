package com.edwardalarik.app.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.edwardalarik.app.AppViewModel
import com.edwardalarik.app.R
import com.edwardalarik.app.api.logic.toastLong
import com.edwardalarik.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<AppViewModel>()
     private val graphNavigation by lazy { findNavController(R.id.flActivityFragment) }

    private fun requireContext(): Context = this

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 101
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    graphNavigation.navigate(R.id.actionHome)
                    true
                }
                R.id.listPokemon -> {
                    graphNavigation.navigate(R.id.actionListPokemon)
                    true
                }
                R.id.listTypes -> {
                    graphNavigation.navigate(R.id.actionListTypes)
                    true
                }
                R.id.settings -> {
                    this.toastLong("settings")
                    true
                }

                else -> false
            }
        }

    }

    override fun onStart() {
        super.onStart()

        checkPermissions()
    }

    private fun checkPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var allPermissionsGranted = false

        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = true
                break
            }
        }

        if (allPermissionsGranted) {
            onPermissionsGranted()
        } else {
            showPermissionsDeniedMessage()
        }
    }

    private fun onPermissionsGranted() {
        requireContext().toastLong("Permisos concedidos")
    }

    private fun showPermissionsDeniedMessage() {
        AlertDialog.Builder(this)
            .setTitle("Permiso de Notificaciones Denegado")
            .setMessage("Para recibir notificaciones, por favor habilita las notificaciones en la configuración de la aplicación.")
            .setPositiveButton("Ir a Configuración") { _, _ ->
                openNotificationSettings()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}