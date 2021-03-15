package com.esmaeel.networkretrycalladapterlibrary

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.esmaeel.statelib.appHasNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Esmaeel Nabil on Mar, 2021
 */


/**
 *  Takes Current Activity in order to have a Context with a Theme of the App
 *  Shows the Dialog with a default callback that recreates the activity if onRetry is not provided
 */
internal fun Activity.showNetworkDialog(
    error: String? = "Default Network Error",
    onRetry: (() -> Unit?)? = null
) {
    val act = this
    /**
     *  On Main, cause we maybe are going to call this-
     *  in a background thread like an Interceptor or a flow builder with an IO.
     */
    GlobalScope.launch(Dispatchers.Main) {

        val view = act.layoutInflater.inflate(R.layout.no_internet_view, null, false)
        view?.let {
            val dialog = Dialog(act).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(view)
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

            dialog.show()

            view.findViewById<TextView>(R.id.details)?.let {
                it.text = error
            }

            view.findViewById<Button>(R.id.actionButton)?.let {
                it.setOnClickListener {
                    if (appHasNetwork) {
                        onRetry
                            ?.invoke()
                            ?: run {
                                act.recreate()
                            }

                        dialog.dismiss()
                    }
                }
            }
        }
    }
}