/**
 * 
 */

package com.perceivedev.vhelp;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class vHelp extends JavaPlugin {

	private Logger logger;

	@Override
	public void onEnable() {

		logger = getLogger();

		logger.info(versionText() + " enabled");

	}

	@Override
	public void onDisable() {

		logger.info(versionText() + " disabled");

	}

	public String versionText() {
		return getName() + " v" + getDescription().getVersion();
	}

}
