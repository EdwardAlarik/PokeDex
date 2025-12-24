package com.edwardalarik.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import com.edwardalarik.app.api.logic.KTE
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppApplication : Application(), androidx.work.Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var source: AppDataSource

    override val workManagerConfiguration: androidx.work.Configuration
        get() = androidx.work.Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        source.executorService.execute {
            initNotificationChanel()
        }
    }

    private fun initNotificationChanel() {
        val notificationManager: NotificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(
            NotificationChannel(
                KTE.ID_CHANEL_SECURITY,
                KTE.ID_CHANEL_SECURITY,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
        notificationManager.createNotificationChannel(
            NotificationChannel(
                KTE.ID_CHANEL_REMINDER,
                KTE.ID_CHANEL_REMINDER,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
        notificationManager.createNotificationChannel(
            NotificationChannel(
                KTE.ID_CHANEL_DESCARGA,
                KTE.ID_CHANEL_DESCARGA,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
        notificationManager.createNotificationChannel(
            NotificationChannel(
                KTE.ID_CHANEL_CARGA,
                KTE.ID_CHANEL_CARGA,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
        notificationManager.createNotificationChannel(
            NotificationChannel(
                KTE.ID_CHANEL_UBICACION,
                KTE.ID_CHANEL_UBICACION,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
        notificationManager.createNotificationChannel(
            NotificationChannel(
                KTE.ID_CHANEL_ERRORES,
                KTE.ID_CHANEL_ERRORES,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
        notificationManager.createNotificationChannel(
            NotificationChannel(
                KTE.ID_CHANEL_NOT_SEND,
                KTE.ID_CHANEL_NOT_SEND,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }

    private fun requireContext(): AppApplication = this
}