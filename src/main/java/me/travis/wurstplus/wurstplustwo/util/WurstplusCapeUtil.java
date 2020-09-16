package me.travis.wurstplus.wurstplustwo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class WurstplusCapeUtil {

    final static ArrayList<String> final_uuid_list = get_uuids();

    public static ArrayList<String> get_uuids() {
        try {
            URL url = new URL("https://pastebin.com/raw/u7fPkfBD");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            final ArrayList<String> uuid_list = new ArrayList<>();

            String s;

            while ((s = reader.readLine()) != null) {
                uuid_list.add(s);
            }

            return uuid_list;
        } catch (Exception ignored){
            return null;
        }
    }

    public static boolean is_uuid_valid(UUID uuid) {
        for (String u : Objects.requireNonNull(final_uuid_list)) {
            if (u.equals(uuid.toString())) {
                return true;
            }
        }
        return false;
    }

}
