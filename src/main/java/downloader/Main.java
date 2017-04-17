package downloader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import downloader.checker.Directory;
import downloader.download.PdfDownload;
import downloader.parser.parserWrapper;

public class Main {
	private static Logger logger = LoggerFactory
			.getLogger(Main.class.getName());

	private static List<Map<String, String>> targets = new ArrayList<Map<String, String>>();


	public static void main(String[] args) {
		Map<String, String> target1 = new HashMap<String, String>();
		target1.put("url", "http://kei008220.webcrow.jp/seika.pdf");
		target1.put("filename", "仙台市中央市場日報.pdf");
		target1.put("path", "seikapdf");
		targets.add(target1);

		Map<String, String> target2 = new HashMap<String, String>();
		target2.put("url", "http://kei008220.webcrow.jp/seikamitoshi.pdf");
		target2.put("filename", "入荷価格見通し.pdf");
		target2.put("path", "predictionpdf");
		targets.add(target2);

		targets.stream().forEach(entry -> {
			// TODO Auto-generated method stub
			LocalDateTime downloadTime = LocalDateTime.now();
			logger.info(downloadTime.toString() + "作業を開始します");
			logger.info("-TargetURL:"+ entry.get("filename"));

			Directory dir = new Directory(entry.get("path"));
			dir.mkdir();

			// PDFダウンロード
			try {
				PdfDownload pdf = new PdfDownload(entry.get("filename"), entry.get("url"), entry.get("path"));
				pdf.getFIle();
				logger.info("pdf一時保存完了しました");
				// pdf名の変更
				parserWrapper parser = new parserWrapper(entry.get("filename"), entry.get("url"), entry.get("path"));
				logger.info("pdfファイル名変更を行います");
				boolean result = parser.changeFileName();
				if (result) {
					logger.info("pdf一時保存を破棄します");
					boolean delResult = parser.deleteFile();
					if (delResult) {
						logger.info("pdfファイル名変更を完了しました");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("pdfDownloadError :", e);
			}

		logger.info(downloadTime.toString() + "作業を終了します");
		});

		}
}
