package com.example.savedinstancestate

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        state = if (savedInstanceState == null) {
            State(
                counterValue = 0,
                textColor = ContextCompat.getColor(this, R.color.teal_200),
                textVisible = true
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
    ) : Serializable

    companion object {
        @JvmStatic
        private val KEY_STATE = "State"
    }
}

