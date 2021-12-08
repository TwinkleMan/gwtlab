package com.twinkieman.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GameLibraryUnit implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private String developer;
    private String name;
    private boolean isCompleted;

    public GameLibraryUnit() {
    }

    public GameLibraryUnit(String developer, String name, boolean isCompleted) {
        this.developer = developer;
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public String getDeveloper() {
        return developer;
    }
    public String getName() {
        return name;
    }
    public boolean isCompleted() {
        return isCompleted;
    }

}
