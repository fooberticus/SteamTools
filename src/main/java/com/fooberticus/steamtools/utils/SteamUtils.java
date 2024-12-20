package com.fooberticus.steamtools.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SteamUtils {

    public static final long STEAM_64_BASE = 76561197960265728L;
    private static final Pattern STEAM_ID_32_PATTERN = Pattern.compile("\\[U:1:([0-9]+)]");

    private SteamUtils() {}

    public static String getSteamID32FromSteamID64(final Long steamID64) {
        long steam32 = steamID64 - STEAM_64_BASE;
        return "[U:1:" + steam32 + "]";
    }

    public static List<Long> getUserIdsFromText(final String text) {
        Set<Long> result = new HashSet<>();
        Matcher m = STEAM_ID_32_PATTERN.matcher(text);
        while ( m.find() ) {
            int steam32 = Integer.parseInt(m.group(1));
            result.add(steam32 + STEAM_64_BASE);
        }
        return result.stream().toList();
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
        return StringUtils.containsAnyIgnoreCase(reason, "cheat", "aim", "bot", "hack", "lmaobox", "smac", "silent");
    }
}
