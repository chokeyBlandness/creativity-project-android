package usst.dysrc.kaoyan.utils

import android.widget.ImageView
import android.widget.TextView
import java.util.*

interface SingleCalendarTransaction{

    fun manipulateTextView(date: Date, textView: TextView)

    fun manipulateIconView(date: Date,iconView: ImageView)
}
