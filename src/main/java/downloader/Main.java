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
		LocalDateTime downloadTime = LocalDateTime.now();
		logger.info(downloadTime.toString() + "作業を開始します");

		Directory dir = new Directory();
		dir.existDirectory();

		// PDFダウンロード
		try {
			PdfDownload pdf = new PdfDownload();
			pdf.getFIle();
			logger.info("pdf一時保存完了しました");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("pdfDownloadError :", e);
		}

		// pdf名の変更
		parserWrapper parser = new parserWrapper();
		logger.info("pdfファイル名変更を行います");
		boolean result = parser.changeFileName();
		if (result) {
			logger.info("pdf一時保存を破棄します");
			boolean delResult = parser.deleteFile();
			if (delResult) {
				logger.info("pdfファイル名変更を完了しました");
			}
		}
		logger.info(downloadTime.toString() + "作業を終了します");
	}
}
