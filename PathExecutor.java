package crudTask1;

import java.io.File;
import java.util.Objects;

public class PathExecutor {

	public static void prevDirectory(WorkPath crud) { PathExecutor.repath(crud, crud.getFile().getParentFile()); }
	
	/**
	 * Finds the parent path ready for execution:
	 * 	if file or directory not ready for working,
	 *  then in shift to the parent until a folder is found ready to execution.
	 *  The offset is made by the method: {@code prevDirectory} receiving parameter <b>FilePath</b>
	 */
	public static void normalizePath(WorkPath filePath) {
		if (!filePath.getFile().canExecute()) {
			prevDirectory(filePath);
			normalizePath(filePath);
		}
	}
	
	public static boolean isAbsPathDirectory(WorkPath path) {
		return isPathDirectory(path.getFile().getAbsolutePath());
	}
	
	public static boolean isAbsPathDirectory(String path) {
		return isPathDirectory(path);
	}
	
	public static boolean isPathDirectory(WorkPath path) throws NullPointerException {
		return isPathDirectory(getCurrent(path));
	}
	
	public static boolean isPathDirectory(String path) throws NullPointerException {
		int length = path.length();
		for (; --length >= 0 && path.charAt(length) != '.'; );
		return length == -1;
	}
	
	public static String getCurrent(WorkPath path) { return Objects.nonNull(path) ? path.getFile().getName() : null; }
	
	public static String getPrev(WorkPath path) { return Objects.nonNull(path) ? path.getFile().getParent() : null; }
	
	public static boolean isPathFile(WorkPath crud) { return !PathExecutor.isAbsPathDirectory(crud); }
	
	public static void repath(WorkPath path, String next) { 
			String newPath = path.getFile()
					.getAbsolutePath()
					.concat(File.separator + next);
			repath(path, new File(newPath));
	 }
	
	public static void repath(WorkPath current, File newPath) { current.setFile(newPath); } 
}
