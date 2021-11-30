package ru.sinitsyndev.rs_shcool_final_task.utils

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class XAxisTimeFormatter: ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return getDateTime(value)
    }
    private fun getDateTime(s: Float): String {
        try {
            val sdf = SimpleDateFormat("dd/MMM", Locale.US)
            //val netDate = Date(s.toLong())
            val calendar = Calendar.getInstance()

            //Log.d(LOG_TAG,  calendar.timeInMillis.toString())

            calendar.timeInMillis = s.toLong()

            return sdf.format(calendar.time)

            //return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}