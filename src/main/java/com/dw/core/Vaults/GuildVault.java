package com.dw.core.Vaults;

import java.util.*;
import javax.annotation.*;
import org.bukkit.configuration.serialization.*;
import com.dw.core.DataModels.GuildModel;

/**
 * GuildVault GuildModel array serializer.
 * 
 * @author BlackWarlow, WhaleOpop
 *
 */
@SerializableAs("GuildVault")
public class GuildVault extends ModelVault<GuildModel> {
	/**
	 * Ctor.
	 * 
	 * @param list List of GuildModel initial models
	 */
	public GuildVault(@Nullable List<GuildModel> list) {
		super(list);
	}

	/**
	 * toString override method.
	 * 
	 * @return String Vault representation
	 */
	@Override
	public String toString() {
		String msg = this.getClass().toString() + " -> { data: [\n";

		for (GuildModel obj : models) {
			msg += obj.toString() + ",\n";
		}

		return msg + "]";
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