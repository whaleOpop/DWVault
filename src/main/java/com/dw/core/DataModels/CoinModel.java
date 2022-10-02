package com.dw.core.DataModels;

import java.util.*;
import javax.annotation.*;
import org.bukkit.configuration.serialization.*;

/**
 * CoinModel class to save wallets to
 * 
 * @author whaleOpop, BlackWarlow
 *
 */
@SerializableAs("CoinModel")
public class CoinModel implements ConfigurationSerializable {

	// Model fields
	private String walletName;
	private Boolean isGuild;
	private Double coins;

	/**
	 * Simple constructor with all model fields.
	 * 
	 * @param walletName Wallet name
	 * @param isGuild    Boolean value is wallet designated to a guild (null =
	 *                   false)
	 * @param coins      Amount of coins currency in the wallet (null = 0.0)
	 */
	public CoinModel(String walletName, @Nullable Boolean isGuild, @Nullable Double coins) {
		this.walletName = walletName;

		if (isGuild == null)
			isGuild = false;

		this.isGuild = isGuild;
		if (coins == null)
			coins = 0.0;

		this.coins = coins;
	}

	/**
	 * Wallet constructor without coins.
	 * 
	 * @param walletName Wallet name
	 * @param isGuild    Boolean value is wallet designated to a guild (null =
	 *                   false)
	 */
	public CoinModel(String walletName, @Nullable Boolean isGuild) {
		this.walletName = walletName;

		if (isGuild == null)
			isGuild = false;

		this.isGuild = isGuild;
		this.coins = 0.0;
	}

	/**
	 * Another much simpler constructor for player wallets.
	 * 
	 * @param walletName Wallet name
	 */
	public CoinModel(String walletName) {
		this.walletName = walletName;
		this.isGuild = false;
		this.coins = 0.0;
	}

	// Setters and getters

	/**
	 * walletName getter.
	 * 
	 * @return String walletName
	 */
	@Nonnull
	public String getWalletName() {
		return walletName;
	}

	/**
	 * walletName setter<br>
	 * On null does not set walletName
	 * 
	 * @param walletName Wallet name to set walletName to
	 */
	public void setWalletName(String walletName) {
		if (walletName == null)
			return;

		this.walletName = walletName;
	}

	/**
	 * isGuild getter.
	 * 
	 * @return Boolean isGuild
	 */
	@Nonnull
	public Boolean getIsGuild() {
		return isGuild;
	}

	/**
	 * isGuild setter.<br>
	 * On null does not set isGuild.
	 * 
	 * @param isGuild Boolean wallet is guild's wallet
	 */
	public void setIsGuild(Boolean isGuild) {
		if (isGuild == null)
			return;

		this.isGuild = isGuild;
	}

	/**
	 * Converts Double coins to Integer balance.<br>
	 * coins getter.
	 * 
	 * @return Integer wallet coins balance
	 */
	@Nonnull
	public Integer getBalance() {
		return coins.intValue();
	}

	/**
	 * Converts Integer balance to Double coins.<br>
	 * coins setter.<br>
	 * On null does not set coins.
	 * 
	 * @param balance Integer wallet balance
	 */
	public void setBalance(Integer balance) {
		if (balance == null)
			return;

		coins = Double.valueOf(balance);
	}

	/**
	 * coins getter.
	 * 
	 * @return Double wallet coins
	 */
	@Nonnull
	public Double getCoins() {
		return coins;
	}

	/**
	 * coins setter.<br>
	 * On null does not set coins.
	 * 
	 * @param coins Coins amount to set coins to
	 */
	public void setCoins(Double coins) {
		if (coins == null)
			return;

		this.coins = coins;
	}

	// Serialization and debug

	/**
	 * Implements ConfigurationSerializable interface methods. Creates CoinModel
	 * object from Map args data.
	 * 
	 * @param args Map of <String, Object> to load data from.
	 * @return CoinModel class instance.
	 */
	@Nullable
	public static CoinModel deserialize(Map<String, Object> args) {
		String walletName = null;
		Boolean isGuild = null;
		Double coins = null;

		if (args.containsKey("walletName")) {
			walletName = args.get("walletName").toString();
			if (args.containsKey("isGuild")) {
				isGuild = Boolean.valueOf(args.get("isGuild").toString());
				if (args.containsKey("coins")) {
					coins = Double.valueOf(args.get("coins").toString());
					return new CoinModel(walletName, isGuild, coins);
				}
			}
		}

		return null;
	}

	/**
	 * Implements ConfigurationSerializable interface methods. Serializes data from
	 * CoinModel instance.
	 * 
	 * @return Map<String, Object> of field data.
	 */
	@Nonnull
	public Map<String, Object> serialize() {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("walletName", this.getWalletName());
		fields.put("isGuild", this.getIsGuild());
		fields.put("coins", this.getCoins());
		return fields;
	}

	/**
	 * toString override method.
	 * 
	 * @return String CoinModel representation
	 */
	@Override
	public String toString() {
		return this.getClass().toString() + " -> { walletName: " + walletName + ", isGuild: " + isGuild.toString()
				+ ", coins: " + coins.toString();
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
