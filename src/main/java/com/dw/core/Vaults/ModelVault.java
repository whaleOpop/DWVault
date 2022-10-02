package com.dw.core.Vaults;

import java.util.*;
import javax.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.*;
import com.dw.core.DWVault;

/**
 * 
 * ModelVault class.<br>
 * Implements any <T> Vault serialization.
 * 
 * @author blackwarlow
 *
 */
@SerializableAs("ModelVault")
public class ModelVault<T> implements ConfigurationSerializable {
	/**
	 * Core plugin reference.
	 */
	protected DWVault coreInstance = (DWVault) Bukkit.getPluginManager().getPlugin("DWAPI");

	/**
	 * Main data storage List.
	 */
	protected List<T> models;

	/**
	 * Ctor.
	 * 
	 * @param list List of initial models
	 */
	public ModelVault(List<T> list) {
		if (list == null) {
			models = new ArrayList<T>();
		} else {
			models = list;
		}
	}

	/**
	 * models getter.
	 * 
	 * @return List of models
	 */
	public List<T> getModels() {
		return models;
	}

	/**
	 * Implements ConfigurationSerializable interface methods. Creates ModelVault
	 * object from Map args data.
	 * 
	 * @param args Map of String, Object to load data from.
	 * @return ModelVault class instance.
	 */
	@Nullable
	public ModelVault<T> deserialize(Map<String, Object> args) {
		@SuppressWarnings("unchecked") // TODO: Remove dumb straight cast.
		ArrayList<T> data = (ArrayList<T>) args.get("data");
		if (args.containsKey("data")) {
			return new ModelVault<T>(data);
		}
		return null;
	}

	/**
	 * Implements ConfigurationSerializable interface methods. Serializes data.
	 * 
	 * @return Map of String, Object of field data.
	 */
	@Nonnull
	public Map<String, Object> serialize() {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("data", this.models);
		return fields;
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
