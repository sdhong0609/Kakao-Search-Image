package com.hongstudio.kakaosearchimage.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseActivity
import com.hongstudio.kakaosearchimage.databinding.ActivityImageDetailBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ImageDetailActivity : BaseActivity<ActivityImageDetailBinding>(
    inflater = ActivityImageDetailBinding::inflate
) {
    private val viewModel: ImageDetailViewModel by viewModels {
        ImageDetailViewModelFactory(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(IMAGE_DETAIL_EXTRA, DocumentEntity::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(IMAGE_DETAIL_EXTRA)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch {
            viewModel.detailItem.collectLatest { item ->
                if (item == null) return@collectLatest run { finish() }

                binding.imageViewDetail.load(item.imageUrl) {
                    error(android.R.drawable.ic_delete)
                }

                binding.textViewDetailSiteName.text = getString(R.string.activity_image_detail_sitename, item.displaySitename)
                binding.textViewDocUrl.text = getString(R.string.activity_image_detail_link, item.docUrl)

                val localDate = Instant.parse(item.datetimeString).toLocalDateTime(TimeZone.currentSystemDefault()).date
                binding.textViewDateTime.text = getString(
                    R.string.activity_image_detail_date,
                    localDate.year,
                    localDate.monthNumber,
                    localDate.dayOfMonth
                )

                val starDrawable = if (item.isFavorite) {
                    android.R.drawable.btn_star_big_on
                } else {
                    android.R.drawable.btn_star_big_off
                }
                binding.imageViewFavorite.load(starDrawable)
            }
        }

        binding.imageViewFavorite.setOnClickListener {
            viewModel.onClickFavorite()
        }
    }

    companion object {
        private const val IMAGE_DETAIL_EXTRA = "ImageDetailExtra"

        fun newIntent(context: Context, item: DocumentEntity): Intent {
            return Intent(context, ImageDetailActivity::class.java).putExtra(IMAGE_DETAIL_EXTRA, item)
        }
    }
}
