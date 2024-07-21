package crudTask1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkPath {

	private File file;
	
	private WorkPath(String path) { this.file = new File(path);}
	
	public static WorkPath create(String path) { return new WorkPath(path); }
	
	public File getFile() { return this.file; }
	public void setFile(File file) { this.file = file; }
	
}

enum Format {
	
	TXT(".txt"),
	CSV(".csv");
	
	String format;
	
	Format(String format) {
		this.format = format;
	}
}
