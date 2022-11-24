package com.example.savedinstancestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.savedinstancestate.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private val viewModel by viewModels<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnIncrement.setOnClickListener { viewModel.increment() }
            btnSetRandomColor.setOnClickListener { viewModel.setRandomColor() }
            btnSetVisible.setOnClickListener { viewModel.switchVisible() }
        }

        viewModel.initState(
            savedInstanceState?.getParcelable(KEY_VM) ?: ActivityViewModel.State(
                counterValue = 0,
                textColor = ContextCompat.getColor(this, R.color.teal_200),
                textVisible = true
            )
        )

        viewModel.state.observe(this, Observer {
            renderState(it)
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_VM, viewModel.state.value)
    }

    private fun renderState(state: ActivityViewModel.State) = with(binding.tvText) {
        setText(state.counterValue.toString())
        setTextColor(state.textColor)
        visibility =
            if (state.textVisible) android.view.View.VISIBLE else android.view.View.INVISIBLE
    }

    companion object {
        @JvmStatic
        val KEY_VM = "KEY_VM"
    }


}