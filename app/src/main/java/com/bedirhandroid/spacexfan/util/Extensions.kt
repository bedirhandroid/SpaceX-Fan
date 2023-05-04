package com.bedirhandroid.spacexfan.util

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bedirhandroid.spacexfan.R
import com.bumptech.glide.Glide


//infix funcs for views visibility
infix fun View.visibleIf(bool: Boolean) =
    if (bool) visible() else gone()

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

infix fun ImageView.isFavorite(bool: Boolean) {
    if (bool) this.backgroundTintList = (ContextCompat.getColorStateList(this.context,R.color.teal_700))
    else this.backgroundTintList = (ContextCompat.getColorStateList(this.context,R.color.black))
}

fun ImageView.fav() {
    this.backgroundTintList = (ContextCompat.getColorStateList(this.context,R.color.teal_700))
}
//ImageView ext for load image
fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_no_photo)
        .into(this)
}

fun EditText.isNotEmpty(): Boolean {
    return (this.text.isNotEmpty())
}