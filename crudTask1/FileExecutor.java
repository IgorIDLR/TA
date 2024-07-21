package crudTask1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileExecutor {
	
	public static void view(WorkPath file) {
		File[] files = getList(file);
		if (files != null) {
			for (var f : files) System.out.println(f.getName());
		}
	}
	
	public static File[] getList(WorkPath file) {
		if (file.getFile().canExecute()) {
			return file.getFile().listFiles();
		}
		return null;
	}
	
	public static boolean rename(String newName, WorkPath file) {
		boolean res = false;
		if (Objects.isNull(newName) && Objects.isNull(file)) return res;
		
		if (file.getFile().canExecute()) {
			String path = file.getFile().getParent().concat(File.separator + newName);
			File newFile = new File(path);
			res = file.getFile().renameTo(newFile);
			PathExecutor.repath(file, newFile);
		}
		return res;
	}
	
	public static boolean delete(WorkPath file) {
		if (file.getFile().canExecute()) {
			return file.getFile().delete();
		}
		return false;
	}
	 
	public static boolean createNewFile(WorkPath file) throws IOException { 
		return file.getFile().canExecute() ? file.getFile().createNewFile() : false;
	}
	
	public static boolean createNewDirectory(WorkPath fileParent, String child, boolean forced) {
		if (Objects.isNull(fileParent) && Objects.isNull(child)) return false;
		
		if (fileParent.getFile().canExecute()) {
			PathExecutor.repath(fileParent, child);
			return fileParent.getFile().mkdir();
		} 

		if (forced) return forcedCreaterFolder(fileParent, child, false);
		
		return false;
	}
	
	public static boolean forcedCreaterFolder(WorkPath fileParent, String child, boolean ignorType) {
		
		PathExecutor.repath(fileParent, child);
		var file = fileParent.getFile();
		PathExecutor.normalizePath(fileParent);
		String pathFile = file.getAbsolutePath();
		
		int length = pathFile.length();
		int subLength = fileParent.getFile().getAbsolutePath().length();

		String[] temp = pathFile.substring(++subLength, length).replace('\\', '/').split("/");
		
		for (int i = 0; i < temp.length; i++) {
			String type = null;
			if (!ignorType) {
				if (getTypeFile(temp[i]) != null) break;
			}
			type = getNameFile(temp[i]);
			PathExecutor.repath(fileParent, type);
			fileParent.getFile().mkdir();
		}
		return file.canExecute();
	}
	
	public static String getTypeFile(WorkPath file) {
		return getTypeFile(file.getFile().getName());
	}
	
	public static String getTypeFile(String file) {
		final String split = "\\.";
		String[] type = file.split(split);
		int length = type.length;
		return length > 1 ? type[--length] : null;
	}
	
	public static String getNameFile(String file) {
		String type = getTypeFile(file);
		if (type == null) return file;
		int position = file.length() - type.length();
		return file.substring(0, --position);
	}
	
	public static String getNameFile(WorkPath file) {
		String current = PathExecutor.getCurrent(file);
		return getNameFile(current);
	}
}
