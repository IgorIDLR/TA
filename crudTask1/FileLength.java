package crudTask1;

import java.io.File;

public enum FileLength {
	Byte("byte") {
		@Override public String get(File file) { return String.format("%d byte", file.length()); }
	},
	KiloByte("kb") {
		@Override public String get(File file) { return String.format("%d kb", file.length() / 1024);}
	},
	MegaByte("mb") {
		@Override public String get(File file) { return String.format("%d mb", file.length() / Math.pow(1024, 2));};
	};
	
	String name;
	
	FileLength(String name) { this.name = name; }
	
	public abstract String get(File file);
}
