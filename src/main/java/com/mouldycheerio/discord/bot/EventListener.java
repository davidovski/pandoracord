package com.mouldycheerio.discord.bot;

import java.util.HashMap;
import java.util.Locale;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class EventListener {
    private String prefix;
    private MyBot myBot;
    private CommandController commandController;
    private String ourmention;
    private String ourmention2;
    private ChatterBot bot1;
    private ChatterBotSession bot1session;

    private HashMap<String, ChatterBotSession> sessions;

    public EventListener(String prefix, MyBot myBot) {
        this.prefix = prefix;
        this.myBot = myBot;
        commandController = new CommandController(myBot);
        ChatterBotFactory factory = new ChatterBotFactory();
        sessions = new HashMap<String, ChatterBotSession>();
        try {
            bot1 = factory.create(ChatterBotType.PANDORABOTS, myBot.getConfig().get("pandorabot_id"));
        } catch (Exception e) {
            System.out.println("====== INVALID PANDORA BOT ID ======");
            System.out.println("Using default...");// f6d4afd83e34564d
            try {
                bot1 = factory.create(ChatterBotType.PANDORABOTS, "f6d4afd83e34564d");
            } catch (Exception e1) {
                System.out.println("====== Default ID failed ======");
                System.out.println("Please ensure that you can make a valid connection to https://pandorabots.com if not please contact your network administrator");

                e1.printStackTrace();
            }
        }

    }

    @EventSubscriber
    public void onUserJoinEvent(UserJoinEvent event) {
        // event.getGuild().getGeneralChannel().sendMessage("Hi, <@" + event.getUser().getStringID() + "> and welcome to " + event.getGuild().getName());
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {

        IUser user = event.getClient().getOurUser();
        Logger.raw("==========");
        Logger.raw(user.getName() + "#" + user.getDiscriminator());
        Logger.raw(user.getStringID());
        Logger.raw("==========");
        ourmention = "<@" + myBot.getClient().getOurUser().getStringID() + ">";
        ourmention2 = "<@!" + myBot.getClient().getOurUser().getStringID() + ">";

    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) throws Exception {
        String content = event.getMessage().getContent();

        if (myBot.isChatChannel(event.getChannel())) {
            event.getChannel().setTypingStatus(true);
            ai(event.getAuthor(), content, event.getChannel());
            event.getChannel().setTypingStatus(false);

        }

        if (event.getMessage().getMentions().contains(myBot.getClient().getOurUser())) {
            if (myBot.getConfig().has("reply_to_mentions") && myBot.getConfig().getBoolean("reply_to_mentions")) {
                event.getChannel().setTypingStatus(true);
                String c = content;
                for (IUser i : event.getMessage().getMentions()) {
                    if (!i.equals(myBot.getClient().getOurUser())) {
                        c = c.replace(i.mention(), i.getName());
                        c = c.replace(i.mention(false), i.getName());
                    } else {
                        c = c.replace(i.mention(), "");
                        c = c.replace(i.mention(false), "");

                    }
                }
                ai(event.getAuthor(), c, event.getChannel());
                event.getChannel().setTypingStatus(false);

            }
        }

        if (content.startsWith(prefix)) {
            event.getChannel().setTypingStatus(true);
            commandController.onMessageReceivedEvent(event, prefix);
            event.getChannel().setTypingStatus(false);

        }
    }

    public void ai(IUser user, String message, IChannel reply) {
        try {
            if (!sessions.containsKey(user.getStringID())) {
                sessions.put(user.getStringID(), bot1.createSession(Locale.UK));
            }
            ChatterBotSession session = sessions.get(user.getStringID());
            String think = session.think(message);
            reply.sendMessage(user.mention() + " " + think);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CommandController getCommandController() {
        return commandController;
    }

    public void setCommandController(CommandController commandController) {
        this.commandController = commandController;
    }
}
