package net.sfka.sac.sentryfiles;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class PackedSentryFile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name, filename;
	private byte[] bytes;
	
	public PackedSentryFile(File file) throws IOException {
		this.name = file.getName();
		this.filename = name;
		this.bytes = Files.readAllBytes(file.toPath());
	}
	
	public PackedSentryFile(String name, String filename,  byte[] bytes) {
		this.name = name;
		this.filename = filename;
		this.bytes = bytes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
