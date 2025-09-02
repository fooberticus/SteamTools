package com.fooberticus.steamtools.models.steamhistory;

import lombok.Data;

import java.util.HashSet;

@Data
public class SourceBanResponse {
    private HashSet<SourceBan> response;
}
