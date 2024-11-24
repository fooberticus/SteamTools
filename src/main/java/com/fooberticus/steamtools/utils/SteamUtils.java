package com.fooberticus.steamtools.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SteamUtils {

    public static final long STEAM_64_BASE = 76561197960265728L;
    private static final Pattern STEAM_ID_32_PATTERN = Pattern.compile("\\[U:1:([0-9]+)]");
    private static final Pattern USER_NAME_PATTERN = Pattern.compile("^#[ ]+[0-9]+ \"(.+)\" +");

    private SteamUtils() {}

    public static Long getSteamID64FromSteamID32(final String steamID32) {
        Matcher m = STEAM_ID_32_PATTERN.matcher(steamID32);
        if (m.find()) {
            int steam32 = Integer.parseInt(m.group(1));
            return steam32 + STEAM_64_BASE;
        }
        return null;
    }

    public static String getSteamID32FromSteamID64(final Long steamID64) {
        long steam32 = steamID64 - STEAM_64_BASE;
        return "[U:1:" + steam32 + "]";
    }

    public static String getUserNameFromLine(final String line) {
        Matcher m = USER_NAME_PATTERN.matcher(line);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static Map<Long, String> getUserMapFromStatusText(final String statusText) {
        Map<Long, String> userMap = new HashMap<>();
        for (String line : statusText.split("\n")) {
            String name = getUserNameFromLine(line);
            Long id = getSteamID64FromSteamID32(line);
            if (name != null && id != null) {
                userMap.put(id, name);
            }
        }
        return userMap;
    }

    public static LocalDate getLocalDateFromTimestamp(final Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return Instant.ofEpochMilli( timestamp * 1000 )
                .atZone( ZoneId.systemDefault() )
                .toLocalDate();
    }

    public static boolean isBanReasonCheating(final String reason) {
        return StringUtils.containsAnyIgnoreCase(reason, "cheat", "bot", "hack", "lmaobox");
    }
}
