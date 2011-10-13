package org.getchunky.chunkyciv.locale;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.config.Configuration;
import org.getchunky.chunkyciv.ChunkyCiv;
import org.getchunky.chunkyciv.config.Config;
import org.getchunky.chunkyciv.util.Logging;
import org.getchunky.chunkyciv.util.PluginTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dumptruckman, SwearWord
 */
public enum Language {
    ERROR("generic.error"),
    SUCCESS("generic.success"),
    INFO("generic.info"),
    
    CMD_TOWN_HELP("command.town.help"),
    CMD_TOWN_STATUS("command.town.status"),

    CMD_CIV_HELP("command.civ.help"),
    CMD_CIV_STATUS("command.civ.status"),
    CMD_CIV_NEW_HELP("command.civ.new.help"),
    CMD_CIV_NEW_DESC("command.civ.new.description"),


    NO_TOWN("town.no_town"),

    
    NO_CIV("civ.no_civ"),
    HAS_CIV("civ.has_civ"),
    CIV_EXISTS("civ.already_exists"),
    NO_CREATE_ON_CIV("civ.cannot_create_on_existing"),
    ;

    private String path;
    private static Configuration language;

    Language(String path) {
        this.path = path;
    }

    /**
     * Retrieves the path for a config option
     *
     * @return The path for a config option
     */
    public String getPath() {
        return path;
    }

    /**
     * Loads the language data into memory and sets defaults
     *
     * @throws java.io.IOException
     */
    public static void load() throws IOException {
        // Make the data folders
        ChunkyCiv.getInstance().getDataFolder().mkdirs();

        // Extract the default language file
        PluginTools.extractFromJar("english.yml");

        // Check if the language file exists.  If not, create it.
        File languageFile = new File(ChunkyCiv.getInstance().getDataFolder(), Config.LANGUAGE_FILE_NAME.getString());
        if (!languageFile.exists()) {
            languageFile.createNewFile();
        }

        // Load the language file into memory
        language = new Configuration(languageFile);
        language.load();

        // Saves the configuration from memory to file
        language.save();
    }

    public static String formatString(String string, Object... args) {
        // Replaces & with the Section character
        string = string.replaceAll("&", Character.toString((char) 167));
        // If there are arguments, %n notations in the message will be
        // replaced
        if (args != null) {
            for (int j = 0; j < args.length; j++) {
                string = string.replace("%" + (j + 1), args[j].toString());
            }
        }
        return string;
    }

    /**
     * Gets a list of the messages for a given path.  Color codes will be
     * converted and any lines too long will be split into an extra element in
     * the list.  %n notated variables n the message will be replaced with the
     * optional arguments passed in.
     *
     * @param path Path of the message in the language yaml file.
     * @param args Optional arguments to replace %n variable notations
     * @return A List of formatted Strings
     */
    public static List<String> getStrings(Language path, Object... args) {
        // Gets the messages for the path submitted
        List<Object> list = language.getList(path.getPath());

        List<String> message = new ArrayList<String>();
        if (list == null) {
            Logging.warning("Missing language for: " + path.getPath());
            return message;
        }
        // Parse each item in list
        for (int i = 0; i < list.size(); i++) {
            String temp = formatString(list.get(i).toString(), args);

            // Pass the line into the line breaker
            List<String> lines = Font.splitString(temp);
            // Add the broken up lines into the final message list to return
            for (int j = 0; j < lines.size(); j++) {
                message.add(lines.get(j));
            }
        }
        return message;
    }

    public static String getString(Language language, Object... args) {
        List<Object> list = Language.language.getList(language.getPath());
        if (list == null) {
            Logging.warning("Missing language for: " + language.getPath());
            return "";
        }
        if (list.isEmpty()) return "";
        return (formatString(list.get(0).toString(), args));
    }

    public String getString(Object... args) {
        return getString(this, args);
    }

    public List<String> getStrings(Object... args) {
        return getStrings(this, args);
    }

    public void bad(CommandSender sender, Object... args) {
        send(ChatColor.RED.toString() + Language.ERROR.getString(), sender, args);
    }

    public void normal(CommandSender sender, Object... args) {
        send("", sender, args);
    }

    private void send(String prefix, CommandSender sender, Object... args) {
        List<String> messages = getStrings(this, args);
        for (int i = 0; i < messages.size(); i++) {
            if (i == 0) {
                sender.sendMessage(prefix + " " + messages.get(i));
            } else {
                sender.sendMessage(messages.get(i));
            }
        }
    }

    public void good(CommandSender sender, Object... args) {
        send(ChatColor.GREEN.toString() + Language.SUCCESS.getString(), sender, args);
    }

    public void info(CommandSender sender, Object... args) {
        send(ChatColor.YELLOW.toString() + Language.INFO.getString(), sender, args);
    }

    /**
     * Sends a custom string to a player.
     *
     * @param player
     * @param message
     * @param args
     */
    public static void sendMessage(CommandSender player, String message, Object... args) {
        List<String> messages = Font.splitString(formatString(message, args));
        for (String s : messages) {
            player.sendMessage(s);
        }
    }
}