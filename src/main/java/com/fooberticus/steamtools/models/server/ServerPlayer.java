package com.fooberticus.steamtools.models.server;

import lombok.Data;

@Data
public class ServerPlayer {
    Long steam64Id;
    String timeOnServer;

    public String getTimeOnServer() {
        if (timeOnServer != null) {
            if (timeOnServer.length() == 5) {
                timeOnServer = "00:" + timeOnServer;
            } else if (timeOnServer.length() == 7) {
                timeOnServer = "0" + timeOnServer;
            }
        }
        return timeOnServer;
    }
}
