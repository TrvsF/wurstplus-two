package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class WurstplusTabUtil {

    public static String get_player_name(final NetworkPlayerInfo info) {
        final String name = (info.getDisplayName() != null) ? info.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(info.getPlayerTeam(),
                info.getGameProfile().getName());
        if (WurstplusFriendUtil.isFriend(name)) {
            return section_sign() + "6" + name;
        }
        if (WurstplusEnemyUtil.isEnemy(name)) {
            return section_sign() + "c" + name;
        }
        return name;
    }

    public static String section_sign() {
        return "\u00A7";
    }

}
