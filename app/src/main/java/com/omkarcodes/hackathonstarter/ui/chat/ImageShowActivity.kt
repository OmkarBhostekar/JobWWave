package com.omkarcodes.hackathonstarter.ui.chat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.databinding.ActivityImageShowBinding

class ImageShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityImageShowBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_image_show)

        binding.imageLoadingTextView.text = getString(R.string.loading)
        binding.imageLoadingTextView.visibility = View.VISIBLE

        val imageUrl: String? = intent.getStringExtra("image")

//        if(imageUrl != null && imageUrl != "") {
//            imageUrl?.let { url ->
//                val imgUri = (BASE_URL + url).toUri().buildUpon().scheme("https").build()
//                Glide.with(this)
//                    .load(imgUri)
//                    .listener(object : RequestListener<Drawable> {
//                        override fun onLoadFailed(
//                            e: GlideException?,
//                            model: Any?,
//                            target: Target<Drawable>?,
//                            isFirstResource: Boolean,
//                        ): Boolean {
//                            binding.imageLoadingTextView.text =
//                                getString(R.string.glide_image_error)
//                            return false
//                        }
//
//                        override fun onResourceReady(
//                            resource: Drawable?,
//                            model: Any?,
//                            target: Target<Drawable>?,
//                            dataSource: DataSource?,
//                            isFirstResource: Boolean,
//                        ): Boolean {
//                            binding.imageLoadingTextView.visibility = View.GONE
//                            return false
//                        }
//
//                    })
//                    .into(binding.fullScreenImageView)
//            }
//        } else {
//            binding.imageLoadingTextView.text =
//                getString(R.string.glide_image_error)
//        }

        binding.goBackButton.setOnClickListener{
            finish()
        }
    }
}