package com.psalles.multiWork.youtube.mappers;

import com.psalles.multiWork.youtube.models.dtos.VideoDto;
import com.psalles.multiWork.youtube.models.youtubeApiModels.Video;
import com.psalles.multiWork.youtube.models.youtubeApiModels.VideoResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VideoMapper {

    public static VideoDto toDto(Video apiResponse) {

        return VideoDto.builder()
                .id(apiResponse.getId())
                .caption(apiResponse.getContentDetails().getCaption())
                .definition(apiResponse.getContentDetails().getDefinition())
                .dimension(apiResponse.getContentDetails().getDimension())
                .duration(apiResponse.getContentDetails().getDuration())
                .hasCustomThumbnail(apiResponse.getContentDetails().getHasCustomThumbnail())
                .licensedContent(apiResponse.getContentDetails().getLicensedContent())
                .projection(apiResponse.getContentDetails().getProjection())

                .categoryId(apiResponse.getSnippet().getCategoryId())
                .channelId(apiResponse.getSnippet().getChannelId())
                .channelTitle(apiResponse.getSnippet().getChannelTitle())
                .defaultAudioLanguage(apiResponse.getSnippet().getDefaultAudioLanguage())
                .defaultLanguage(apiResponse.getSnippet().getDefaultLanguage())
                .description(apiResponse.getSnippet().getDescription())
                .liveBroadcastContent(apiResponse.getSnippet().getLiveBroadcastContent())
                .publishedAt(apiResponse.getSnippet().getPublishedAt())
                .tags(apiResponse.getSnippet().getTags())
                .title(apiResponse.getSnippet().getTitle())
                .thumbnails(apiResponse.getSnippet().getThumbnails())

                .commentCount(apiResponse.getStatistics().getCommentCount())
                .dislikeCount(apiResponse.getStatistics().getDislikeCount())
                .favoriteCount(apiResponse.getStatistics().getFavoriteCount())
                .likeCount(apiResponse.getStatistics().getLikeCount())
                .viewCount(apiResponse.getStatistics().getViewCount())

                .build();
    }

    public static List<VideoDto> toDtos(List<VideoResponse> responses) {
        List<Video> items = responses.stream().map(VideoResponse::getItems).flatMap(Collection::stream).collect(Collectors.toList());
        return items.stream().map(VideoMapper::toDto).collect(Collectors.toList());
    }

}
