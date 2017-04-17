package downloader.checker;

import java.io.File;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import downloader.Main;

public class Directory {
	private static Logger logger = LoggerFactory
			.getLogger(Main.class.getName());
	private String path;
	private String FS = File.separator;

	private Directory(){}
	public Directory(String path){
		this.path = path;
	}

	public void mkdir() {
		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	public void mkdir(String path) {
		File f = new File(this.path + FS + path);
		if (!f.exists()) {
			f.mkdirs();
		}
	}
}
