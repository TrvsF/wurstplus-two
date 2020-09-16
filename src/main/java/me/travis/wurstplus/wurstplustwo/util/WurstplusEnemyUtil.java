package me.travis.wurstplus.wurstplustwo.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class WurstplusEnemyUtil {

    public static ArrayList<Enemy> enemies = new ArrayList<>();

    public static boolean isEnemy(String name) {
        return enemies.stream().anyMatch(enemy -> enemy.username.equalsIgnoreCase(name));
    }

    public static class Enemy {
        String username;
        UUID uuid;

        public Enemy(String username, UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }
    }

    public static Enemy get_enemy_object(String name) {
        ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        NetworkPlayerInfo profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if (profile == null) {
            String s = request_ids("[\"" + name + "\"]");
            if (s == null || s.isEmpty()) {
                // cant find
            } else {
                JsonElement element = new JsonParser().parse(s);
                if (element.getAsJsonArray().size() != 0) {
                    try {
                        String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                        String username = element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                        return new Enemy(username, UUIDTypeAdapter.fromString(id));
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
        return new Enemy(profile.getGameProfile().getName(), profile.getGameProfile().getId());
    }

    private static String request_ids(String data) {
        try{
            String query = "https://api.mojang.com/profiles/minecraft";

            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String res = convertStreamToString(in);
            in.close();
            conn.disconnect();

            return res;
        }catch (Exception e) {
            return null;
        }
    }

    private static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        String r = s.hasNext() ? s.next() : "/";
        return r;
    }

}