/**
 * 
 */
package com.perceivedev.vhelp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Rayzr
 *
 */
public class HelpSection {

    private String permission = "";
    private List<HelpPage> pages = new ArrayList<HelpPage>();

    public HelpSection(ConfigurationSection section) {

	for (String key : section.getKeys(false)) {

	    if (key.equals("permission")) {

		permission = section.getString(key);
		continue;

	    }

	    pages.add(new HelpPage(section.getStringList(key)));

	}

    }

    /**
     * @return the permission
     */
    public String getPermission() {
	return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
	this.permission = permission;
    }

    /**
     * @return the pages
     */
    public List<HelpPage> getPages() {
	return pages;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(List<HelpPage> pages) {
	this.pages = pages;
    }

    /**
     * @param sender
     */
    public void display(CommandSender sender, int page) {

	page = page == 0 ? page : page - 1;
	if (page >= pages.size()) {
	    msg(sender, "&cPage " + (page + 1) + " does not exist");
	    return;
	}
	page = page < pages.size() ? page : 0;

	pages.get(page).display(sender);

    }

    private void msg(CommandSender sender, String msg) {
	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

}
