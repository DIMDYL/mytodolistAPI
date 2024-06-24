package org.example.api.content.common;

public class RedisMsg {

    public static final String[] IDENTIFYING_PREFIX = {
            "",
            "REGISTER_USER:",
            "EDIT_USER_INFO:",
            "RESTORE_USER_PASSWORD:"
    };
    public static final Long[] IDENTIFYING_TTL = {0L,60L, 60L, 60L};
}
