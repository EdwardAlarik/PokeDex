package com.edwardalarik.app.api.logic

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.postDelayed
import com.edwardalarik.app.BuildConfig
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File

class KTE {
    companion object {
        const val NUM_CHANEL_NOT_SEND = 96224
        const val NUM_CHANEL_SECURITY = 96225
        const val NUM_CHANEL_REMINDER = 96226
        const val NUM_CHANEL_DESCARGA = 96227
        const val NUM_CHANEL_CARGA = 96228
        const val NUM_CHANEL_UBICACION = 96229

        const val ID_CHANEL_NOT_SEND = "Falla de servidor"
        const val ID_CHANEL_SECURITY = "Seguridad"
        const val ID_CHANEL_REMINDER = "Recordatorios"
        const val ID_CHANEL_DESCARGA = "Descarga de datos"
        const val ID_CHANEL_CARGA = "Carga de datos"
        const val ID_CHANEL_UBICACION = "Monitoreo de dispositivo"
        const val ID_CHANEL_ERRORES = "Errores de red"
    }
}

enum class TypeAlert { Success, Warning, Error, Question, Info, Loading }

enum class TypeData { Success, Progress, Warning, Error, Config, Info }

fun View.visible() {
    post { this.visibility = View.VISIBLE }
}

fun View.invisible() {
    post { this.visibility = View.INVISIBLE }
}

fun View.gone() {
    post { this.visibility = View.GONE }
}

fun TextInputLayout.setText(str: String = "") {
    post { this.editText?.setText(str) }
}

fun Context.toastShort(message: String) {
    CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
        Toast.makeText(this@toastShort, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.toastLong(message: String) {
    CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
        Toast.makeText(this@toastLong, message, Toast.LENGTH_LONG).show()
    }
}

fun View.onClickDisableListener(onClickListener: () -> Unit) {
    post {
        this.setOnClickListener {
            this.isEnabled = false
            this.postDelayed(1000) { this.isEnabled = true }
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            onClickListener.invoke()
        }
    }
}

fun Context.copyToClipboard(tag: String, text: String) {
    CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
        val clipboardManager =
            this@copyToClipboard.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(tag, text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this@copyToClipboard, tag, Toast.LENGTH_LONG).show()
    }
}

fun View.hideKeyboard() {
    val inputManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
    this.clearFocus()
}

fun Context.retrofitCache(): File {
    return File(this.filesDir,"RetrofitCache").apply {
        if (!BuildConfig.DEBUG) deleteRecursively()
        mkdir()
    }
}