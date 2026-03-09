package ph.edu.usc24104013.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)

fun Int.toStepLabel(): String =
    if (this >= 1000) "${this / 1000}k steps" else "$this steps"