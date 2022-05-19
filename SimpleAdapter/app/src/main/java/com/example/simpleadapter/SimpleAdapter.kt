package com.example.simpleadapter

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simpleadapter.databinding.ActivityMainBinding

class SimpleAdapter : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupListViewSingle()
        setContentView(binding.root)
    }

    private fun setupListViewSingle() {

        val adapterElements = listOf(
            mapOf(
                KEY_TITLE to "First title",
                KEY_DESCRIPTION to "First description"
            ),
            mapOf(
                KEY_TITLE to "Second title",
                KEY_DESCRIPTION to "Second description"
            ),
            mapOf(
                KEY_TITLE to "Third title",
                KEY_DESCRIPTION to "Third description"
            ),
        )
    }

    companion object {
        @JvmStatic val KEY_TITLE = "title"
        @JvmStatic val KEY_DESCRIPTION = "description"
    }
}