package com.hongstudio.image_detail

import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.hongstudio.image_detail.databinding.ActivityImageDetailBinding
import com.hongstudio.ui.base.BaseActivity
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

            binding.imageViewDetail.load(item?.imageUrl) {
                error(android.R.drawable.ic_delete)
            }

            binding.textViewDetailSiteName.text =
                getString(R.string.activity_image_detail_sitename, item?.displaySitename)
            binding.textViewDocUrl.text = getString(R.string.activity_image_detail_link, item?.docUrl)

            val localDate = item?.datetimeString?.let { Instant.parse(it).toLocalDateTime(TimeZone.currentSystemDefault()).date }
            binding.textViewDateTime.text = getString(
                R.string.activity_image_detail_date,
                localDate?.year,
                localDate?.monthNumber,
                localDate?.dayOfMonth
            )
        }

        viewModel.isFavorite.observe {
            binding.imageViewFavorite.isSelected = it
        }

        binding.imageViewFavorite.setOnClickListener {
            viewModel.onClickFavorite()
        }
    }
}
