package com.esmaeel.networkretrycalladapter.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.esmaeel.networkretrycalladapter.R
import com.esmaeel.networkretrycalladapter.data.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private var apiService = ServiceLocator.getApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.IO) {

            val data = apiService.getUsers().data

            withContext(Dispatchers.Main) {
                findViewById<TextView>(R.id.dataText).text = data.toString()
            }

        }

    }
}