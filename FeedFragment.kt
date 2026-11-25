package com.example.madd7_ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_feed, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipAll = view.findViewById<TextView>(R.id.chip_feed_all)
        val chipWriting = view.findViewById<TextView>(R.id.chip_feed_writing)
        val chipDrawing = view.findViewById<TextView>(R.id.chip_feed_drawing)
        val cardWriting = view.findViewById<View>(R.id.card_feed_writing)
        val cardDrawing = view.findViewById<View>(R.id.card_feed_drawing)

        fun applyFilter(selected: TextView, type: String) {
            listOf(chipAll, chipWriting, chipDrawing).forEach { chip ->
                val active = chip == selected
                chip.isSelected = active
                chip.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (active) R.color.white else R.color.ink_secondary
                    )
                )
            }
            cardWriting.visibility = if (type == "Drawing") View.GONE else View.VISIBLE
            cardDrawing.visibility = if (type == "Writing") View.GONE else View.VISIBLE
        }

        applyFilter(chipAll, "All")
        chipAll.setOnClickListener { applyFilter(chipAll, "All") }
        chipWriting.setOnClickListener { applyFilter(chipWriting, "Writing") }
        chipDrawing.setOnClickListener { applyFilter(chipDrawing, "Drawing") }

        val tvRatingMaya = view.findViewById<TextView>(R.id.tv_rating_maya)
        val tvRatingAri = view.findViewById<TextView>(R.id.tv_rating_ari)

        view.findViewById<View>(R.id.btn_rate_maya).setOnClickListener {
            showRatingDialog("Things I Never Said", "@maya", tvRatingMaya, "4.4 ★")
        }

        view.findViewById<View>(R.id.btn_rate_ari).setOnClickListener {
            showRatingDialog("A quiet kind of morning", "@ari", tvRatingAri, "4.8 ★")
        }
    }

    private fun showRatingDialog(title: String, author: String, badge: TextView, updatedScore: String) {
        val options = arrayOf("5.0 ★ — Deeply moving", "4.5 ★ — Thoughtful craft", "4.0 ★ — Warm & honest")
        AlertDialog.Builder(requireContext())
            .setTitle("Rate \"$title\" by $author")
            .setItems(options) { _, _ ->
                badge.text = updatedScore
                Toast.makeText(requireContext(), "Thank you! Rating recorded.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Close", null)
            .show()
    }
}