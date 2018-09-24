package com.mouldycheerio.discord.bot;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public class PeelingUtils {
    public static String mentionToId(String mention, IGuild server) {
        String id = "";
        for (char c : mention.toCharArray()) {
            if (Character.isDigit(c)) {
                id = id + c;
            }
        }
        if (id.length() <= 4) {

            for (IUser u : server.getUsers()) {
                if (u.getDiscriminator().equals(id) && u.getName().equals((mention.split("#")[0]))) {
                    id = u.getStringID();
                    break;
                }
            }
        }
        return id;
    }

    public static IUser mentionToUser(String mention, IGuild server) {
        String id = "";
        for (char c : mention.toCharArray()) {
            if (Character.isDigit(c)) {
                id = id + c;
            }
        }
        if (id.length() <= 4) {

            for (IUser u : server.getUsers()) {
                if (u.getDiscriminator().equals(id) && u.getName().equals((mention.split("#")[0]))) {
                    id = u.getStringID();
                    break;
                }
            }
        }
        for (IUser iUser : server.getUsers()) {
            if (iUser.getStringID().equals(id)) {
                return iUser;
            }
        }
        return null;
    }

    public static IRole roleMentionToId(String mention, IGuild server) {
        String id = "";
        for (char c : mention.toCharArray()) {
            if (Character.isDigit(c)) {
                id = id + c;
            }
        }

        for (IRole u : server.getRoles()) {
            if (u.getStringID().equals(id)) {
                id = u.getStringID();
                return u;
            }
        }
        return null;
    }
    public static IChannel channelMentionToId(String mention, IGuild server) {
        String id = "";
        for (char c : mention.toCharArray()) {
            if (Character.isDigit(c)) {
                id = id + c;
            }
        }

        for (IChannel u : server.getChannels()) {
            if (u.getStringID().equals(id)) {
                id = u.getStringID();
                return u;
            }
        }
        return null;
    }
}
