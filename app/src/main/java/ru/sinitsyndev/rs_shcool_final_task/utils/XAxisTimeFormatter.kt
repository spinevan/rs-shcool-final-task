package ru.sinitsyndev.rs_shcool_final_task.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class XAxisTimeFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return getDateTime(value)
    }
    private fun getDateTime(s: Float): String {
        try {
            val sdf = SimpleDateFormat("dd/MMM", Locale.US)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = s.toLong()
            return sdf.format(calendar.time)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}
