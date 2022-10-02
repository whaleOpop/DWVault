package com.dw.core.Hooks;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.annotation.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.dw.core.DWVault;
import com.dw.core.Tasks.Task;
import com.dw.core.Tasks.TaskType;
import com.dw.core.Vaults.ModelVault;

/**
 * "Abstract" addon hook to inherit from. Handles tasks, vault save/load data.
 * 
 * @author BlackWarlow
 *
 */
public abstract class PluginHook {
	/**
	 * Tasks setup according to config.yml file.
	 */
	public List<Task> tasks = new ArrayList<Task>();

	/**
	 * Core plugin reference.
	 */
	protected DWVault coreInstance = (DWVault) Bukkit.getPluginManager().getPlugin("DWAPI");

	/**
	 * Main plugin storage vault.
	 */
	protected ModelVault<?> vault;

	/**
	 * Hook plugin name.
	 */
	public String pluginName = null;

	/**
	 * Ctor. Grabs pluginName from child class name with a little bit of hidden
	 * java.reflect and cuts Hook from the end.
	 * 
	 * @param pluginName String that will be used for storage filename
	 */
	public PluginHook() {
		this.pluginName = this.getClass().getSimpleName().replace("Hook", "");
	}

	/**
	 * Saves vault data to designated config name.yml file.
	 * 
	 * @return true if data is saved, false otherwise
	 */
	public Boolean saveData() {
		FileConfiguration fc = new YamlConfiguration();
		fc.set("data", this.vault);
		try {
			fc.save(coreInstance.getDataFolder() + File.separator + pluginName + ".yml");
		} catch (IOException e) {
			coreInstance.getLogger().severe(
					"While saving data for " + pluginName + " unexpected error happened: " + e.getLocalizedMessage());
			return false;
		}
		return true;
	}

	/**
	 * Loads vault data from designated config name.yml file.
	 * 
	 * @return true if data is loaded, false otherwise
	 */
	public Boolean loadData() {
		File file = new File(coreInstance.getDataFolder() + File.separator + pluginName + ".yml");
		try {
			file.createNewFile();
		} catch (IOException e) {
			coreInstance.getLogger().severe(
					"While loading data for " + pluginName + " unexpected error happened: " + e.getLocalizedMessage());
			return false;
		}
		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
		if (vault == null) {
			coreInstance.getLogger().severe("Data vault for " + pluginName
					+ " hook is not defined. Removing hook from core. Please check hook constructor and add vault class!");
			this.coreInstance.availableHooks.remove(this.pluginName);
			return false;
		}
		vault = vault.deserialize(fc.getValues(true));
		return true;
	}

	/**
	 * Runs all on_enable addon tasks according to config.yml file.
	 * 
	 * @param toLaunch TaskType to launch
	 */
	public void launchTasks(@Nonnull TaskType toLaunch) {
		if (tasks == null) {
			this.coreInstance.getLogger().info("[launch " + toLaunch.toString() + "] No tasks to launch, skipping!");
			return;
		}

		FileConfiguration fc = coreInstance.getConfig();
		String taskPath = "tasks." + pluginName + ".";
		for (Task task : tasks) {
			if (task.type == toLaunch) {
				if (fc.getBoolean(taskPath + task.name + ".enabled", false)) {
					task.issue(fc.getLong(taskPath + task.name + ".delay", 0),
							fc.getLong(taskPath + task.name + ".timeout", 0));
				}
			}
		}
	}

	/**
	 * Cancels all plugin-related tasks.
	 */
	public void stopAllTasks() {
		if (tasks == null)
			return;

		for (Task task : tasks) {
			try {
				task.cancel();
			} catch (IllegalStateException e) {
				// Do nothing - task isn`t launched - no need to stop it.
			}
		}
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