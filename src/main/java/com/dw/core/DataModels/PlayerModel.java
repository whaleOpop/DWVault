package com.dw.core.DataModels;

import java.util.*;
import javax.annotation.*;
import org.bukkit.configuration.serialization.*;
import com.dw.core.DataModels.GuildModel.RoleGuild;

/**
 * PlayerModel class to store guild player data.
 * 
 * @author whaleOpop, BlackWarlow
 *
 */
@SerializableAs("PlayerModel")
public class PlayerModel implements ConfigurationSerializable {

	// Model fields
	private String name;
	private Boolean active;
	private RoleGuild role;

	/**
	 * Simple constructor.
	 * 
	 * @param name   Player name
	 * @param active Boolean is player active, if null will be set to false
	 * @param role   RoleGuild role value of created player
	 */
	public PlayerModel(String name, @Nullable Boolean active, @Nullable RoleGuild role) {
		this.name = name;
		if (active == null)
			active = false;
		this.active = active;
		if (role == null)
			role = RoleGuild.Requested;
		this.role = role;
	}

	// Useful methods

	/**
	 * Tests if player has Member level permissions in a guild - so roles Creator,
	 * Operator and Member, but not Requested.
	 * 
	 * @return true if player's role is Creator, Operator or Member, false otherwise
	 */
	@Nonnull
	public Boolean testMembership() {
		return (role == RoleGuild.Member) || (role == RoleGuild.Operator) || (role == RoleGuild.Creator);
	}

	/**
	 * Tests if player has Operator level permissions in a guild - so roles Creator
	 * and Operator.
	 * 
	 * @return true if player's role is Operator or Creator, false otherwise
	 */
	@Nonnull
	public Boolean testOperatorship() {
		return (role == RoleGuild.Operator) || (role == RoleGuild.Creator);
	}

	/**
	 * Tests if player's role is Creator level.
	 * 
	 * @return true if player's role is Creator, false otherwise
	 */
	@Nonnull
	public Boolean testCreatorship() {
		return role == RoleGuild.Creator;
	}

	/**
	 * Tests for upper available permission by RoleGuild role.
	 * 
	 * @param testRole Lowest role to test
	 * @return true if player possesses this role, false otherwise
	 */
	@Nonnull
	public Boolean testRole(RoleGuild testRole) {
		switch (testRole) {
		case Creator:
			return testCreatorship();
		case Operator:
			return testOperatorship();
		case Member:
			return testMembership();
		case Requested:
			return !testMembership();
		}
		return false;
	}

	// Setters and getters

	/**
	 * name getter.
	 * 
	 * @return name Player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name setter.
	 * 
	 * @param name Player name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * active getter.
	 * 
	 * @return active Player status
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * active setter.
	 * 
	 * @param active Player status
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * role getter.
	 * 
	 * @return role type of RoleGuild enum
	 */
	public RoleGuild getRole() {
		return role;
	}

	/**
	 * role setter.
	 * 
	 * @param role type of RoleGuild enum
	 */
	public void setRole(RoleGuild role) {
		this.role = role;
	}

	// Serialization and debug

	/**
	 * Implements ConfigurationSerializable interface methods. Creates PlayerModel
	 * object from Map args data.
	 * 
	 * @param args Map of <String, Object> to load data from.
	 * @return PlayerModel class instance.
	 */
	@Nullable
	public static PlayerModel deserialize(Map<String, Object> args) {
		String name = null;
		Boolean active = null;
		RoleGuild role = null;

		if (args.containsKey("name")) {
			name = args.get("name").toString();
			if (args.containsKey("active")) {
				active = Boolean.valueOf(args.get("active").toString());
				if (args.containsKey("role")) {
					role = RoleGuild.valueOf(args.get("role").toString());
					return new PlayerModel(name, active, role);
				}
			}
		}
		return null;
	}

	/**
	 * Implements ConfigurationSerializable interface methods. Serializes data from
	 * PlayerModel instance.
	 * 
	 * @return Map<String, Object> of field data.
	 */
	@Nonnull
	public Map<String, Object> serialize() {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("name", this.getName());
		fields.put("active", this.getActive());
		fields.put("role", this.getRole().toString());
		return fields;
	}

	/**
	 * toString override method.
	 * 
	 * @return String PlayerModel representation
	 */
	@Override
	public String toString() {
		return this.getClass().toString() + " -> { name: " + name + ", active: " + active.toString() + ", role: "
				+ role.toString() + " }";
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
