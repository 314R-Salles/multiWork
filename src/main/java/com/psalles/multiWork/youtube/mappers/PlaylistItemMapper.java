package com.psalles.multiWork.youtube.mappers;

import com.psalles.multiWork.youtube.models.dtos.PlaylistItemDto;
import com.psalles.multiWork.youtube.models.youtubeApiModels.PlaylistItem;
import com.psalles.multiWork.youtube.models.youtubeApiModels.PlaylistItemResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistItemMapper {

    public static PlaylistItemDto toDto(PlaylistItem apiResponse) {
        return PlaylistItemDto.builder()
                .description(apiResponse.getSnippet().getDescription())
                .position(apiResponse.getSnippet().getPosition())
                .publishedAt(apiResponse.getSnippet().getPublishedAt())
                .thumbnails(apiResponse.getSnippet().getThumbnails())
                .title(apiResponse.getSnippet().getTitle())
                .videoId(apiResponse.getSnippet().getResourceId().getVideoId())
                .build();
    }

    public static List<PlaylistItemDto> toDtos(List<PlaylistItemResponse> responses) {
        List<PlaylistItem> items = responses.stream().map(PlaylistItemResponse::getItems).flatMap(Collection::stream).collect(Collectors.toList());
        return items.stream().map(PlaylistItemMapper::toDto).collect(Collectors.toList());
    }


}
