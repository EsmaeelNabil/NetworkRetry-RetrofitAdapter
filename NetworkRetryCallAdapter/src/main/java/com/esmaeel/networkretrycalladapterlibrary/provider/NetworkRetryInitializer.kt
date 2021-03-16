package com.esmaeel.networkretrycalladapterlibrary.provider

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import com.esmaeel.statelib.initNetworkStateHandler
import com.esmaeel.statelib.registerActivityTracker

/**
 * Created by Esmaeel Nabil on Mar, 2021
 */


open class NetworkRetryInitializer() : ContentProvider() {

    override fun onCreate(): Boolean {
        (context?.applicationContext as Application).apply {
            registerActivityTracker()
            initNetworkStateHandler()
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ) = throw UnsupportedOperationException("Don't play with this content provider")

    override fun getType(uri: Uri) =
        throw UnsupportedOperationException("Don't play with this content provider")

    override fun insert(uri: Uri, values: ContentValues?) =
        throw UnsupportedOperationException("Don't play with this content provider")

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) =
        throw UnsupportedOperationException("Don't play with this content provider")

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ) = throw UnsupportedOperationException("Don't play with this content provider")

}