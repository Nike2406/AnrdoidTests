package com.example.simpleadapter

import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleadapter.databinding.ActivityMainBinding

class SimpleAdapterWithRandomGenerator : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupListViewWithSimpleGeneratorData()
        setContentView(binding.root)
    }

    private fun setupListViewWithSimpleGeneratorData() {
        val data = (1..100).map {
            mapOf(
                KEY_TITLE to "Item #$it",
                KEY_DESCRIPTION to "Description #$it",
            )
        }

        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.simple_list_item,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(R.id.listItemTitle, R.id.text2),
        )
        binding.listView.adapter = adapter
    }


    companion object {
        const val KEY_TITLE = "title"
        const val KEY_DESCRIPTION = "description"
    }
}