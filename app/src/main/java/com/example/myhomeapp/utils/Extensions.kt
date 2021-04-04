package com.example.myhomeapp.utils

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

/** Получение null если строка пуста */
fun String.nullIfBlank(): String? = if (isBlank()) null else this

/** Получение null если коллекция пуста */
fun <T> Collection<T>.nullIfEmpty(): Collection<T>? = if (isEmpty()) null else this

/** Получение строковой даты в формате ISO  */
fun TemporalAccessor.toIsoDateString() = toFormat("yyyy-MM-dd")

/** Получение форматированной строки по шаблону из класса даты и/или времени */
fun TemporalAccessor.toFormat(fmt: String): String {
    val formatter = DateTimeFormatter.ofPattern(fmt, Locale.getDefault())
    return formatter.format(this)
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)
    return fallback
}