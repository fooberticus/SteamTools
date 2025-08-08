package com.fooberticus.steamtools.models.rentamedic;

import lombok.Data;

@Data
public class RentAMedicCommunityBan {
    private String source;
    private String username;
    private String description;
    private String timestamp;
    private boolean active;
}
