package com.fooberticus.steamtools.models.server;

import lombok.Data;

@Data
public class ServerPlayer {
    Long steam64Id;
    String timeOnServer;
}
