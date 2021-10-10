package com.psalles.multiWork.youtube.mappers;

import com.psalles.multiWork.youtube.models.dtos.PlaylistDto;
import com.psalles.multiWork.youtube.models.dtos.PlaylistItemDto;
import com.psalles.multiWork.youtube.models.youtubeApiModels.Playlist;
import com.psalles.multiWork.youtube.models.youtubeApiModels.PlaylistItem;
import com.psalles.multiWork.youtube.models.youtubeApiModels.PlaylistItemResponse;
import com.psalles.multiWork.youtube.models.youtubeApiModels.PlaylistResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistMapper {

    public static PlaylistDto toDto(Playlist apiResponse) {
        return PlaylistDto.builder()
                .id(apiResponse.getId())
                .thumbnails(apiResponse.getSnippet().getThumbnails())
                .title(apiResponse.getSnippet().getTitle())
                .status(apiResponse.getStatus().getPrivacyStatus())
                .itemCount(apiResponse.getContentDetails().getItemCount())
                .build();
    }

    public static List<PlaylistDto> toDtos(List<PlaylistResponse> responses) {
        List<Playlist> items = responses.stream().map(PlaylistResponse::getItems).flatMap(Collection::stream).collect(Collectors.toList());
        return items.stream().map(PlaylistMapper::toDto).collect(Collectors.toList());
    }


}
