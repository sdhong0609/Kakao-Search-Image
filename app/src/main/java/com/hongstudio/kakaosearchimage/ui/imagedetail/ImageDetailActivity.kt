package com.hongstudio.kakaosearchimage.ui.imagedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseActivity
import com.hongstudio.kakaosearchimage.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.databinding.ActivityImageDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@AndroidEntryPoint
class ImageDetailActivity : BaseActivity<ActivityImageDetailBinding>(
    inflater = ActivityImageDetailBinding::inflate
) {
    private val viewModel: ImageDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.detailItemStream.observe { item ->
            if (item == null) return@observe run { finish() }

            binding.imageViewDetail.load(item.imageUrl) {
                error(android.R.drawable.ic_delete)
            }

            binding.textViewDetailSiteName.text =
                getString(R.string.activity_image_detail_sitename, item.displaySitename)
            binding.textViewDocUrl.text = getString(R.string.activity_image_detail_link, item.docUrl)

            val localDate = Instant.parse(item.datetimeString).toLocalDateTime(TimeZone.currentSystemDefault()).date
            binding.textViewDateTime.text = getString(
                R.string.activity_image_detail_date,
                localDate.year,
                localDate.monthNumber,
                localDate.dayOfMonth
            )
        }

        viewModel.isFavorite.observe {
            binding.imageViewFavorite.isSelected = it
        }

        binding.imageViewFavorite.setOnClickListener {
            viewModel.onClickFavorite()
        }
    }

    companion object {
        private const val IMAGE_DETAIL_EXTRA = "ImageDetailExtra"

        fun newIntent(context: Context, item: LocalDocument): Intent {
            return Intent(context, ImageDetailActivity::class.java).putExtra(IMAGE_DETAIL_EXTRA, item)
        }
    }
}
