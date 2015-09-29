package downloader;

import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import downloader.checker.Directory;
import downloader.download.PdfDownload;
import downloader.parser.parserWrapper;

public class Main {
	private static Logger logger = LoggerFactory
			.getLogger(Main.class.getName());

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Directory dir = new Directory();
		dir.existDirectory();

		LocalDateTime downloadTime = LocalDateTime.now();
		logger.info(downloadTime.toString() + "のデータ取得を開始します");

		// pdfファイルのダウンロード
		try {
			PdfDownload pdf = new PdfDownload();
			pdf.getFIle();
			logger.info("pdfのダウンロードが完了しました。");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("pdfDownloadError :", e);
		}

		// pdfファイル名の変更
		parserWrapper parser = new parserWrapper();
		boolean result = parser.changeFileName();
		if (result) {
			logger.info("pdfのファイル名変更が完了しました");
			boolean delResult = parser.deleteFile();
			if (delResult) {
				logger.info("pdfファイルの削除が完了しました");
			}
		}
	}
}
