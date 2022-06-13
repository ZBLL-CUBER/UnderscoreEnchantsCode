package com.roughlyunderscore.enchs.parsers;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.entity.Player;

@EqualsAndHashCode @ToString
public class PDCPlaceholder {
	private final Player[] players;
	private final int size;

	public PDCPlaceholder(Player... players) {
		this.players = players;
		this.size = players.length;
	}

	public final Player[] getPlayers() {
		return players;
	}

	public final int getSize() {
		return size;
	}
}
