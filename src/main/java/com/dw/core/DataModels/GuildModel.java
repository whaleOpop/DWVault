package com.dw.core.DataModels;

import java.util.*;
import java.util.regex.Pattern;
import javax.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.serialization.*;

/**
 * GuildModel class to save guilds' data to
 *
 * @author whaleOpop, BlackWarlow
 */
@SerializableAs("GuildModel")
public class GuildModel implements ConfigurationSerializable {
	/**
	 * Enum of all guild roles descending in permissions:<br>
	 * - Creator is able to op, deop players, delete guild + Operator
	 * permissions.<br>
	 * - Operator is able to add requested, exile players with lower level
	 * permissions, modify guild + Member permissions.<br>
	 * - Member is able to view guild info + Requested permissions.<br>
	 * - Requested is able to list all players in a guild, leave guild.<br>
	 * <br>
	 * - Not in guild players are able to create new guilds and list all guilds.<br>
	 * - Everyone is able to use help command.
	 */
	public enum RoleGuild {
		Creator, Operator, Member, Requested
	}

	// Model fields
	private String guildName;
	private String guildPrefix;
	private String guildColor;
	private String creatorName;
	private ArrayList<PlayerModel> players = new ArrayList<PlayerModel>();

	/**
	 * Color names supported by Mojang team command.
	 */
	private static final List<String> supportedColorNames = Arrays
			.asList(new String[] { "aqua", "black", "blue", "dark_aqua", "dark_blue", "dark_gray", "dark_green",
					"dark_purple", "dark_red", "gold", "gray", "green", "light_purple", "red", "white", "yellow" });

	/**
	 * Empty guild constructor.<br>
	 * Warning: does not test for already existing guilds!
	 *
	 * @param nameGuild   String guild name
	 * @param prefixGuild String guild prefix
	 * @param colorGuild  String guild color
	 */
	public GuildModel(String creatorName, String nameGuild, String prefixGuild, String colorGuild) {
		this.creatorName = creatorName;

		// Create new Minecraft:team
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		Server server = Bukkit.getServer();
		server.dispatchCommand(console, "team add " + creatorName + " {\"text\":\"" + nameGuild + "\"}");

		// Add creator
		players.add(new PlayerModel(creatorName, true, RoleGuild.Creator));
		server.dispatchCommand(console, "team join " + creatorName + " " + creatorName);

		// Modify team and guild settings
		setGuildColor(colorGuild);
		setGuildName(nameGuild);
		setGuildPrefix(prefixGuild);

	}

	/**
	 * Another constructor with straight fields setting.<br>
	 * Used for serialization/deserialization and cloning.
	 *
	 * @param nameGuild    String guild name
	 * @param prefixGuild  String guild prefix
	 * @param colorGuild   String guild color
	 * @param guildPlayers ArrayList of PlayerModel guild players
	 */
	public GuildModel(String creatorName, String nameGuild, String prefixGuild, String colorGuild,
			List<PlayerModel> guildPlayers) {
		this.creatorName = creatorName;
		this.guildName = nameGuild;
		this.guildPrefix = prefixGuild;
		this.guildColor = colorGuild;
		if (players != null) {
			this.players = (ArrayList<PlayerModel>) guildPlayers;
		}
	}

	// Useful methods

	/**
	 * Adds a player to the players ArrayList if he was not added.<br>
	 * Handles CREATOR role singleton.
	 *
	 * @param playerName Player name
	 * @param status     Boolean true if player is active, false otherwise (null =
	 *                   false)
	 * @param role       A RoleGuild enum value (null = Requested)
	 * @return true if user had been added false otherwise
	 */
	@Nonnull
	public Boolean addPlayer(String playerName, @Nullable Boolean status, @Nullable RoleGuild role) {
		if (!hasPlayer(playerName)) {
			// Check players array - there can only be one Creator player
			if (role == RoleGuild.Creator) {
				for (PlayerModel pm : players) {
					if (pm.getRole() == RoleGuild.Creator)
						return false;
				}
			}
			if (role == null)
				role = RoleGuild.Requested;

			players.add(new PlayerModel(playerName, status, role));
			return true;
		}
		return false;
	}

	/**
	 * Searches for player in guild list by player name.
	 *
	 * @param playerName Player name
	 * @return PlayerModel object or null
	 */
	@Nullable
	public PlayerModel getPlayerByName(String playerName) {
		PlayerModel playerFound = null;
		for (PlayerModel player : players) {
			if (player.getName() == playerName) {
				playerFound = player;
				break;
			}
		}
		return playerFound;
	}

	/**
	 * Searches for first PlayerModel in guild list by role.
	 *
	 * @param playerRole Player RoleGuild role
	 * @return PlayerModel object or null
	 */
	@Nullable
	public PlayerModel getPlayerByRole(RoleGuild playerRole) {
		PlayerModel playerFound = null;
		for (PlayerModel player : players) {
			if (player.getRole() == playerRole) {
				playerFound = player;
				break;
			}
		}
		return playerFound;
	}

	/**
	 * Searches for player in a guild by player name.
	 *
	 * @param playerName String player name
	 * @return true if player is found in a guild, false otherwise
	 */
	@Nonnull
	public Boolean hasPlayer(String playerName) {
		return getPlayerByName(playerName) != null;
	}

	/**
	 * Removes player from players.
	 *
	 * @param playerName Player to remove name
	 * @return true if player was removed, false otherwise
	 */
	@Nonnull
	public Boolean removePlayer(String playerName) {
		for (PlayerModel player : players) {
			if (player.getName() == playerName) {
				players.remove(player);
				return true;
			}
		}
		return false;
	}

