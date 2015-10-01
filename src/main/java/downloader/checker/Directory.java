package downloader.checker;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import downloader.Main;

public class Directory {
	private static Logger logger = LoggerFactory
			.getLogger(Main.class.getName());

	public void existDirectory() {
		File f = new File("seikapdf");

		if (!f.exists()) {
			f.mkdirs();
		}
	}
}
