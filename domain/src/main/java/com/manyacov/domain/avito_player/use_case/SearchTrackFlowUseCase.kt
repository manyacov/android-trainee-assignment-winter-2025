package com.manyacov.domain.avito_player.use_case

import androidx.paging.PagingData
import com.manyacov.common.domain.BaseFlowUseCase
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import javax.inject.Inject

class SearchTrackFlowUseCase @Inject constructor(
    private val songsRepository: PlaylistRepository
) : BaseFlowUseCase<PagingData<PlaylistTrack>, SearchTrackFlowUseCase.Params>() {

    override fun execute(params: Params) =
        songsRepository.searchSongs(params.searchText)
            //.map { data -> data.map { songEntity -> songEntity.toSong() } }

    data class Params(val searchText: String)
}