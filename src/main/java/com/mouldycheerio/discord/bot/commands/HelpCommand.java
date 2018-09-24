package com.mouldycheerio.discord.bot.commands;

import java.util.List;

import com.mouldycheerio.discord.bot.MyBot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;

public class HelpCommand extends BotCommand {
    private List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
        setName("help");
        setDescription(new CommandDescription("Help", "Display this message", "help"));
    }

    public void onCommand(MyBot orangepeel, IDiscordClient client, IMessage commandMessage, String[] args) {

        String stringID = commandMessage.getAuthor().getStringID();

        IPrivateChannel pm = client.getOrCreatePMChannel(commandMessage.getAuthor());
        String message = orangepeel.getTexts().getString("help_message");


        message = message.replace("[name]", client.getOurUser().getName());
        message = message.replace("[prefix]", orangepeel.getConfig().getString("prefix"));
        IUser owner = client.getUserByID(orangepeel.getConfig().getLong("owner_id"));
        message = message.replace("[owner]", owner.getName() + "#" + owner.getDiscriminator());
        message = message.replace("[invite]", "<https://discordapp.com/oauth2/authorize?client_id=" + client.getOurUser().getLongID() + "&scope=bot>");

        commandMessage.getChannel().sendMessage(message);
        // https://discordapp.com/oauth2/authorize?client_id=306115875784622080&scope=bot
    }
}
