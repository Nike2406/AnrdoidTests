package com.example.savestate

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.savestate.databinding.ActivityMainBinding
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

class SaveStateWithParcelable : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.incrementButton.setOnClickListener { increment() }
        binding.randomColorButton.setOnClickListener { setRandomColor() }
        binding.switchVisibilityButton.setOnClickListener { switchVisibility() }

        state = savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            counterValue = 0,
            counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
            counterIsVisible = true,
        )
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
    }

    private fun switchVisibility() {
        state.counterIsVisible = !state.counterIsVisible
        renderState()
    }

    private fun setRandomColor() {
        state.counterTextColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256),
        )
        renderState()
    }

    private fun increment() {
        state.counterValue++
        renderState()
    }

    private fun renderState() = with(binding) {
        counterText.text = state.counterValue.toString()
        counterText.setTextColor(state.counterTextColor)
        counterText.visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }

    /*
    * Для подключения аннотации Parcelize следует добавить
    * плагин id 'org.jetbrains.kotlin.plugin.parcelize' */
    @Parcelize
    class State(
        var counterValue: Int,
        var counterTextColor: Int,
        var counterIsVisible: Boolean,
    ) : Parcelable

    companion object {
        private const val KEY_STATE = "STATE"
    }
}