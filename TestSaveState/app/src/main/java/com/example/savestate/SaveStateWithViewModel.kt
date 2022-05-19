package com.example.savestate

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.savestate.databinding.ActivityMainBinding

class SaveStateWithViewModel : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<ViewModelLogic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.incrementButton.setOnClickListener { viewModel.increment() }
        binding.randomColorButton.setOnClickListener { viewModel.setRandomColor() }
        binding.switchVisibilityButton.setOnClickListener { viewModel.switchVisibility() }

        if (viewModel.state.value == null) {
            viewModel.initState(
                ViewModelLogic.State(
                    counterValue = 0,
                    counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                    counterIsVisible = true,
                )
            )
        }
        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: ViewModelLogic.State) = with(binding) {
        counterText.text = state.counterValue.toString()
        counterText.setTextColor(state.counterTextColor)
        counterText.visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }
}