package com.mouldycheerio.discord.bot.commands;

import com.mouldycheerio.discord.bot.MyBot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class ResponseTimeCommand extends BotCommand {
    public ResponseTimeCommand() {
        setName("ping");
        setDescription(new CommandDescription("Ping", "Find out how fast the bots running", "ping"));
    }

    public void onCommand(MyBot orangepeel, IDiscordClient client, IMessage commandMessage, String[] args) {
        String c = orangepeel.getTexts().getString("ping");
        c = c.replace("[ping]", (commandMessage.getTimestamp().getNano() / 1000000)+ "ms");
        commandMessage.getChannel().sendMessage(c);

    }
}
