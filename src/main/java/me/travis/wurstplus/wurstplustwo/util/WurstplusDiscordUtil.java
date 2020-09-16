//package me.travis.wurstplus.wurstplusmod.util;
//
//import net.minecraft.client.Minecraft;
//
//import net.minecraft.client.multiplayer.ServerData;
//
//import net.arikia.dev.drpc.DiscordEventHandlers;
//import net.arikia.dev.drpc.DiscordRPC;
//import net.arikia.dev.drpc.DiscordRichPresence;
//
//import me.travis.wurstplus.Wurstplus;
//
//public class WurstplusDiscordUtil {
//
//    public static Minecraft mc = Minecraft.getMinecraft();
//
//    public static String details;
//    public static String state;
//    public static int players;
//    public static int maxPlayers;
//    public static int players2;
//    public static int maxPlayers2;
//    public static ServerData svr;
//    public static String[] popInfo;
//
//    public static void start() {
//
//        String applicationId = "705741263470723093";
//        String steamId = "";
//
//        DiscordRichPresence presence = new DiscordRichPresence();
//        DiscordEventHandlers handlers = new DiscordEventHandlers();
//
//        DiscordRPC.discordInitialize(applicationId, handlers, true, steamId);
//        DiscordRPC.discordUpdatePresence(presence);
//
//        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
//        presence.details = "Vibin' RN. NR";
//        presence.state = "Wurstplus Two";
//        presence.largeImageKey = "large";
//
//        new Thread(() ->
//        {
//            while (!Thread.currentThread().isInterrupted()) {
//                try {
//                    details = "";
//                    state = "";
//                    players = 0;
//                    maxPlayers = 0;
//                    if (mc.isIntegratedServerRunning()) {
//                        details = "on his tod";
//                    }
//                    else if (mc.getCurrentServerData() != null) {
//                        svr = mc.getCurrentServerData();
//                        if (!svr.serverIP.equals("")) {
//                            details = "with the fellas";
//                            state = svr.serverIP;
//                            if (svr.populationInfo != null) {
//                                popInfo = svr.populationInfo.split("/");
//                                if (popInfo.length > 2) {
//                                    players2 = Integer.parseInt(popInfo[0]);
//                                    maxPlayers2 = Integer.parseInt(popInfo[1]);
//                                }
//                            }
//                        }
//                    }
//                    else {
//                        details = "Vibin' RN. NR";
//                        state = "Listening to mr. worldwide";
//                    }
//                    if (!details.equals(presence.details) || !state.equals(presence.state)) {
//                        presence.startTimestamp = System.currentTimeMillis() / 1000L;
//                    }
//                    presence.details = details;
//                    presence.state = state;
//                    DiscordRPC.discordUpdatePresence(presence);
//                }
//                catch (Exception e2) {
//                    e2.printStackTrace();
//                }
//                try {
//                    Thread.sleep(5000L);
//                }
//                catch (InterruptedException e3) {
//                    e3.printStackTrace();
//                }
//            }
//        }, "RPC-Callback-Handler").start();
//    }
//
//}