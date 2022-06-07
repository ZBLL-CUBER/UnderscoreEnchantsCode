package com.roughlyunderscore.enchs.util;

import lombok.Data;

@Data
public class Pair<K, V>{
	private final K key;
	private final V value;

	public static <K, V> Pair<K, V> of(K k, V v) {
		return new Pair<>(k, v);
	}
}
