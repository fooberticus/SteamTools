package com.fooberticus.steamtools.models.rentamedic;

import lombok.Data;

import java.util.List;

@Data
public class RentAMedicResponse {
    private List<RentAMedicResult> results;
}
