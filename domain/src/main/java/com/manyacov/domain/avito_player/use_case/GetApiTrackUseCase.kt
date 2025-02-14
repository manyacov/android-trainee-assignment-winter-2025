package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.mapper.toUiIssuesType
import com.manyacov.domain.avito_player.model.AudioDomain
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import com.manyacov.domain.avito_player.utils.CustomResult
import com.manyacov.domain.avito_player.utils.UiResult
import javax.inject.Inject

class GetApiTrackUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : BaseUseCase<UiResult<AudioDomain>, GetApiTrackUseCase.Params>() {

    override suspend fun execute(params: Params): UiResult<AudioDomain> {
        var result: UiResult<AudioDomain> = UiResult.Empty()

        playlistRepository.loadTrack(params.id).collect {
            result = when (it) {
                is CustomResult.Success -> {
                    UiResult.Success(it.data)
                }

                is CustomResult.Error -> {
                    UiResult.Error(it.issueType.toUiIssuesType())
                }
            }
        }
        return result
    }

    data class Params(val id: String)
}