package com.azureproject.SharedEnum;

public enum EnumActions {
    LOGIN_ATTEMPT, GET_MAIN_SCREEN_DATA, NEW_USER, LOG_OUT, NEW_MESSAGE_PEER, GET_CHAT_DATA, NO_VALUE;

    public static EnumActions toAction(EnumActions action) {
        try {
            return valueOf(EnumActions.class, action.toString());
        } catch (Exception e) {
            return NO_VALUE;
        }
    }
}
