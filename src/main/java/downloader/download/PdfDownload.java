package downloader.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PdfDownload {

	private String url;
	private String fileName;
	private String path;
	private ResourceBundle rb = ResourceBundle.getBundle("properties");
	private PdfDownload(){

	}

	public PdfDownload(String targetFileName, String targetUrl, String targetPath){
		fileName = targetFileName;
		url = targetUrl;
		path = targetPath;
	}

	public void getFIle() throws IOException {
		URL url = new URL(this.url);
		URLConnection conn = url.openConnection();

		InputStream in = null;
		FileOutputStream out = null;
		try {
			in = conn.getInputStream();

			ZonedDateTime now = ZonedDateTime.now();
			String nowString = now.format(DateTimeFormatter
					.ofPattern("yyyyMMdd"));

			File file = new File(this.path + "¥¥" + nowString + this.fileName);
			out = new FileOutputStream(file, false);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
		}
	}
}
