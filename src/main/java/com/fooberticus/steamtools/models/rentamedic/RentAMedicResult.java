package com.fooberticus.steamtools.models.rentamedic;

import lombok.Data;

import java.util.List;

@Data
public class RentAMedicResult {
    private String id;
    private boolean cheater;
    private String cheaterType;
    private String cheaterDate;
    private RentAMedicCheaterMeta cheaterMeta;
    private List<RentAMedicCommunityBan> communityBans;
}
