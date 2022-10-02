package com.dw.core.Tasks;

import javax.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import com.dw.core.DWVault;
import com.dw.core.Hooks.PluginHook;

/**
 * Simple abstract wrapper class extending BukkitRunnable.<br>
 * Adds type, name, DWAPI plugin reference and plugin hook to use.
 * 
 * @author whaleOpop, BlackWarlow
 *
 */
public abstract class Task extends BukkitRunnable {
	/**
	 * Task type to load task from config.yml file.
	 */
	public TaskType type;
	public String name;
	public PluginHook hook;

	/**
	 * DW core plugin.
	 */
	protected DWVault coreInstance = (DWVault) Bukkit.getPluginManager().getPlugin("DWAPI");

	/**
	 * Ctor.
	 * 
	 * @param hook Plugin hook
	 */
	Task(@Nonnull PluginHook hook, TaskType type) {
		this.hook = hook;
		this.type = type;
		this.name = this.getClass().getName();
	}

	/**
	 * Issues this task according to logic. Implements with override issue() method.
	 * 
	 * @param delay    the delay to start the task
	 * @param duration the period duration between reoccuring tasks
	 */
	public void issue(Long delay, Long duration) {
	}

	/**
	 * Actual task logic. Implements with override run() method.
	 */
	public void run() {
	}

	/**
	 * Task toString override method
	 * 
	 * @return Task representation
	 */
	@Override
	public String toString() {
		return "Task(" + name + " / " + type.toString() + ")";
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