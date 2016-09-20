/**
 * 
 */

package com.perceivedev.vhelp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class vHelp extends JavaPlugin {

    private Logger logger;

    private List<HelpSection> pages = new ArrayList<HelpSection>();

    // Config strings
    private String usageHelp;
    private String usageVHelp;

    @Override
    public void onEnable() {

	logger = getLogger();

	load();

	logger.info(versionText() + " enabled");

    }

    private void load() {

	if (!getDataFolder().exists()) {
	    getDataFolder().mkdirs();
	}

	saveFile("config.yml");

	YamlConfiguration config = getConfig("config.yml");
	ConfigurationSection pagesSection = config.getConfigurationSection("pages");

	for (String key : pagesSection.getKeys(false)) {
	    HelpSection section = new HelpSection(pagesSection.getConfigurationSection(key));
	    pages.add(section);
	}

	usageHelp = config.getString("usage.help");
	usageVHelp = config.getString("usage.vhelp");

    }

    private void saveFile(String path) {
	File file = getFile(path);
	if (!file.exists()) {
	    saveResource(path, false);
	}
    }

    private File getFile(String path) {
	return new File(getDataFolder(), path.replace('/', File.separatorChar));
    }

    private YamlConfiguration getConfig(String path) {
	return YamlConfiguration.loadConfiguration(getFile(path));
    }

    @Override
    public void onDisable() {

	logger.info(versionText() + " disabled");

    }

    public String versionText() {
	return getName() + " v" + getDescription().getVersion();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

	label = command.getLabel().toLowerCase();

	if (label.equals("help")) {
	    if (!handleHelp(sender, args)) {
		msg(sender, usageHelp);
	    }
	} else if (label.equals("vhelp")) {
	    if (!handleVHelp(sender, args)) {
		msg(sender, usageVHelp);
	    }
	}

	return true;

    }

    private boolean handleHelp(CommandSender sender, String[] args) {

	Object[] valid = pages.stream().filter(s -> sender.hasPermission(s.getPermission())).toArray();

	if (valid.length < 1) {
	    return false;
	}

	int page = 0;

	if (args.length >= 1) {
	    try {
		page = Integer.valueOf(args[0]);
	    } catch (NumberFormatException e) {
		// Ignore
	    }
	}

	((HelpSection) valid[valid.length - 1]).display(sender, page);

	return true;

    }

    private boolean handleVHelp(CommandSender sender, String[] args) {

	if (args.length < 1) {
	    msg(sender, "&7This server is running &b" + versionText());
	} else if (args[0].equalsIgnoreCase("reload")) {
	    reloadConfig();
	    load();
	    msg(sender, "&7The config has been reloaded");
	} else {
	    return false;
	}
	return true;

    }

    private void msg(CommandSender sender, String message) {
	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
