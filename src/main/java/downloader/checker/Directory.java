package downloader.checker;

import java.io.File;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import downloader.Main;

public class Directory {
	private static Logger logger = LoggerFactory
			.getLogger(Main.class.getName());
	private ResourceBundle rb = ResourceBundle.getBundle("properties");

	public void existDirectory() {
		File f = new File(rb.getString("filePath"));

		if (!f.exists()) {
			f.mkdirs();
		}
	}

	public void existDirectory(String path) {
		File f = new File(rb.getString("filePath") + path);

		if (!f.exists()) {
			f.mkdirs();
		}
	}
}
