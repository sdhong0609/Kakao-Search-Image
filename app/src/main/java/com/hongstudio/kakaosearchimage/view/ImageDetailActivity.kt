package com.hongstudio.kakaosearchimage.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import coil.load
import com.hongstudio.kakaosearchimage.R
import com.hongstudio.kakaosearchimage.base.BaseActivity
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.databinding.ActivityImageDetailBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ImageDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityImageDetailBinding
    private var documentEntity = DocumentEntity()

    companion object {
        private const val IMAGE_DETAIL_EXTRA = "ImageDetailExtra"
        private const val BUNDLE_IS_FAVORITE_KEY = "isFavorite"

        fun newIntent(context: Context, documentEntity: DocumentEntity): Intent {
            return Intent(context, ImageDetailActivity::class.java).putExtra(IMAGE_DETAIL_EXTRA, documentEntity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding)

        documentEntity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(IMAGE_DETAIL_EXTRA, DocumentEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(IMAGE_DETAIL_EXTRA)
        } ?: DocumentEntity()

        savedInstanceState?.getBoolean(BUNDLE_IS_FAVORITE_KEY)?.let {
            documentEntity = documentEntity.copy(isFavorite = it)
        }

        setUpView()
    }

    private fun setUpView() {

        binding.imageViewDetail.load(documentEntity.imageUrl) {
            error(android.R.drawable.ic_delete)
        }

        setImageViewFavorite(documentEntity.isFavorite)

        binding.textViewDetailSiteName.text =
            getString(R.string.activity_image_detail_sitename, documentEntity.displaySitename)
        binding.textViewDocUrl.text = getString(R.string.activity_image_detail_link, documentEntity.docUrl)

        val localDate =
            Instant.parse(documentEntity.datetimeString).toLocalDateTime(TimeZone.currentSystemDefault()).date
        binding.textViewDateTime.text = getString(
            R.string.activity_image_detail_date,
            localDate.year,
            localDate.monthNumber,
            localDate.dayOfMonth
        )

        binding.imageViewFavorite.setOnClickListener {
            onClickFavorite()
        }
    }

    private fun onClickFavorite() {
        uiScope.launch {
            val dao = FavoriteDatabase.getDatabase(this@ImageDetailActivity).documentDao()
            documentEntity = documentEntity.copy(isFavorite = !documentEntity.isFavorite)
            if (documentEntity.isFavorite) {
                dao.insert(documentEntity)
            } else {
                dao.delete(documentEntity)
            }

            setImageViewFavorite(documentEntity.isFavorite)
        }
    }

    private fun setImageViewFavorite(isFavorite: Boolean) {
        val starDrawable = if (isFavorite) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }
        binding.imageViewFavorite.load(starDrawable)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUNDLE_IS_FAVORITE_KEY, documentEntity.isFavorite)
    }
}
