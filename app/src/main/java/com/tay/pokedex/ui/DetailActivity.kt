package com.tay.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tay.pokedex.R
import com.tay.pokedex.model.Pokemon

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        (intent.extras?.get(Intent.EXTRA_USER) as Pokemon).let {
            val name = findViewById<TextView>(R.id.tv_pokemon)
            name.text = it.name
        }
    }
}