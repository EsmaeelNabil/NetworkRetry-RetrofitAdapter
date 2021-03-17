package com.esmaeel.networkretry.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.esmaeel.networkretry.R
import com.esmaeel.networkretry.data.ServiceLocator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var serviceLocator: ServiceLocator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.IO) {
            try {

                val data = serviceLocator.apiService().getUsers().data

                withContext(Dispatchers.Main) {
                    findViewById<TextView>(R.id.dataText).text = data.toString()
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        val ee = e.response().toString() ?: "failed to get errorbody"
                        Toast.makeText(applicationContext, ee, Toast.LENGTH_SHORT).show()
                    }

                }

            }

        }

    }
}