	/**
	 * Ops player according to their role.
	 *
	 * @param playerName Player to op name
	 * @return true if operation is successful, else false
	 */
	@Nonnull
	public Boolean opPlayer(String playerName) {
		PlayerModel player = getPlayerByName(playerName);
		if (player == null)
			return false;

		if (player.getRole() == RoleGuild.Member) {
			player.setRole(RoleGuild.Operator);
			return true;
		}
		return false;
	}

	/**
	 * Deops player according to their role.
	 *
	 * @param playerName Player to deop name
	 * @return true is operation is successful, else false
	 */
	public Boolean deopPlayer(String playerName) {
		PlayerModel player = getPlayerByName(playerName);
		if (player == null)
			return false;

		if (player.getRole() == RoleGuild.Operator) {
			player.setRole(RoleGuild.Member);
			return true;
		}
		return false;
	}

	/**
	 * Accepts REQUESTED role members into guild;
	 * 
	 * @param playerName String player name
	 * @return true if player was accepted, false otherwise
	 */
	public Boolean acceptPlayer(String playerName) {
		PlayerModel player = getPlayerByName(playerName);
		if (player == null)
			return false;

		if (player.getRole() == RoleGuild.Requested) {
			player.setRole(RoleGuild.Member);
			return true;
		}
		return false;
	}

	// Setters and getters

	/**
	 * guildColor setter with regex pattern for hex color and check with
	 * supportedColorNames.
	 *
	 * @param guildColor String guild color (if null will not be set)
	 * @return true if color was set, false otherwise
	 */
	public Boolean setGuildColor(String guildColor) {
		if (guildColor == null)
			return false;

		if (guildColor.startsWith("#") && (guildColor.length() == 7)) {
			if (Pattern.matches("#[[0-9]a-f]{6}", guildColor)) {
				this.guildColor = guildColor;
				return true;
			}

		} else if (GuildModel.supportedColorNames.contains(guildColor)) {
			this.guildColor = guildColor;
			return true;
		}
		return false;
	}

	/**
	 * guildName setter with regex symbol escape.
	 *
	 * @param guildName String guild name
	 * @return new String that guildName was set to after escaping
	 */
	public String setGuildName(String guildName) {
		this.guildName = guildName.replaceAll("[-+=*&|\\\\/@{}.^:,<>\\[\\]!?\\'\\\"]", "");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team modify " + creatorName
				+ " displayName [{\"text\" : \"" + guildName + ",\"color\":\"" + guildColor + "\"}]");
		return this.guildName;
	}

	/**
	 * guildPrefix setter with regex symbol escape.
	 *
	 * @param guildPrefix String guild prefix
	 * @return new String guildPrefix was set to after escaping
	 */
	public String setGuildPrefix(String guildPrefix) {
		this.guildPrefix = guildPrefix.replaceAll("[-+=*&|\\\\/@{}.^:,<>\\[\\]!?\\'\\\"]", "");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team modify " + creatorName
				+ " prefix [{\"text\":\"[" + guildPrefix + "] \",\"color\":\"" + guildColor + "\"}]");
		return this.guildPrefix;
	}

	/**
	 * guildColor getter.
	 *
	 * @return guildColor String guild color
	 */
	public String getGuildColor() {
		return guildColor;
	}

	/**
	 * guildName getter.
	 *
	 * @return guildName String guild name
	 */
	public String getGuildName() {
		return guildName;
	}

	/**
	 * guildPrefix getter.
	 *
	 * @return guildPrefix String guild prefix
	 */
	public String getGuildPrefix() {
		return guildPrefix;
	}

	/**
	 * creatorName getter.
	 *
	 * @return Guild creator name
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * players getter.
	 * 
	 * @return players ArrayList
	 */
	public ArrayList<PlayerModel> getPlayers() {
		return players;
	}

	// Serialization handling and debug

	/**
	 * Implements ConfigurationSerializable interface methods. Creates GuildModel
	 * object from Map args data.
	 * 
	 * @param args Map of <String, Object> to load data from.
	 * @return GuildModel class instance.
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public static GuildModel deserialize(Map<String, Object> args) {
		String guildName = null;
		String guildPrefix = null;
		String guildColor = null;
		String creatorName = null;
		List<PlayerModel> players = null;

		if (args.containsKey("guildName")) {
			guildName = args.get("guildName").toString();
			if (args.containsKey("guildPrefix")) {
				guildPrefix = args.get("guildPrefix").toString();
				if (args.containsKey("guildColor")) {
					guildColor = args.get("guildColor").toString();
					if (args.containsKey("creatorName")) {
						creatorName = args.get("creatorName").toString();
						if (args.containsKey("players")) {
							players = (ArrayList<PlayerModel>) args.get("players");
							return new GuildModel(creatorName, guildName, guildPrefix, guildColor, players);
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Implements ConfigurationSerializable interface methods. Serializes data from
	 * CoinModel instance.
	 * 
	 * @return Map of String, Object of field data.
	 */
	@Nonnull
	public Map<String, Object> serialize() {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("guildName", this.guildName);
		fields.put("guildPrefix", this.guildPrefix);
		fields.put("guildColor", this.guildColor);
		fields.put("creatorName", this.creatorName);
		fields.put("players", this.players);
		return fields;
	}

	/**
	 * toString override method.
	 * 
	 * @return String GuildModel representation
	 */
	@Override
	public String toString() {
		String msg = this.getClass().toString() + " -> { guildName: " + guildName + ", guildPrefix: " + guildPrefix
				+ ", guildColor: " + guildColor + ", creatorName: " + creatorName + ", players = [\n";

		for (PlayerModel pm : players) {
			msg += "  " + pm.toString() + ",\n";
		}

		return msg + "] }";
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
