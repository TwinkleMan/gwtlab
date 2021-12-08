package com.twinkieman.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.twinkieman.client.GWTLabService;
import com.twinkieman.client.GameLibraryUnit;

import java.util.*;

public class GWTLabServiceImpl extends RemoteServiceServlet implements GWTLabService {

    private static Map <String, List<GameLibraryUnit>> database = null;

    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    @Override
    public List<String> getUserList() {
        if (database == null) initDatabase();
        String[] temp = new String[database.keySet().size()];
        database.keySet().toArray(temp);

        return Arrays.asList(temp);
    }

    @Override
    public Map<String, List<GameLibraryUnit>> getAllEntries() {
        if (database == null) initDatabase();

        return database;
    }

    @Override
    public List<GameLibraryUnit> getUserLibrary(String username) {
        if (database == null) initDatabase();

        return database.get(username);
    }

    @Override
    public void addEntry(GameLibraryUnit entry, String username) {
        if (entry != null) {
            database.get(username).add(entry);
        }
    }

    private void initDatabase() {
        database = new HashMap<String, List<GameLibraryUnit>>();
        List<GameLibraryUnit> lib1 = new ArrayList<>();
        List<GameLibraryUnit> lib2 = new ArrayList<>();
        List<GameLibraryUnit> lib3 = new ArrayList<>();
        lib1.add(new GameLibraryUnit("CD PROJECT RED", "The Witcher 3", true));
        lib1.add(new GameLibraryUnit("Digital Sun", "Moonlighter", false));
        lib1.add(new GameLibraryUnit("Playground games", "Forza Horizon 5", false));
        lib1.add(new GameLibraryUnit("CD PROJECT RED", "Cyberpunk 2077", false));
        database.put("VanVan", lib1);

        lib2.add(new GameLibraryUnit("Valve", "Dota 2", true));
        lib2.add(new GameLibraryUnit("CD PROJECT RED", "Cyberpunk 2077", true));
        lib2.add(new GameLibraryUnit("HotFoodGames", "Drunken Samurai", false));
        lib2.add(new GameLibraryUnit("Larian Studios", "Divinity: Original Sin 2", false));
        database.put("SinsODR", lib2);

        lib3.add(new GameLibraryUnit("Valve", "Dota 2", false));
        lib3.add(new GameLibraryUnit("CD PROJECT RED", "Cyberpunk 2077", true));
        lib3.add(new GameLibraryUnit("Larian Studios", "Divinity: Original Sin 2", false));
        lib3.add(new GameLibraryUnit("Playground games", "Forza Horizon 5", false));
        lib3.add(new GameLibraryUnit("Digital Sun", "Moonlighter", true));
        lib3.add(new GameLibraryUnit("CD PROJECT RED", "The Witcher 3", true));
        database.put("GrigSig", lib3);
    }
}