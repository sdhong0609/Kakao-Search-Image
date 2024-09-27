package com.hongstudio.image_detail

import android.os.Bundle
import android.view.View
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

        viewModel.uiState.observe {
            when (it) {
                is ImageDetailUiState.Loading -> {
                    setVisibility(isLoading = true)
                }

                is ImageDetailUiState.Found -> {
                    val item = it.item
                    setVisibility(isLoading = false)

                    binding.run {
                        imageViewDetail.load(item.imageUrl) {
                            error(android.R.drawable.ic_delete)
                        }
                        textViewDetailSiteName.text =
                            getString(R.string.activity_image_detail_sitename, item.displaySitename)
                        textViewDocUrl.text = getString(R.string.activity_image_detail_link, item.docUrl)

                        val localDate = item.datetimeString.let { dateTimeString ->
                            Instant.parse(dateTimeString).toLocalDateTime(TimeZone.currentSystemDefault()).date
                        }
                        textViewDateTime.text = getString(
                            R.string.activity_image_detail_date,
                            localDate.year,
                            localDate.monthNumber,
                            localDate.dayOfMonth
                        )

                        imageViewFavorite.isSelected = item.isFavorite
                    }
                }

                is ImageDetailUiState.NotFound -> {
                    finish()
                }
            }
        }

        binding.imageViewFavorite.setOnClickListener {
            viewModel.onClickFavorite()
        }
    }

    private fun setVisibility(isLoading: Boolean) {
        binding.run {
            progressBarImageDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
            imageViewDetail.visibility = if (isLoading) View.GONE else View.VISIBLE
            textViewDetailSiteName.visibility = if (isLoading) View.GONE else View.VISIBLE
            textViewDocUrl.visibility = if (isLoading) View.GONE else View.VISIBLE
            textViewDateTime.visibility = if (isLoading) View.GONE else View.VISIBLE
            imageViewFavorite.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }
}
