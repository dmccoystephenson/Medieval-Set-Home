package dansplugins.sethomesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StorageManager {

    private boolean debug = false;

    private static StorageManager instance;

    private StorageManager() {

    }

    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    public void saveHomeRecords() {
        for (HomeRecord record : PersistentData.getInstance().getHomeRecords()) {
            record.save();
        }
    }

    public void loadHomeRecords() {
        try {
            if (debug) { System.out.println("Attempting to load home records..."); }
            File loadFile = new File("./plugins/Medieval-Set-Home/" + "home-record-filenames.txt");
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            while (loadReader.hasNextLine()) {
                String nextName = loadReader.nextLine();
                HomeRecord temp = new HomeRecord();
                temp.setPlayerName(nextName);
                temp.load(nextName); // provides owner field among other things

                // existence check
                boolean exists = false;
                for (int i = 0; i < PersistentData.getInstance().getHomeRecords().size(); i++) {
                    if (PersistentData.getInstance().getHomeRecords().get(i).getPlayerName().equalsIgnoreCase(temp.getPlayerName())) {
                        PersistentData.getInstance().getHomeRecords().remove(i);
                    }
                }

                PersistentData.getInstance().getHomeRecords().add(temp);

            }

            loadReader.close();
            if (debug) { System.out.println("Home records successfully loaded."); }
        } catch (FileNotFoundException e) {
            if (debug) { System.out.println("Error loading the factions!"); }
        }
    }
}
