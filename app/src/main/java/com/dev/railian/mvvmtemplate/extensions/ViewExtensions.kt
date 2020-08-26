package com.dev.railian.mvvmtemplate.extensions

import android.graphics.Bitmap
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.visibleIf(predicate: Boolean) {
    if (predicate) show() else hide()
}

fun View.invisibleIf(predicate: Boolean) {
    if (predicate) invisible() else show()
}

fun EditText.addMask(maskString: String): FormatWatcher {
    val slots = UnderscoreDigitSlotsParser().parseSlots(maskString)
    val mask = MaskImpl.createTerminated(slots)
    val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
    formatWatcher.installOn(this)
    return formatWatcher
}

fun ImageView.loadImageFromUrl(
    url: String?,
    @DrawableRes onError: Int //TODO add your resource like default value
) {
    Glide.with(context)
        .load(url)
        .error(onError)
        .into(this)
}

fun ImageView.loadCircleImageFromUrl(
    url: String?,
    @DrawableRes onError: Int //TODO add your resource like default value
) {
    Glide.with(context)
        .load(url)
        .transform(CircleCrop())
        .error(onError)
        .into(this)
}

fun ImageView.loadCorneredImageResources(
    @DrawableRes image: Int,
    cornerRadius: Int,
    @DrawableRes onError: Int //TODO add your resource like default value
) {
    Glide.with(context)
        .load(image)
        .transform(CenterCrop(), RoundedCorners(cornerRadius.toPx()))
        .error(onError)
        .into(this)
}

fun ImageView.loadCircleImageFromBitmap(
    bitmap: Bitmap?,
    @DrawableRes onError: Int //TODO add your resource like default value
) {
    Glide.with(context)
        .load(bitmap)
        .transform(CircleCrop())
        .error(onError)
        .into(this)
}