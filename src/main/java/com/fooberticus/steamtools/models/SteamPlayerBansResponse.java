package com.fooberticus.steamtools.models;

import lombok.Data;

import java.util.List;

@Data
public class SteamPlayerBansResponse {
    List<SteamPlayerBan> players;
}
