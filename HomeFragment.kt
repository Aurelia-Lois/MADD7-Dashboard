package com.example.madd7_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipAll = view.findViewById<TextView>(R.id.chip_home_all)
        val chipWriting = view.findViewById<TextView>(R.id.chip_home_writing)
        val chipDrawing = view.findViewById<TextView>(R.id.chip_home_drawing)
        val promptTitle = view.findViewById<TextView>(R.id.tv_daily_prompt_title)
        val promptMeta = view.findViewById<TextView>(R.id.tv_daily_prompt_meta)
        val recentCard = view.findViewById<View>(R.id.card_recent_work)

        fun selectChip(selected: TextView, category: String) {
            listOf(chipAll, chipWriting, chipDrawing).forEach { chip ->
                val isSel = chip == selected
                chip.isSelected = isSel
                chip.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (isSel) R.color.white else R.color.ink_secondary
                    )
                )
            }
            when (category) {
                "Drawing" -> {
                    promptTitle.text = "Sketch the shadow cast by morning light on your desk"
                    promptMeta.text = "Beginner  ·  Drawing"
                    recentCard.visibility = View.GONE
                }
                else -> {
                    promptTitle.text = getString(R.string.daily_prompt_headline)
                    promptMeta.text = getString(R.string.daily_prompt_meta)
                    recentCard.visibility = View.VISIBLE
                }
            }
        }

        selectChip(chipAll, "All")
        chipAll.setOnClickListener { selectChip(chipAll, "All") }
        chipWriting.setOnClickListener { selectChip(chipWriting, "Writing") }
        chipDrawing.setOnClickListener { selectChip(chipDrawing, "Drawing") }

        view.findViewById<View>(R.id.btn_notifications).setOnClickListener {
            Toast.makeText(requireContext(), "7-day streak active! Daily Prompt #015 is ready.", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.btn_home_start_prompt).setOnClickListener {
            openStudioModal("DAILY PROMPT / 015", promptTitle.text.toString())
        }

        view.findViewById<View>(R.id.card_quick_write).setOnClickListener {
            openStudioModal("FREE WRITE", "Let the words out — no timer, no rules.")
        }

        view.findViewById<View>(R.id.card_quick_draw).setOnClickListener {
            openStudioModal("SKETCHBOOK SPACE", "Follow your instinct with a quick study.")
        }

        view.findViewById<View>(R.id.tv_home_view_all).setOnClickListener {
            (activity as? MainActivity)?.switchTab(2)
        }
    }

    private fun openStudioModal(eyebrow: String, promptText: String) {
        val dialog = BottomSheetDialog(requireContext())
        val modalView = layoutInflater.inflate(R.layout.dialog_compose_work, null)
        modalView.findViewById<TextView>(R.id.tv_modal_eyebrow).text = eyebrow
        modalView.findViewById<TextView>(R.id.tv_modal_prompt).text = promptText

        val etTitle = modalView.findViewById<EditText>(R.id.et_work_title)
        modalView.findViewById<Button>(R.id.btn_publish_work).setOnClickListener {
            val title = etTitle.text.toString().ifBlank { "Untitled Reflection" }
            Toast.makeText(requireContext(), "Saved \"$title\" to your journal!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.setContentView(modalView)
        dialog.show()
    }
}