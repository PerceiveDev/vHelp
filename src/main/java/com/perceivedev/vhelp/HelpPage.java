/**
 * 
 */
package com.perceivedev.vhelp;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Rayzr
 *
 */
public class HelpPage {

    private List<String> lines;

    public HelpPage(List<String> lines) {
	this.lines = lines;
    }

    /**
     * @return the lines
     */
    public List<String> getLines() {
	return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(List<String> lines) {
	this.lines = lines;
    }

    /**
     * @param sender
     */
    public void display(CommandSender sender) {
	lines.stream().forEach(line -> msg(sender, line));
    }

    private void msg(CommandSender sender, String msg) {
	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

}
