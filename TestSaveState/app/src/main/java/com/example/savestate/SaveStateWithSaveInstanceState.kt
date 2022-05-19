package com.example.savestate

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.savestate.databinding.ActivityMainBinding
import kotlin.properties.Delegates.notNull
import kotlin.random.Random

class SaveStateWithSaveInstanceState: AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var counterValue by notNull<Int>()
    private var counterTextColor by notNull<Int>()
    private var counterIsVisible by notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.incrementButton.setOnClickListener{ increment() }
        binding.randomColorButton.setOnClickListener { setRandomColor() }
        binding.switchVisibilityButton.setOnClickListener { switchVisibility() }

        if (savedInstanceState == null) {
            counterValue = 0
            counterTextColor = ContextCompat.getColor(this, R.color.purple_700)
            counterIsVisible = true
        } else {
            counterValue = savedInstanceState.getInt(KEY_COUNTER)
            counterTextColor = savedInstanceState.getInt(KEY_COLOR)
            counterIsVisible = savedInstanceState.getBoolean(KEY_IS_VISIBLE)
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNTER, counterValue)
        outState.putInt(KEY_COLOR, counterTextColor)
        outState.putBoolean(KEY_IS_VISIBLE, counterIsVisible)
    }

    private fun switchVisibility() {
        counterIsVisible = !counterIsVisible
        renderState()
    }

    private fun setRandomColor() {
        counterTextColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256),
        )
        renderState()
    }

    private fun increment() {
        counterValue++
        renderState()
    }

    private fun renderState() = with(binding) {
        counterText.text = counterValue.toString()
        counterText.setTextColor(counterTextColor)
        counterText.visibility = if (counterIsVisible) View.VISIBLE else View.INVISIBLE
    }


    companion object {
        private const val KEY_COUNTER = "COUNTER"
        private const val KEY_COLOR = "COLOR"
        private const val KEY_IS_VISIBLE = "IS_VISIBLE"
    }
}