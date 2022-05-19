package com.example.savestate

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.savestate.databinding.ActivityMainBinding

class SaveStateWithViewModelParce : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<ViewModelLogicParce>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.incrementButton.setOnClickListener { viewModel.increment() }
        binding.randomColorButton.setOnClickListener { viewModel.setRandomColor() }
        binding.switchVisibilityButton.setOnClickListener { viewModel.switchVisibility() }

        viewModel.initState(
            savedInstanceState?.getParcelable(KEY_STATE) ?: ViewModelLogicParce.State(
                counterValue = 0,
                counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                counterIsVisible = true,
            )
        )
        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, viewModel.state.value)
    }

    private fun renderState(state: ViewModelLogicParce.State) = with(binding) {
        counterText.text = state.counterValue.toString()
        counterText.setTextColor(state.counterTextColor)
        counterText.visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        @JvmStatic
        val KEY_STATE = "STATE"
    }
}