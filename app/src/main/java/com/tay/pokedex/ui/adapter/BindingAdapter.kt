package com.tay.pokedex.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import java.util.*

@BindingAdapter("imageUrl", "cardView")
fun loadImage(view: ImageView, url: String, cardView: CardView) {
    GlideApp.with(view.context)
        .load(url)
        .listener(
            GlidePalette.with(url)
                .use(BitmapPalette.Profile.MUTED_LIGHT)
                .intoCallBack { palette ->
                    val rgb = palette?.dominantSwatch?.rgb
                    rgb?.let { cardView.setCardBackgroundColor(it) }
                }.crossfade(true)
        ).into(view)
}


@BindingAdapter("capitalizeText")
fun capitalizeText(view: TextView, text: String) {
    view.text = text.replaceFirstChar { it.uppercase() }
}