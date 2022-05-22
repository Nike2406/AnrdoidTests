package com.example.simpleadapter

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleadapter.databinding.ActivityListViewBinding
import com.example.simpleadapter.databinding.DialogAddCharacterBinding
import kotlin.random.Random

class BaseAdapterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListViewBinding

    val data = mutableListOf(
        BaseCharacter(id = 1, name = " Reptile", isCustom = false),
        BaseCharacter(id = 2, name = " Raiden", isCustom = false),
        BaseCharacter(id = 3, name = " Subzero", isCustom = false),
        BaseCharacter(id = 4, name = " Scorpion", isCustom = false),
        BaseCharacter(id = 5, name = " Smoke", isCustom = false),
    )

    private lateinit var adapter: CharacterAdapterBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapterBase(data) {
            deleteCharacter(it)
        }
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            showCharacterInfo(adapter.getItem(position))
        }
    }

    private fun showCharacterInfo(character: BaseCharacter) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(character.name)
            .setMessage(getString(R.string.character_info, character.name, character.id))
            .setPositiveButton(R.string.ok) { _, _ -> }
            .create()
        dialog.show()
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create character")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { d, which ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) {
                    createCharacter(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = BaseCharacter(
            id = Random.nextLong(),
            name = name,
            isCustom = true,
        )
        data.add(character)
        adapter.notifyDataSetChanged()
    }

    private fun deleteCharacter(character: BaseCharacter) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                data.remove(character)
                adapter.notifyDataSetChanged()
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete character")
            .setMessage("Are you sure to delete the character ${character.name}")
            .setPositiveButton("OK", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }
}