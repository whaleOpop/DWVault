package com.dw.core;

import com.dw.core.Hooks.*;
import com.dw.core.Tasks.TaskType;
import com.dw.core.DataModels.*;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import com.dw.core.Vaults.ModelVault;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * DWAPI core Bukkit Plugin implementation
 * 
 * @author whaleOpop, BlackWarlow
 *
 */
public final class DWVault extends JavaPlugin {
	/**
	 * Plugin logger.
	 */
	private Logger logger = getLogger();
	public HashMap<String, PluginHook> availableHooks = new HashMap<String, PluginHook>();

	/**
	 * Plugin startup method.
	 */
	@Override
	public void onEnable() {
		// Save default config.yml
		saveDefaultConfig();

		// Setting up available plugin hooks
		CoinMaterialHook cm = new CoinMaterialHook();
		availableHooks.put(cm.pluginName, cm);
		GuildedHook g = new GuildedHook();
		availableHooks.put(g.pluginName, g);

		// Register CS classes for serialization
		registerCSClasses();

		// Load plugin data to hooks
		loadData();

		// Launch tasks if needed
		launchTasks(TaskType.ON_ENABLE);
		logger.info("Startup tasks launched");
	}

	/**
	 * Registers all ConfigurationSerialization class hooks.
	 */
	private void registerCSClasses() {
		// Model hooks
		ConfigurationSerialization.registerClass(PlayerModel.class, "PlayerModel");
		ConfigurationSerialization.registerClass(GuildModel.class, "GuildModel");
		ConfigurationSerialization.registerClass(CoinModel.class, "CoinModel");

		// Vault hook
		ConfigurationSerialization.registerClass(ModelVault.class, "ModelVault");
		logger.info("CSClasses registered!");
	}

	/**
	 * Launches tasks of a single type in all hooks.
	 * 
	 * @param toLaunch TaskType to launch
	 */
	private void launchTasks(TaskType toLaunch) {
		for (Entry<String, PluginHook> s : availableHooks.entrySet()) {
			s.getValue().launchTasks(toLaunch);
		}
	}

	/**
	 * Cancels all tasks.
	 */
	private void cancelAllTasks() {
		for (Entry<String, PluginHook> s : availableHooks.entrySet()) {
			s.getValue().stopAllTasks();
		}
		logger.info("All tasks stopped!");
	}

	/**
	 * Saves all plugins' data.
	 */
	private void saveData() {
		for (Entry<String, PluginHook> s : availableHooks.entrySet()) {
			s.getValue().saveData();
		}
		logger.info("All vault data saved!");
	}

	/**
	 * Loads all plugins' data.
	 */
	private void loadData() {
		for (Entry<String, PluginHook> s : availableHooks.entrySet()) {
			s.getValue().loadData();
		}
		logger.info("Vault data loaded!");
	}

	/**
	 * Plugin shutdown method. Cancels all tasks, saves all data files before
	 * exiting.
	 */
	@Override
	public void onDisable() {
		cancelAllTasks();
		saveData();
		// TODO: Add ON_DISABLE TaskType
	}
}

/*
 * DWAPI core plugin for data storage and provision in Minecraft Bukkit plugins.
 * Copyright (C) 2022 DoubleWhale
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 */