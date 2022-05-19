package com.example.savestate

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.savestate.databinding.ActivityMainBinding
import java.io.Serializable
import kotlin.random.Random

class SaveStateWithSerialization : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.incrementButton.setOnClickListener { increment() }
        binding.randomColorButton.setOnClickListener { setRandomColor() }
        binding.switchVisibilityButton.setOnClickListener { switchVisibility() }

        state = if (savedInstanceState == null) {
            State(
                counterValue = 0,
                counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                counterIsVisible = true,
            )
        } else {
            savedInstanceState.getSerializable(KEY_STATE) as State
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STATE, state)
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
    * Если класс сериализуемый, то его можно положить в bundle
    *
    * Serializable - занимает много места и работает медленно +
    * y Bundle есть ограничение по объему (500kb).
    * Лучше использовать Parcelable */
    class State(
        var counterValue: Int,
        var counterTextColor: Int,
        var counterIsVisible: Boolean,
    ) : Serializable

    companion object {
        private const val KEY_STATE = "STATE"
    }
}