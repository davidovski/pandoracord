package com.mouldycheerio.discord.bot.commands;

import com.mouldycheerio.discord.bot.MyBot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

public class PerServerCustomCmd extends SimpleCustomCmd {
    private IGuild server;

    public PerServerCustomCmd(String command, CommandDescription description, String text, IGuild server) {
        super(command, description, text);
        this.server = server;
    }

    @Override
    public void onCommand(MyBot orangepeel, IDiscordClient client, IMessage commandMessage, String[] args) {
        if (commandMessage.getGuild().getStringID().equals(server.getStringID())) {
            commandMessage.getChannel().sendMessage(getText());
        }
    }

    public IGuild getServer() {
        return server;
    }

    public void setServer(IGuild server) {
        this.server = server;
    }

    public boolean isOnServer(IGuild g) {
        return (g.getStringID().equals(server.getStringID()));
    }
}
