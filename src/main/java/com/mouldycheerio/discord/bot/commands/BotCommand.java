package com.mouldycheerio.discord.bot.commands;

public abstract class BotCommand implements Command {

    private String name;
    private CommandDescription description;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public CommandDescription getDescription() {
        return description;
    }
    public void setDescription(CommandDescription description) {
        this.description = description;
    }
}
