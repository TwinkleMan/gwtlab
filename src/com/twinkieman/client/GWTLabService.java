package com.twinkieman.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("twinkiemanService")
public interface GWTLabService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * get list of all users, registered in app
     * @return list of users
     */
    List<String> getUserList();

    /**
     * get all database entries (pairs of user-game rows)
     * @return all database entries
     */
    Map<String, List<GameLibraryUnit>> getAllEntries();

    /**
     * get specified user library
     * @param username user's login
     * @return list of entered user's games
     */
    List<GameLibraryUnit> getUserLibrary(String username);

    /**
     * add new game and its completion status to current user's library
     * @param entry game and completion status
     */
    void addEntry(GameLibraryUnit entry, String username);

    /**
     * Utility/Convenience class.
     * Use GWTLabService.App.getInstance() to access static instance of twinkiemanServiceAsync
     */
    public static class App {
        private static GWTLabServiceAsync ourInstance = GWT.create(GWTLabService.class);

        public static synchronized GWTLabServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
