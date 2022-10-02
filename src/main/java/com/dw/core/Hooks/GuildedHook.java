package com.dw.core.Hooks;

import java.util.*;
import javax.annotation.*;
import com.dw.core.DataModels.GuildModel;
import com.dw.core.Tasks.AutosaveTask;
import com.dw.core.Vaults.GuildVault;

/**
 * Addon class representing Guilded plugin hook with own vault and a simple
 * autosave task.
 * 
 * @author BlackWarlow
 *
 */
public class GuildedHook extends PluginHook {
	/**
	 * Ctor.
	 */
	public GuildedHook() {
		super();
		vault = new GuildVault(null);
		tasks.add(new AutosaveTask(this));
	}

	/**
	 * Get a list of all guild names.
	 * 
	 * @return List of String of all GuildModel names.
	 */
	@SuppressWarnings("unchecked")
	@Nonnull
	public List<String> getAllGuildNames() {
		List<String> selected = new ArrayList<String>();
		for (GuildModel gm : (List<GuildModel>) vault.getModels()) {
			selected.add(gm.getCreatorName());
		}
		return selected;
	}

	/**
	 * Get a list of all guild infos.
	 * 
	 * @return List of String of all GuildModel names, creators and players count.
	 */
	@SuppressWarnings("unchecked")
	@Nonnull
	public List<String> getAllGuildInfos() {
		List<String> selected = new ArrayList<String>();
		for (GuildModel gm : (List<GuildModel>) vault.getModels()) {
			selected.add(
					gm.getGuildName() + " (" + gm.getCreatorName() + ") - " + String.valueOf(gm.getPlayers().size()));
		}
		return selected;
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
