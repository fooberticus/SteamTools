package com.fooberticus.steamtools.models;

import lombok.Data;

@Data
public class SourceBan {
    private String SteamID;
    private String Name;
    private String CurrentState;
    private String BanReason;
    private String UnbanReason;
    private Long BanTimestamp;
    private Long UnbanTimestamp;
    private String Server;
}
