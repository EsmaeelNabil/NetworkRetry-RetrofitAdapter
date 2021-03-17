package com.esmaeel.networkretry

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.esmaeel.networkretry.databinding.NoInternetViewBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * Created by Esmaeel Nabil on Mar, 2021
 */

class DialogManager @Inject constructor(@ActivityContext val context: Context) {

    fun showNetworkDialog(
        message: String = "",
        tryAgainButtonText: String = "Try again!",
        onRetry: () -> Unit
    ) {
        AlertDialog.Builder(context).also {
            it.setMessage(message)
                .setPositiveButton(tryAgainButtonText) { dialog, which -> onRetry() }
        }.show()
    }

    fun showNetworkScreen(
        titleText: String? = null,
        message: String? = null,
        tryAgainButtonText: String? = null,
        imageResources: Int? = null,
        onRetry: () -> Unit
    ) {

        val activity = context as Activity
        NoInternetViewBinding.inflate(activity.layoutInflater, null, false).also { view ->

            Dialog(activity).also {
                with(it) {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setContentView(view.root)
                    setCanceledOnTouchOutside(false)
                    setCancelable(false)
                    with(window) {
                        this?.let {
                            setBackgroundDrawableResource(android.R.color.white)
                            setLayout(
                                ConstraintLayout.LayoutParams.MATCH_PARENT,
                                ConstraintLayout.LayoutParams.MATCH_PARENT
                            )
                        }
                    }
                }
            }.show()

            with(view) {
                with(title) {
                    titleText?.let {
                        text = it
                    } ?: run {
                        text = activity.getString(R.string.oops)
                    }
                }

                with(details) {
                    message?.let {
                        text = it
                    } ?: run {
                        text =
                            activity.getString(R.string.no_internet)
                    }
                }

                with(image) {
                    imageResources?.let {
                        setImageResource(it)
                    } ?: run { setImageResource(R.drawable.ic_earth) }
                }

                with(actionButton) {
                    setOnClickListener { onRetry() }
                    tryAgainButtonText?.let {
                        text = it
                    } ?: run { text = activity.getString(R.string.retry) }
                }
            }
        }
    }

}