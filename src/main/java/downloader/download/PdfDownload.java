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

	private ResourceBundle rb = ResourceBundle.getBundle("properties");

	public void getFIle() throws IOException {
		// �ڑ���ݒ�
		URL url = new URL(rb.getString("downloadLocation"));
		URLConnection conn = url.openConnection();
		// ������
		InputStream in = null;
		FileOutputStream out = null;
		try {
			in = conn.getInputStream();
			// ���ݎ����擾
			ZonedDateTime now = ZonedDateTime.now();
			String nowString = now.format(DateTimeFormatter
					.ofPattern("yyyyMMdd"));

			File file = new File(rb.getString("tmpPath") + nowString
					+ rb.getString("fileName"));
			out = new FileOutputStream(file, false);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new IOException(e);
		} finally {
		}
	}
}