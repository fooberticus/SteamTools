package com.fooberticus.steamtools.models.steam;

import lombok.Data;

import java.util.List;

@Data
public class SteamPlayerSummaryResponse {
    public record InnerResponse(List<SteamPlayerSummary> players) {}

    InnerResponse response;
}
