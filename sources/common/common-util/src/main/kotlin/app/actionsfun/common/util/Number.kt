package app.actionsfun.common.util

fun Float.format(digits: Int) = "%.${digits}f".format(this)