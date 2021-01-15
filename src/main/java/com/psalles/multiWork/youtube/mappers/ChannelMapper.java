package com.psalles.multiWork.youtube.mappers;

import com.psalles.multiWork.youtube.models.dtos.ChannelDto;
import com.psalles.multiWork.youtube.models.youtubeApiModels.Channel;
import com.psalles.multiWork.youtube.models.youtubeApiModels.ChannelResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ChannelMapper {
    public static ChannelDto toDto(Channel apiResponse) {

        return ChannelDto.builder()
                .title(apiResponse.getSnippet().getTitle())
                .description(apiResponse.getSnippet().getDescription())
                .customUrl(apiResponse.getSnippet().getCustomUrl())
                .publishedAt(apiResponse.getSnippet().getPublishedAt())
                .thumbnails(apiResponse.getSnippet().getThumbnails())
                .defaultLanguage(apiResponse.getSnippet().getDefaultLanguage())
                .country(apiResponse.getSnippet().getCountry())

                .uploads(apiResponse.getContentDetails().getRelatedPlaylists().getUploads())

                .viewCount(apiResponse.getStatistics().getViewCount())
                .subscriberCount(apiResponse.getStatistics().getSubscriberCount())
                .videoCount(apiResponse.getStatistics().getVideoCount())

                .hints(apiResponse.getBrandingSettings().getHints())
                .image(apiResponse.getBrandingSettings().getImage())

                .defaultTab(apiResponse.getBrandingSettings().getChannel().getDefaultTab())
                .featuredChannelsTitle(apiResponse.getBrandingSettings().getChannel().getFeaturedChannelsTitle())
                .featuredChannelsUrls(apiResponse.getBrandingSettings().getChannel().getFeaturedChannelsUrls())
                .keywords(apiResponse.getBrandingSettings().getChannel().getKeywords())
                .profileColor(apiResponse.getBrandingSettings().getChannel().getProfileColor())
                .trackingAnalyticsAccountId(apiResponse.getBrandingSettings().getChannel().getTrackingAnalyticsAccountId())
                .unsubscribedTrailer(apiResponse.getBrandingSettings().getChannel().getUnsubscribedTrailer())

                .build();
    }

    public static List<ChannelDto> toDtos(ChannelResponse response) {
        return response.getItems().stream().map(ChannelMapper::toDto).collect(Collectors.toList());
    }
}
