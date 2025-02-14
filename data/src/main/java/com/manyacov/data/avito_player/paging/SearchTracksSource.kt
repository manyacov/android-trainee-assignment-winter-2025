package com.manyacov.data.avito_player.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException
import com.manyacov.common.Constants
import com.manyacov.common.Constants.PAGE_SIZE
import com.manyacov.data.avito_player.datasource.remote.api.PlaylistApi
import com.manyacov.data.avito_player.mapper.toPlaylistTrack
import com.manyacov.domain.avito_player.model.PlaylistTrack

class SearchTracksSource(
    private val text: String,
    private val playlistApi: PlaylistApi
) : PagingSource<Int, PlaylistTrack>() {

    override fun getRefreshKey(state: PagingState<Int, PlaylistTrack>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaylistTrack> {
        val page = params.key ?: Constants.INITIAL_INDEX
        return try {
            val response = playlistApi.fetchSearchedList("track:${text}", page * PAGE_SIZE, params.loadSize)

            if (!response.isSuccessful) return LoadResult.Error(Exception(response.message()))

            val participants = response.body() ?: return LoadResult.Error(Exception("No data"))
            val nextKey = if (participants.data.isEmpty()) null else page + 1

            LoadResult.Page(
                data = participants.data.map { it.toPlaylistTrack() },
                prevKey = if (page == Constants.INITIAL_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}