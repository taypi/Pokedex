package com.tay.pokedex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.tay.pokedex.R
import com.tay.pokedex.databinding.ActivityMainBinding
import com.tay.pokedex.ui.adapter.PagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val adapter = PagingAdapter {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(Intent.EXTRA_USER, it)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.rvMain.let {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(this, 2)
        }

        fetchPokemons()
    }

    private fun fetchPokemons() {
        lifecycleScope.launch {
            viewModel.fetchPokemons().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}