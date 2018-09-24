package com.mouldycheerio.discord.bot.commands;

public abstract class AdminCommand extends BotCommand {
    private String noPermText = "You can't do that! *(yet)*";

    public String getNoPermText() {
        return noPermText;
    }

    public void setNoPermText(String noPermText) {
        this.noPermText = noPermText;
    }

}
