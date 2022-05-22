package com.example.simpleadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.simpleadapter.databinding.ItemCharacterBinding

typealias OnDeletePressedListener = (BaseCharacter) -> Unit

class CharacterAdapterBase(
    private val charactersBase: List<BaseCharacter>,
    private val onDeletePressedListener: OnDeletePressedListener,
) : BaseAdapter(), View.OnClickListener {

    override fun getCount(): Int {
        return charactersBase.size
    }

    override fun getItem(position: Int): BaseCharacter {
        return  charactersBase[position]
    }

    override fun getItemId(position: Int): Long {
        return charactersBase[position].id
    }

    // converView - нужен для оптимизации отрисовки
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            convertView?.tag as ItemCharacterBinding? ?:
            createBinding(parent.context)

        val character = getItem(position)

        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.deleteImageView.visibility = if (character.isCustom) View.VISIBLE else View.GONE

        return binding.root
    }

    override fun onClick(v: View) {
        val character = v.tag as BaseCharacter
        onDeletePressedListener.invoke(character)
    }

    private fun createBinding(context: Context): ItemCharacterBinding {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding
        return binding
    }
}

class BaseCharacter(
    val id: Long,
    val name: String,
    val isCustom: Boolean,
)