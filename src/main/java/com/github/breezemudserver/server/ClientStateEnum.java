package com.github.breezemudserver.server;

public enum ClientStateEnum {
    CLIENT_NO_STATE,
    CLIENT_INIT_CONNECTION,
    CLIENT_LOGIN,
    CLIENT_REGISTER,
    CLIENT_SELECT_CHARACTER,
    CLIENT_CREATE_CHARACTER,
    CLIENT_DELETE_CHARACTER,
    CLIENT_IN_GAME,
    CLIENT_DISCONNECT
}
