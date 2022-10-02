package com.dw.core.Hooks;

import java.util.*;
import javax.annotation.*;
import com.dw.core.DataModels.CoinModel;
import com.dw.core.Tasks.AutosaveTask;
import com.dw.core.Vaults.CoinVault;

/**
 * Class representing CoinMaterial plugin hook.
 * 
 * @author BlackWarlow
 *
 */
public class CoinMaterialHook extends PluginHook {
	/**
	 * Ctor. Creates CoinMaterial hook with CoinVault and AutosaveTask.
	 */
	public CoinMaterialHook() {
		super();
		vault = new CoinVault(null);
		tasks.add(new AutosaveTask(this));
	}

	/**
	 * Get a list of all wallet names.
	 * 
	 * @param isGuild flag for only guild's wallets
	 * @return List of String of all CoinModel wallets names.
	 */
	@SuppressWarnings("unchecked")
	@Nonnull
	public List<String> getAllWalletNames(boolean isGuild) {
		List<String> selected = new ArrayList<String>();
		for (CoinModel cm : (List<CoinModel>) vault.getModels()) {
			if (cm.getIsGuild() == isGuild) {
				selected.add(cm.getWalletName());
			}
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