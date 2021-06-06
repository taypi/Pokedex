package com.tay.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.tay.pokedex.R
import com.tay.pokedex.databinding.ActivityMainBinding
import com.tay.pokedex.ui.adapter.PagingAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val pokedexAdapter = PagingAdapter()
        binding.rvMain.let {
            it.adapter = pokedexAdapter
            it.layoutManager = GridLayoutManager(this, 2)
        }

        fetchPokemons(adapter = pokedexAdapter)
    }

    private fun fetchPokemons(adapter: PagingAdapter) {
        lifecycleScope.launch {
            viewModel.fetchPokemons().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}