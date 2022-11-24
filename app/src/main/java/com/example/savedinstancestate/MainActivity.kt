package com.example.savedinstancestate

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.savedinstancestate.databinding.ActivityMainBinding
import java.io.Serializable
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnIncrement.setOnClickListener { increment() }
            btnSetRandomColor.setOnClickListener { setRandomColor() }
            btnSetVisible.setOnClickListener { switchVisible() }
        }

        state = savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            counterValue = 0,
            textColor = ContextCompat.getColor(this, R.color.teal_200),
            textVisible = true
        )
        renderState()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
    }

    private fun increment() {
        state.counterValue++
        renderState()
    }

    private fun setRandomColor() {
        state.textColor = Color.rgb(
            nextInt(256),
            nextInt(256),
            nextInt(256),
        )
        renderState()
    }

    private fun switchVisible() {
        state.textVisible = !state.textVisible
        renderState()
    }

    private fun renderState() = with(binding.tvText) {
        setText(state.counterValue.toString())
        setTextColor(state.textColor)
        visibility = if (state.textVisible) View.VISIBLE else View.INVISIBLE
    }

    class State(
        var counterValue: Int,
        var textColor: Int,
        var textVisible: Boolean
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(counterValue)
            parcel.writeInt(textColor)
            parcel.writeByte(if (textVisible) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<State> {
            override fun createFromParcel(parcel: Parcel): State {
                return State(parcel)
            }

            override fun newArray(size: Int): Array<State?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {
        @JvmStatic
        private val KEY_STATE = "State"
    }
}

