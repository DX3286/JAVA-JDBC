package my.api.access.work;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class JDBC_Filter extends FileFilter {
	
	private String ext;
	private String desc;
	
	public JDBC_Filter(String ext, String desc) {
		this.ext = ext;
		this.desc = desc;
	}

	public String getExt(File f) {
		String ext = null;
		String filename = f.getName();
		int index = filename.lastIndexOf(".");
		if (index > 0 && index < filename.length() - 1) {
			ext = filename.substring(index + 1).toLowerCase();
		}
		return ext;
	}

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String ext = this.getExt(f);
		if (ext != null) {
			if (ext.equals(this.ext)) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	public String getDescription() {
		return this.desc;
	}
}