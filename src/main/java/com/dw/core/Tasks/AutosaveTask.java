package com.dw.core.Tasks;

import com.dw.core.Hooks.PluginHook;

/**
 * Simple autosave storage task.
 *
 * @author BlackWarlow
 */
public class AutosaveTask extends Task {
	/**
	 * Simple constructor with super support.
	 */
	public AutosaveTask(PluginHook hook) {
		super(hook, TaskType.ON_ENABLE);
	}

	/**
	 * Issues task run in Bukkit.
	 * 
	 * @param delay    the delay to start the task
	 * @param duration the period duration between reoccuring tasks
	 */
	public void issue(Long delay, Long duration) {
		this.runTaskTimer(this.coreInstance, delay, duration);
	}

	/**
	 * Implements storage autosaving.
	 */
	@Override
	public void run() {
		hook.saveData();
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