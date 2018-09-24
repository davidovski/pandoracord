package com.mouldycheerio.discord.bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

public class PandoraRun {
    private static JSONObject config;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            config = new JSONObject();
            load();

            MyBot orangepeel = null;
            try {
                orangepeel = new MyBot(config);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.exit(1);
            }

            long a = 0;
            while (true) {

                try {
                    orangepeel.loop(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                a++;
                // Logger.raw(a + "");
                Thread.sleep(50);
                if (orangepeel.getStatus() == BotStatus.SHUTTINGDOWN) {
                    System.exit(0);
                }

                if (orangepeel.getStatus() == BotStatus.REBOOTING) {
                    System.out.println("RESTARTING!!");
                    break;
                }
            }
            orangepeel = null;

        }


        //
    }

    public static void load() {
        File tfile = new File("texts.json");
        if (!tfile.exists()) {
            JSONObject t = new JSONObject();
            t.put("help_message",
                    "Hello, I'm [name], a pandoracord chatbot. To set me up to talk in a channel use the following commands:\n\n"
                            + "`[prefix]addChannel #channel` allows me to reply to all messages in that channel\n"
                            + "`[prefix]removeChannel #channel` will prevent me from responding to all messages in that channel\n\n"
                            + "If you wish, you can also `[prefix]ping` me and see my `[prefix]uptime`.\n\n" + "Add me to your server: [invite]\n"
                            + "If you have any issues, please contact **[owner]**");
            t.put("ping", "pong! [ping]");
            t.put("uptime", "I have been online for [time]");
            t.put("channel_add", "I will respond to every message sent in [channel]");
            t.put("channel_remove", "I will no longer respond to every message sent in [channel]");
            try {
                FileWriter fileWriter = new FileWriter(tfile);
                fileWriter.write(t.toString(4));
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        try {
            JSONTokener parser = new JSONTokener(new FileReader("config.json"));

            JSONObject obj = (JSONObject) parser.nextValue();

            config = obj;
            System.out.println(config);

        } catch (FileNotFoundException e) {
            System.out.println("no config found...");
            System.out.println("");
            System.out.println("======================================");
            System.out.println("=           INITIAL  SETUP           =");
            System.out.println("======================================");
            System.out.println("");
            System.out.println("Please setup the bot using the newly created file config.json");

            JSONObject j = new JSONObject();
            j.put("token", "<enter your bot's token here>");
            j.put("prefix", "!");
            j.put("owner_id", "000000000000000000");
            j.put("pandorabot_id", "    ");
            j.put("reply_to_mentions", true);

            try {
                FileWriter file = new FileWriter("config.json");
                file.write(j.toString(4));
                file.flush();
                file.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.exit(1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
