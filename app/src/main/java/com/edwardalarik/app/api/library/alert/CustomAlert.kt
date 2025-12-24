package com.edwardalarik.app.api.library.alert

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.edwardalarik.app.R
import com.edwardalarik.app.api.logic.KUtils
import com.edwardalarik.app.api.logic.TypeAlert
import com.edwardalarik.app.api.logic.gone
import com.edwardalarik.app.api.logic.invisible
import com.edwardalarik.app.api.logic.onClickDisableListener
import com.edwardalarik.app.api.logic.visible
import com.edwardalarik.app.databinding.LayoutAlertBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CustomAlert(val context: Context) {
    private var dialog: Dialog? = null
    private val alertBinding by lazy { LayoutAlertBinding.inflate(LayoutInflater.from(context)) }
    private val scope by lazy { CoroutineScope(SupervisorJob()) }

    private var onPositive: () -> Unit = {}
    private var onNegative: () -> Unit = {}
    private var onDismiss: () -> Unit = {}

    init {
        alertBinding.imgIcon.invisible()
        alertBinding.btnClose.onClickDisableListener { dismissManually() }
    }

    fun setOnPositive(onReady: () -> Unit) {
        onPositive = onReady
    }

    fun setOnNegative(onReady: () -> Unit) {
        onNegative = onReady
    }

    fun setData(titleAlert: String) {
        alertBinding.tvTitle.text = titleAlert
        alertBinding.btnPositive.visibility = View.GONE
        alertBinding.btnNegative.visibility = View.GONE
        alertBinding.viewDivider.visibility = View.GONE
        alertBinding.tvMessage.visibility = View.GONE
    }

    fun setData(titleAlert: String, positiveBtn: String) {
        alertBinding.tvTitle.text = titleAlert
        alertBinding.btnPositive.text = positiveBtn
        alertBinding.btnPositive.visibility = View.VISIBLE
        alertBinding.btnNegative.visibility = View.GONE
        alertBinding.viewDivider.visibility = View.GONE
        alertBinding.tvMessage.visibility = View.GONE
    }

    fun setData(titleAlert: String, messageAlert: String, positiveBtn: String, asHTML: Boolean = false) {
        alertBinding.tvTitle.text = titleAlert
        alertBinding.tvMessage.text = if (asHTML) KUtils.fromHtml(messageAlert) else messageAlert
        alertBinding.btnPositive.text = positiveBtn
        alertBinding.tvMessage.visibility = View.VISIBLE
        alertBinding.btnPositive.visibility = View.VISIBLE
        alertBinding.btnNegative.visibility = View.GONE
        alertBinding.viewDivider.visibility = View.GONE
    }

    fun setDataSimple(titleAlert: String, positiveBtn: String, negativeBtn: String) {
        alertBinding.tvTitle.text = titleAlert
        alertBinding.btnPositive.text = positiveBtn
        alertBinding.btnNegative.text = negativeBtn
        alertBinding.btnPositive.visibility = View.VISIBLE
        alertBinding.btnNegative.visibility = View.VISIBLE
        alertBinding.tvMessage.visibility = View.GONE
    }

    fun setData(
        titleAlert: String,
        messageAlert: String,
        positiveBtn: String,
        negativeBtn: String,
        asHTML: Boolean = false
    ) {
        alertBinding.tvTitle.text = titleAlert
        if (messageAlert.isEmpty()) {
            alertBinding.tvMessage.gone()
        }
        else {
            alertBinding.tvMessage.visible()
            if (asHTML) {
                alertBinding.tvMessage.text = KUtils.fromHtml(messageAlert)
            }
            else {
                alertBinding.tvMessage.text = messageAlert
            }
        }
        alertBinding.btnPositive.text = positiveBtn
        alertBinding.btnNegative.text = negativeBtn
        alertBinding.btnPositive.visibility = View.VISIBLE
        alertBinding.btnNegative.visibility = View.VISIBLE
        alertBinding.viewDivider.visibility = View.VISIBLE
    }

    fun show(
        type: TypeAlert,
        cancelable: Boolean = true,
        closeOnClick: Boolean = true,
        animation: Boolean = true,
    ) {
        updateView(type, animation, closeOnClick)
        showDialog(cancelable)
    }

    private fun showDialog(cancelable: Boolean) {
        scope.launch(Dispatchers.Main) {
            if (cancelable) alertBinding.btnClose.visible()
            else alertBinding.btnClose.gone()

            dialog = MaterialAlertDialogBuilder(context)
                .setView(alertBinding.root)
                .setCancelable(cancelable)
                .setOnDismissListener {
                    onDismiss.invoke()
                    KUtils.freeMemory()
                }
                .show()
        }
    }

    private fun setOnClick(closeOnClick: Boolean) {
        alertBinding.btnPositive.onClickDisableListener {
            onPositive.invoke()
            if (closeOnClick) dismissManually()
        }

        alertBinding.btnNegative.onClickDisableListener {
            onNegative.invoke()
            if (closeOnClick) dismissManually()
        }
    }

    fun updateView(type: TypeAlert, animation: Boolean = true, closeOnClick: Boolean = true) = scope.launch(
        Dispatchers.Main) {

        setOnClick(closeOnClick)

        if (type == TypeAlert.Loading) {
            alertBinding.imgIcon.invisible()
            alertBinding.lottieAnimationView.visible()
            alertBinding.lottieAnimationView.setAnimation(R.raw.animation_loading)
            alertBinding.lottieAnimationView.repeatMode = LottieDrawable.RESTART
            alertBinding.lottieAnimationView.repeatCount = LottieDrawable.INFINITE
            alertBinding.lottieAnimationView.playAnimation()
        } else if (animation) {
            alertBinding.imgIcon.invisible()
            alertBinding.lottieAnimationView.visible()

            alertBinding.lottieAnimationView.setAnimation(
                when (type) {
                    TypeAlert.Success -> R.raw.animation_success
                    TypeAlert.Error -> R.raw.animation_error
                    TypeAlert.Warning -> R.raw.animation_warning
                    TypeAlert.Question -> R.raw.animation_question
                    TypeAlert.Info -> R.raw.animation_information
                    else -> R.raw.animation_loading
                }
            )
            alertBinding.lottieAnimationView.repeatMode = LottieDrawable.RESTART
            alertBinding.lottieAnimationView.repeatCount = 0
            alertBinding.lottieAnimationView.playAnimation()
        } else {
            alertBinding.imgIcon.visible()
            alertBinding.lottieAnimationView.gone()

            Glide.with(context).asDrawable().load(
                when (type) {
                    TypeAlert.Success -> R.drawable.ic_circle_check
                    TypeAlert.Error -> R.drawable.ic_circle_cancel
                    TypeAlert.Warning -> R.drawable.ic_circle_warning
                    TypeAlert.Question -> R.drawable.ic_circle_question
                    else -> R.drawable.ic_circle_info
                }
            ).into(alertBinding.imgIcon)

            alertBinding.imgIcon.imageTintList = (ContextCompat.getColorStateList(context,
                when (type) {
                    TypeAlert.Success -> R.color.success
                    TypeAlert.Error -> R.color.danger
                    TypeAlert.Warning -> R.color.warning
                    TypeAlert.Question -> R.color.question
                    TypeAlert.Info -> R.color.info
                    else -> R.color.color_base
                }
            ))
        }
    }

    fun dismissManually() {
        dialog?.dismiss()
    }
}