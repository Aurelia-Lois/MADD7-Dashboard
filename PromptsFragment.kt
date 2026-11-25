package com.example.madd7_ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PromptsFragment : Fragment() {

    private var savedCount = 8
    private var bookmarked1 = true
    private var bookmarked2 = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_prompts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipAll = view.findViewById<TextView>(R.id.chip_prompts_all)
        val chipWriting = view.findViewById<TextView>(R.id.chip_prompts_writing)
        val chipDrawing = view.findViewById<TextView>(R.id.chip_prompts_drawing)
        val cardDrawing = view.findViewById<View>(R.id.card_inspiration_drawing)
        val cardWriting = view.findViewById<View>(R.id.card_inspiration_writing)
        val tvSaved = view.findViewById<TextView>(R.id.tv_saved_count)

        fun updateCategory(selected: TextView, filter: String) {
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
            cardDrawing.visibility = if (filter == "Writing") View.GONE else View.VISIBLE
            cardWriting.visibility = if (filter == "Drawing") View.GONE else View.VISIBLE
        }

        updateCategory(chipAll, "All")
        chipAll.setOnClickListener { updateCategory(chipAll, "All") }
        chipWriting.setOnClickListener { updateCategory(chipWriting, "Writing") }
        chipDrawing.setOnClickListener { updateCategory(chipDrawing, "Drawing") }

        val etSearch = view.findViewById<EditText>(R.id.et_prompt_search)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s?.toString()?.trim()?.lowercase().orEmpty()
                cardDrawing.visibility = if (q.isEmpty() || "draw quiet object".contains(q) || "drawing".contains(q)) View.VISIBLE else View.GONE
                cardWriting.visibility = if (q.isEmpty() || "letter future self".contains(q) || "writing".contains(q)) View.VISIBLE else View.GONE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val bm1 = view.findViewById<ImageButton>(R.id.btn_bookmark_1)
        val bm2 = view.findViewById<ImageButton>(R.id.btn_bookmark_2)

        bm1.setOnClickListener {
            bookmarked1 = !bookmarked1
            savedCount += if (bookmarked1) 1 else -1
            bm1.setBackgroundResource(if (bookmarked1) R.drawable.bg_pill_rating else R.drawable.bg_card_stone)
            tvSaved.text = "Saved · $savedCount"
        }

        bm2.setOnClickListener {
            bookmarked2 = !bookmarked2
            savedCount += if (bookmarked2) 1 else -1
            bm2.setBackgroundResource(if (bookmarked2) R.drawable.bg_pill_rating else R.drawable.bg_card_stone)
            tvSaved.text = "Saved · $savedCount"
        }

        view.findViewById<View>(R.id.btn_prompts_start).setOnClickListener {
            Toast.makeText(requireContext(), "Starting Daily Practice session...", Toast.LENGTH_SHORT).show()
        }
    }
}