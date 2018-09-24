package com.mouldycheerio.discord.bot.commands;

import com.mouldycheerio.discord.bot.MyBot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public interface Command {
    public String getName();
    public CommandDescription getDescription();
    public void onCommand(MyBot orangepeel, IDiscordClient client, IMessage commandMessage, String[] args);
}
