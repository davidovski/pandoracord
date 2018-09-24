package com.mouldycheerio.discord.bot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;

public class MyBot {
    private EventListener eventListener;
    private IDiscordClient client;
    public EventDispatcher dispatcher;

    private long uptime = 0;
    private long creation;

    private Random random;

    private long owner;
    private String playingText = "a game";
    private long playingtextindex = 0;
    private BotStatus status = BotStatus.INACTIVE;

    private JSONArray chatchannels;

    private String prefix;
    private JSONObject config;
    private JSONObject texts;

    public MyBot(JSONObject config) throws Exception {
        loadTexts();
        this.config = config;
        prefix = config.getString("prefix");
        owner = config.getLong("owner_id");
        // ChatterBotFactory chatterBotFactory = new ChatterBotFactory();
        // chatterBot = chatterBotFactory.create(ChatterBotType.CLEVERBOT);
        // chatsession = chatterBot.createSession(Locale.ENGLISH);

        random = new Random();

        chatchannels = new JSONArray();
        status = BotStatus.ACTIVE;

        eventListener = new EventListener(prefix, this);

        creation = System.currentTimeMillis();
        client = ClientFactory.createClient(config.getString("token"), true);
        dispatcher = client.getDispatcher();
        dispatcher.registerListener(eventListener);

        loadAll();

    }

    public boolean isChatChannel(IChannel c) {
        for (int i = 0; i < chatchannels.length(); i++) {
            if (chatchannels.getString(i).equals(c.getStringID())) {
                return true;
            }
        }
        return false;
    }

    public void loadAll() {
        try {

            JSONTokener parser = new JSONTokener(new FileReader("data.json"));

            JSONObject obj = (JSONObject) parser.nextValue();

            if (obj.has("channels")) {
                chatchannels = obj.getJSONArray("channels");
            }
        } catch (FileNotFoundException e) {
            saveAll();
        }catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void saveAll() {
        JSONObject obj = new JSONObject();
        obj.put("channels", chatchannels);
        try {
            FileWriter file = new FileWriter("data.json");
            file.write(obj.toString(1));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loop(long alpha) throws InterruptedException {
        uptime = alpha;
        updatePlaying(alpha);

    }

    private void updatePlaying(long alpha) {
        if (alpha % 400 == 0 && client.isReady()) {
            if (config.has("playing_text")) {
                client.changePlayingText(config.getString("playing_text"));
            }
            client.changePlayingText(prefix + "help");
        }
    }

    public IDiscordClient getClient() {
        return client;
    }

    public void setClient(IDiscordClient client) {
        this.client = client;
    }

    public long getUptime() {
        return System.currentTimeMillis() - creation;
    }

    public BotStatus getStatus() {
        return status;
    }

    public void setStatus(BotStatus status) {
        this.status = status;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public JSONArray getChatchannels() {
        return chatchannels;
    }

    public void setChatchannels(JSONArray chatchannels) {
        this.chatchannels = chatchannels;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        this.config = config;
    }

    public void loadTexts() {

        try {
            JSONTokener parser = new JSONTokener(new FileReader("texts.json"));

            JSONObject obj = (JSONObject) parser.nextValue();
            texts = new JSONObject();
            texts = obj;

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getTexts() {
        return texts;
    }

    public void setTexts(JSONObject texts) {
        this.texts = texts;
    }
}
