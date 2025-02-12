package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.mapper.toUiIssuesType
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import com.manyacov.domain.avito_player.utils.CustomResult
import com.manyacov.domain.avito_player.utils.UiIssues
import com.manyacov.domain.avito_player.utils.UiResult
import javax.inject.Inject

class SearchApiTracksUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : BaseUseCase<UiResult<List<PlaylistTrack>>, SearchApiTracksUseCase.Params>() {

    override suspend fun execute(params: Params): UiResult<List<PlaylistTrack>> {
        var result: UiResult<List<PlaylistTrack>> = UiResult.Empty()

        playlistRepository.searchTracks(params.searchString).collect {
            result = when (it) {
                is CustomResult.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        UiResult.Error(UiIssues.EMPTY_RESULT)
                    } else {
                        UiResult.Success(it.data)
                    }
                }

                is CustomResult.Error -> {
                    UiResult.Error(it.issueType.toUiIssuesType())
                }
            }
        }
        return result
    }

    data class Params(val searchString: String)
}