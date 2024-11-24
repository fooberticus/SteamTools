package com.fooberticus.steamtools.utils;

import com.fooberticus.steamtools.models.SourceBan;
import com.fooberticus.steamtools.models.SourceBanResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CustomRestClientIntegrationTest {
    private CustomRestClient client = new CustomRestClient();

    static {
        if (GuiUtil.getSavedSteamHistoryApiKey() == null || GuiUtil.getSavedSteamHistoryApiKey().isEmpty()) {
            System.out.println("YOU NEED TO CONFIGURE A STEAM HISTORY KEY");
            System.out.println("RUN THE APPLICATION TO CONFIGURE THIS VALUE");
        }
    }

    @Test
    public void testMakingRequestsToSteamHistory() throws Exception {
        Map<Long, String> userMap = SteamUtils.getUserMapFromStatusText( SteamUtilsTest.STATUS_CHONK );

        assertEquals(20, userMap.size());

        Set<Long> ids = userMap.keySet();

        SourceBanResponse response = client.getSourceBans(ids);

        assertNotNull(response);

        List<SourceBan> sourceBans = response.getResponse();

        assertNotNull(sourceBans);
        assertFalse(sourceBans.isEmpty());

        sourceBans.forEach(System.out::println);
    }
}
