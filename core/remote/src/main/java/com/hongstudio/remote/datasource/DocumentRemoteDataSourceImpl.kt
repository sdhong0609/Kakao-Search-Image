package com.hongstudio.remote.datasource

import com.hongstudio.data.datasource.remote.DocumentRemoteDataSource
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.remote.BuildConfig
import com.hongstudio.remote.api.SearchImageApi
import com.hongstudio.remote.model.toDto
import javax.inject.Inject

class DocumentRemoteDataSourceImpl @Inject constructor(
    private val searchImageApi: SearchImageApi
) : DocumentRemoteDataSource {

    override suspend fun getSearchedImages(
        query: String,
        page: Int
    ): List<DocumentDto> {
        return searchImageApi.getSearchedImages(
            authorization = BuildConfig.REST_API_KEY,
            query = query,
            page = page,
            size = SIZE_PER_PAGE
        ).let {
            it.documents.map { documentRemote ->
                documentRemote.toDto()
            }
        }
    }

    companion object {
        private const val SIZE_PER_PAGE = 10
    }
}
