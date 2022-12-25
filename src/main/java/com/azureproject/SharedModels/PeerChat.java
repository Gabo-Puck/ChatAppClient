package com.azureproject.SharedModels;

import java.util.List;

import com.azureproject.SharedInterfaces.Chat;

public class PeerChat extends Chat {
    private static final long serialVersionUID = 8799656478674716638L;

    public PeerChat(String name, List<Message> messages, List<User> users) {
        super(name, messages, users);
    }

    public PeerChat() {
    }

}
