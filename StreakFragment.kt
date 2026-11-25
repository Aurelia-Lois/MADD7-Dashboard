package com.example.madd7_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class StreakFragment : Fragment() {

    private enum class DayState { MISSED, COMPLETED, STREAK, TODAY }

    private data class StreakDay(val label: String, val state: DayState)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_streak, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btn_streak_back).setOnClickListener {
            (activity as? MainActivity)?.switchTab(0)
        }

        val grid = view.findViewById<GridLayout>(R.id.grid_streak_days)
        val days = listOf(
            StreakDay("16", DayState.COMPLETED), StreakDay("17", DayState.COMPLETED), StreakDay("18", DayState.MISSED),
            StreakDay("19", DayState.COMPLETED), StreakDay("20", DayState.MISSED),    StreakDay("21", DayState.COMPLETED),
            StreakDay("22", DayState.COMPLETED), StreakDay("23", DayState.MISSED),    StreakDay("24", DayState.MISSED),
            StreakDay("25", DayState.COMPLETED), StreakDay("26", DayState.COMPLETED), StreakDay("27", DayState.MISSED),
            StreakDay("28", DayState.COMPLETED), StreakDay("29", DayState.MISSED),    StreakDay("30", DayState.COMPLETED),
            StreakDay("01", DayState.COMPLETED), StreakDay("02", DayState.MISSED),    StreakDay("03", DayState.MISSED),
            StreakDay("04", DayState.COMPLETED), StreakDay("05", DayState.MISSED),    StreakDay("06", DayState.COMPLETED),
            StreakDay("07", DayState.MISSED),    StreakDay("08", DayState.MISSED),    StreakDay("09", DayState.STREAK),
            StreakDay("10", DayState.STREAK),    StreakDay("11", DayState.STREAK),    StreakDay("12", DayState.STREAK),
            StreakDay("13", DayState.STREAK),    StreakDay("14", DayState.STREAK),    StreakDay("15", DayState.TODAY)
        )

        days.forEach { day ->
            val cell = layoutInflater.inflate(R.layout.item_streak_day, grid, false)
            val circle = cell.findViewById<FrameLayout>(R.id.day_circle)
            val check = cell.findViewById<ImageView>(R.id.day_check)
            val label = cell.findViewById<TextView>(R.id.day_label)

            label.text = day.label
            when (day.state) {
                DayState.MISSED -> {
                    circle.setBackgroundResource(R.drawable.bg_streak_circle_missed)
                    check.visibility = View.GONE
                }
                DayState.COMPLETED -> {
                    circle.setBackgroundResource(R.drawable.bg_streak_circle_completed)
                    check.visibility = View.VISIBLE
                }
                DayState.STREAK -> {
                    circle.setBackgroundResource(R.drawable.bg_streak_circle_streak)
                    check.visibility = View.VISIBLE
                }
                DayState.TODAY -> {
                    circle.setBackgroundResource(R.drawable.bg_streak_circle_today)
                    check.visibility = View.VISIBLE
                }
            }

            cell.setOnClickListener {
                val status = if (day.state == DayState.MISSED) "Rest day" else "Creative practice completed"
                Toast.makeText(requireContext(), "Day ${day.label}: $status", Toast.LENGTH_SHORT).show()
            }
            grid.addView(cell)
        }
    }
}