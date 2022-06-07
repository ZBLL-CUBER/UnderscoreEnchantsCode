package com.roughlyunderscore.enchs.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;

import java.io.BufferedWriter;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor @EqualsAndHashCode @ToString // just so that my ide stopped complaining
public class Debug {
	private final boolean log;
	private final BufferedWriter writer;

	@SneakyThrows
	public void log(String message) {
		if (log) writer.write("[" + Date.from(Instant.now()) + "] " + message + "\n");
	}
}
