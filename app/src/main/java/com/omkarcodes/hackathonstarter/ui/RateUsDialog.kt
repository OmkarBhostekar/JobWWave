package com.omkarcodes.hackathonstarter.ui


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.databinding.DialogRateUsBinding

class RateUsDialog : DialogFragment() {

    private var binding: DialogRateUsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRateUsBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dialog?.let {
            it.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.window!!.setBackgroundDrawable(ColorDrawable(0))
        }

        setupRatingsView()

    }

    private fun setupRatingsView() {
        var rating = 0f
        binding?.apply {
            setDefaultEmojis()
            poorEmoji.root.setOnClickListener {
                setDefaultEmojis()
                poorEmoji.apply {
                    context?.getColor(R.color.poorEmojiColor)
                        ?.let { it1 -> emojiText.setTextColor(it1) }
                    emojiImage.setImageResource(R.drawable.ic_poor_emoji_selected)
                }
                rating = 1f
            }
            badEmoji.root.setOnClickListener {
                setDefaultEmojis()
                badEmoji.apply {
                    context?.let { it1 -> emojiText.setTextColor(it1.getColor(R.color.badEmojiColor)) }
                    emojiImage.setImageResource(R.drawable.ic_bad_emoji_selected)
                }
                rating = 2f
            }
            normalEmoji.root.setOnClickListener {
                setDefaultEmojis()
                normalEmoji.apply {
                    context?.let { it1 -> emojiText.setTextColor(it1.getColor(R.color.normalEmojiColor)) }
                    emojiImage.setImageResource(R.drawable.ic_normal_emoji_selected)
                }
                rating = 3f
            }
            goodEmoji.root.setOnClickListener {
                setDefaultEmojis()
                goodEmoji.apply {
                    context?.let { it1 -> emojiText.setTextColor(it1.getColor(R.color.goodEmojiColor)) }
                    emojiImage.setImageResource(R.drawable.ic_good_emoji_selected)
                }
                rating = 4f
            }
            excellentEmoji.root.setOnClickListener {
                setDefaultEmojis()
                excellentEmoji.apply {
                    context?.let { it1 -> emojiText.setTextColor(it1.getColor(R.color.excellentEmojiColor)) }
                    emojiImage.setImageResource(R.drawable.ic_excellent_emoji_selected)
                }
                rating = 5f
            }
            ivClose.setOnClickListener {
                dismiss()
            }
            tvRateUs.setOnClickListener {
                Toast.makeText(context, "Thank you for rating us!!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    private fun setDefaultEmojis() {
        binding?.apply {
            poorEmoji.apply {
                emojiImage.setImageResource(R.drawable.ic_poor_emoji)
                emojiText.text = "Poor"
                emojiText.setTextColor(Color.GRAY)
            }
            badEmoji.apply {
                emojiImage.setImageResource(R.drawable.ic_bad_emoji)
                emojiText.text = "Bad"
                emojiText.setTextColor(Color.GRAY)
            }
            normalEmoji.apply {
                emojiImage.setImageResource(R.drawable.ic_normal_emoji)
                emojiText.text = "Normal"
                emojiText.setTextColor(Color.GRAY)
            }
            goodEmoji.apply {
                emojiImage.setImageResource(R.drawable.ic_good_emoji)
                emojiText.text = "Good"
                emojiText.setTextColor(Color.GRAY)
            }
            excellentEmoji.apply {
                emojiImage.setImageResource(R.drawable.ic_excellent_emoji)
                emojiText.text = "Great"
                emojiText.setTextColor(Color.GRAY)
            }
        }
    }

}