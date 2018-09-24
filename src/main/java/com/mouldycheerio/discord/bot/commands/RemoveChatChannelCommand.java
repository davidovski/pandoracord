package com.mouldycheerio.discord.bot.commands;

import com.mouldycheerio.discord.bot.MyBot;
import com.mouldycheerio.discord.bot.PeelingUtils;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class RemoveChatChannelCommand extends BotCommand {
    public RemoveChatChannelCommand() {
        setName("removeChannel");
        setDescription(new CommandDescription("chatBot", "remove the chatting channel.", "chatBot #channel"));
    }

    public void onCommand(MyBot bot, IDiscordClient client, IMessage commandMessage, String[] args) {
        if (commandMessage.getAuthor().getStringID().equals(commandMessage.getGuild().getOwner().getStringID())) {
            IChannel ch = commandMessage.getChannel();
            if (args.length > 1) {
                ch = PeelingUtils.channelMentionToId(args[1], commandMessage.getGuild());
            }

            for (int i = bot.getChatchannels().length() -1; i > -1; i--) {
                if (bot.getChatchannels().getString(i).equals(ch.getStringID())) {
                    bot.getChatchannels().remove(i);
                    System.out.println("removed");
                }
            }
            String c = bot.getTexts().getString("channel_remove");
            c = c.replace("[channel]", ch.mention());
            commandMessage.reply(c);
            bot.saveAll();
        }
        commandMessage.getChannel().setTypingStatus(false);
    }
}
