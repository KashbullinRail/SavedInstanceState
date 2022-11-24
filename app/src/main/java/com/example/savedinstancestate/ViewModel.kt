package com.example.savedinstancestate

import android.graphics.Color
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

class ActivityViewModel : ViewModel() {

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

   fun setRandomColor() {
        val oldState = stateLiveData.value
        stateLiveData.value = oldState?.copy(
            textColor = Color.rgb(
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256),
            )
        )
    }

   fun switchVisible() {
        val oldState = stateLiveData.value
        stateLiveData.value = oldState?.copy(
        textVisible = !oldState.textVisible
        )
    }

    @Parcelize
    data class State(
        var counterValue: Int,
        var textColor: Int,
        var textVisible: Boolean
    ):Parcelable

}
