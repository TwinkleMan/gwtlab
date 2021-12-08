package com.twinkieman.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Map;

public interface GWTLabServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);

    /**
     * get list of all users, registered in app
     *
     * @return list of users
     */
    void getUserList(AsyncCallback<List<String>> callback);

    /**
     * get all database entries (pairs of user-game rows)
     *
     * @return all database entries
     */
    void getAllEntries(AsyncCallback<Map<String, List<GameLibraryUnit>>> callback);

    /**
     * get specified user library
     * @param username user's login
     * @return list of entered user's games
     */
    void getUserLibrary(String username, AsyncCallback<List<GameLibraryUnit>> callback);

    /**
     * add new game and its completion status to current user's library
     * @param entry game and completion status
     */
    void addEntry(GameLibraryUnit entry, String username, AsyncCallback<Void> async);

}
