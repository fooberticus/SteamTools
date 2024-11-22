package com.fooberticus.steamtools.utils;

import com.fooberticus.steamtools.models.SourceBanResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.juneau.common.internal.StringUtils;
import org.apache.juneau.rest.client.RestClient;

import java.util.Set;

@Slf4j
public final class CustomRestClient {

    public static final String STEAM_HISTORY_ENDPOINT = "https://steamhistory.net/api/sourcebans";

    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public SourceBanResponse getSourceBans(Set<Long> steam64Ids) {
        String ids = StringUtils.join(steam64Ids, ",");
        String url = STEAM_HISTORY_ENDPOINT + "?key=" + GuiUtil.getSavedSteamHistoryKey() + "&steamids=" + ids;
        log.info("url: {}", url);
        return getRequest(url, SourceBanResponse.class);
    }

    /** Reusable method for making GET requests.
     *
     * @param url String url of the endpoint being called.
     * @param responseType Type representing the desired response type.
     * @return Response object of responseType returned from the rest call.
     */
    protected <T> T getRequest(String url, Class<T> responseType) {
        log.info("GET {}", url);
        T response = null;

        try {
            RestClient client = RestClient.create().build();
            String rawResponse = client
                    .get(url)
                    .run()
                    .assertStatus().asCode().is(200)
                    .getContent().asString();
            log.info("RAW RESPONSE: {}", rawResponse);
            response = gson.fromJson(rawResponse, responseType);
            client.close();
            //below was a bug fix (memory leak) for an earlier version
            //PropertyStoreBuilder is no longer accessible in new version
            //PropertyStoreBuilder.clearCache();
        } catch (Exception e) {
            log.error("#############################################");
            log.error("EXCEPTION CAUGHT: {}", e.getMessage(), e);
            log.error("#############################################");
        }

        return response;
    }
}
