package me.travis.wurstplus.wurstplustwo.util;

import java.util.ArrayList;
import java.util.List;

public class WurstplusDrawnUtil {
    
    public static List<String> hidden_tags = new ArrayList<>();

    public static void add_remove_item(String s) {
        s = s.toLowerCase();
        if (hidden_tags.contains(s)) {
            WurstplusMessageUtil.send_client_message("Added " + s);
            hidden_tags.remove(s);
        } else {
            WurstplusMessageUtil.send_client_message("Removed " + s);
            hidden_tags.add(s);
        }

    }

}