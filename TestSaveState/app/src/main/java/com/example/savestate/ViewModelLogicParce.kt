package com.example.savestate

import android.graphics.Color
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

class ViewModelLogicParce : ViewModel() {

    /*
    * Позвооляет менять сосотояние только во viewModel,
    * не изменяя его в activity */
    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState(state: State) {
        stateLiveData.value = state
    }

    fun increment() {
        val oldState = stateLiveData.value
        stateLiveData.value = oldState?.copy(
            counterValue = oldState.counterValue + 1
        )
    }

    fun switchVisibility() {
        val oldState = stateLiveData.value
        stateLiveData.value = oldState?.copy(
            counterIsVisible = !oldState.counterIsVisible
        )
    }

    fun setRandomColor() {
        val oldState = stateLiveData.value
        stateLiveData.value = oldState?.copy(
            counterTextColor = Color.rgb(
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256),
            )
        )
    }

    @Parcelize
    data class State(
        var counterValue: Int,
        var counterTextColor: Int,
        var counterIsVisible: Boolean,
    ) : Parcelable
}