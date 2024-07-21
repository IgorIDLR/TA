package crudTask1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FileManipulator {

	private WorkPath current;
	
	private FileManipulator(WorkPath current) { this.current = current; }
	
	public static FileManipulator create(WorkPath current) {
		Objects.requireNonNull(current);
		return new FileManipulator(current);
	}
	
	public void lastModification() { System.out.println(new Date(this.current.getFile().lastModified())); }
	
	public void separatorSize(FileLength fl) {
		var obj = separator();
		if (obj != null) {
			int count = 0;
			for (var f : obj) {
				System.out.println(count == 0 ? "Folder:" : "File");
				for (var temp : f) System.out.printf("\t%s - Size: %s\n", temp.getName(), fl.get(temp));
				count++;
			}
		}
	}
	
	public void separatorCount() {
		var obj = separator();
		int countFile = 0;
		int countFolder = 0;
		if (obj != null) {
			countFile = obj.get(0).size();
			countFolder = obj.get(1).size();
		}
		System.out.println(String.format("countFile: %d\ncountFolder: %d", countFile, countFolder));
	}
	
	private List<List<File>> separator() {
		File[] temp = FileExecutor.getList(this.current);
		List<List<File>> res = null;
		if (temp != null) {
			res = new ArrayList<List<File>>() {{
				add(new ArrayList<File>());
				add(new ArrayList<File>());
			}};
			for (var f : temp) {
				if (FileExecutor.getTypeFile(f.getName()) == null) res.get(0).add(f);
				else res.get(1).add(f);
			}
		}
		return res;
	}
	
	public int countAll() {
		File[] temp = FileExecutor.getList(this.current);
		return temp == null ? 0 : temp.length;
	}
	
	public List<File> serch(String match) {
		int positionSplite = this.serchSplitSimpol(match);
		return serchSeparator(positionSplite, match);
	}
	
	private List<File> serchSeparator(int position, String matcher) {
		int length = matcher.length();
		final char delimetr = position < 0 ? '?' : matcher.charAt(position);
		
		List<File> list = null;
		String pref = (pref = matcher.substring(0, delimetr == '?' ? length : position)).length() == 0 ? null : pref;
		String suff = pref != null && pref.length() != length ?  matcher.substring(++position) : null;
		
		if (pref != null) list = sertchPref(matcher, pref, pref.length() == length);
		if (suff != null) list = sertchSuff(matcher, suff, list, suff.length() == length);
		
		if (delimetr == '_') {
			for (int i = list.size() - 1; i >= 0; i--) {
				String name = FileExecutor.getNameFile(list.get(i).getName());
				int subLangth = name.length();
				if (subLangth != length) list.remove(i);
			}
		}
		return list;
	}
	
	private List<File> sertchPref(String matcher, String pref, boolean exact) {
		File[] in = this.current.getFile().listFiles();
		var out = new ArrayList<File>();
		for (var o : in) {
			String name = FileExecutor.getNameFile(o.getName());
			if (exact ? name.equals(pref) : name.startsWith(pref)) out.add(o);
		}
		return out;
	}
	
	private List<File> sertchSuff(String matcher, String suff, List<File> list, boolean exact) {
		boolean empty = list == null;
		File[] temp;
		if (empty) {
			list = new ArrayList<>();
			temp = this.current.getFile().listFiles();
			for (var o : temp) {
				String name = FileExecutor.getNameFile(o.getName());
				if (exact ? name.equals(suff) : name.endsWith(suff)) list.add(o);
			}
		} else {
			for (int i = list.size() - 1; i >= 0; i--) {
				String name = FileExecutor.getNameFile(list.get(i).getName());
				if (exact ? !name.equals(suff) : !name.endsWith(suff)) list.remove(i);
			}
		}
		return list;
	}
	
	private int serchSplitSimpol(String match) {
		int length = match.length();
		for (int i = 0; i < length; i++) {
			if (match.charAt(i) == '*' || match.charAt(i) == '_') return i;
		}
		return -1;
	}
}
