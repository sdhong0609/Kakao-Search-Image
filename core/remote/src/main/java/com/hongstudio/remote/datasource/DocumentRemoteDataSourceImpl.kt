package com.hongstudio.remote.datasource

import com.hongstudio.data.datasource.remote.DocumentRemoteDataSource
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.remote.api.SearchImageApi
import com.hongstudio.remote.model.toDto
import javax.inject.Inject

class DocumentRemoteDataSourceImpl @Inject constructor(
    private val searchImageApi: SearchImageApi
) : DocumentRemoteDataSource {

    override suspend fun getSearchedImages(
        authorization: String,
        query: String,
        page: Int,
        size: Int
    ): List<DocumentDto> {
        return searchImageApi.getSearchedImages(
            authorization = authorization,
            query = query,
            page = page,
            size = size
        ).let {
            it.documents.map { documentRemote ->
                documentRemote.toDto()
            }
        }
    }
}
