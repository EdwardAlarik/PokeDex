package com.edwardalarik.app.api.logic

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationCompat
import com.edwardalarik.app.R

class KUtils {
    companion object {
        fun freeMemory() {
            System.runFinalization()
            Runtime.getRuntime().freeMemory()
            Runtime.getRuntime().gc()
            System.gc()
        }

        fun fromHtml(html: String): Spanned {
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(html)
            }
            return result
        }

        fun hideKeyboard(activity: Activity) {
            val view = activity.currentFocus
            if (view != null) {
                val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                view.clearFocus()
            }
        }

        fun createNotificationWorker(
            context: Context,
            title: String,
            text: String = "",
            idChanel: String,
            onGoing: Boolean = true,
            icon: Int = R.drawable.img_splash,
            priority: Int = NotificationCompat.PRIORITY_LOW,
            silent: Boolean = true,
        ): Notification {
            return NotificationCompat.Builder(context, idChanel)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setTicker(title)
                .setSilent(silent)
                .setContentText(text)
                .setChannelId(idChanel)
                .setOngoing(onGoing)
                .setPriority(priority)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .build()
        }
    }
}