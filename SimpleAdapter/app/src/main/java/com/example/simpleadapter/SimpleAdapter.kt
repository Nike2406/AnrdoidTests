package com.example.simpleadapter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.simpleadapter.databinding.ActivityMainBinding
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog

class SimpleAdapter : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupListViewSingle()
        setContentView(binding.root)
    }

    @SuppressLint("StringFormatInvalid")
    private fun setupListViewSingle() {

        val data = listOf(
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

        /*
        * Adapter - посредник между данными и элементами списка
        *
        * Для работы SimpleAdapter требуется импорт
        * android.widget.SimpleAdapter
        *
        * Конструктор: SimpleAdapter(
        *   Context                         context,
        *   List<? extends Map<String, ?>>  data,
        *   int                             resource,
        *   String[]                        from,
        *   int[]                           to
        * )
        * */

        val adapter = SimpleAdapter(

            // Origin
            this, // context
            data, // структура данных для передачи
            android.R.layout.simple_list_item_2, // макетный файл для вывода (готовый)
            arrayOf(KEY_TITLE, KEY_DESCRIPTION), // ключи из словарей
            intArrayOf(android.R.id.text1, android.R.id.text2), // id компонентов

            // Custom
//            this, // context
//            data, // структура данных для передачи
//            R.layout.simple_list_item, // макетный файл для вывода
//            arrayOf(KEY_TITLE, KEY_DESCRIPTION), // ключи из словарей
//            intArrayOf(R.id.listItemTitle, R.id.listItemDescription), // id компонентов
        )
        binding.listView.adapter = adapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItemTitle = data[position][KEY_TITLE]
            val selectedItemDescription = data[position][KEY_DESCRIPTION]

            val dialog = AlertDialog.Builder(this)
                .setTitle(selectedItemTitle)
                .setMessage(getString(R.string.item_selected_message, selectedItemDescription))
                .setPositiveButton("OK") {dialog, which -> }
                .create()
            dialog.show()
        }
    }


    companion object {
        const val KEY_TITLE = "title"
        const val KEY_DESCRIPTION = "description"
    }
}