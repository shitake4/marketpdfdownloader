package downloader.parser;

import java.io.File;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import downloader.checker.Directory;

public class parserWrapper {
	private Logger logger = LoggerFactory.getLogger(parserWrapper.class
			.getName());
	private ResourceBundle rb = ResourceBundle.getBundle("properties");
	private PdfParser parser = new PdfParser();
	public String pdfToText(String fileName) {
		return parser.pdfToText(fileName);
	}
	private String filename;
	private String url;
	private String path;

	private parserWrapper(){

	}
	public parserWrapper(String targetFilename, String targetUrl, String targetPath){
		this.filename = targetFilename;
		this.url = targetUrl;
		this.path = targetPath;
	}

	public boolean changeFileName() {
		String getDate = getCreateDate();
		String fileDate = getyyyyDD();

		// 一時保存ファイルの取得
		ZonedDateTime now = ZonedDateTime.now();
		String nowString = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File fOld = new File(this.path + "¥¥" + nowString
				+ this.filename);

		Directory directory = new Directory(this.path);
		directory.existDirectory(fileDate);

		// 新規ファイル名の作成
		File fNew = new File(this.path + "¥¥" +  fileDate + "¥¥"
				+ getDate + this.filename);
		if (fOld.exists()) {
			// ファイル名変更実行
			fOld.renameTo(fNew);
			return true;
		} else {
			logger.error("ファイルが存在しません。");
		}
		return false;
	}

	public boolean deleteFile() {

		// 一時保存ファイルの取得
		ZonedDateTime now = ZonedDateTime.now();
		String nowString = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File f = new File(this.path + "¥¥" + nowString
				+ this.filename);

		if (f.exists()) {
			// 削除実行
			f.delete();
			return true;
		}
		return false;
	}

	private String getCreateDate() {
		String pdfDate = tmppdfToText();

		String[] subString = StringUtils.split(pdfDate, "\r\n");

		String regex = ".+¥¥d+年¥¥d+月¥¥d+日";
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(subString[2]);
		if (m.find()) {
			String regStr = m.group();

			DateTimeFormatter f = DateTimeFormatter.ofPattern("Gy年M月d日")
					.withChronology(JapaneseChronology.INSTANCE);

			JapaneseDate d2 = JapaneseDate.from(f.parse(regStr));
			return LocalDate.from(d2).toString();// 2015-05-16
		}
		return null;
	}

	private String getyyyyDD() {
		String pdfDate = tmppdfToText();

		String[] subString = StringUtils.split(pdfDate, "\r\n");

		String regex = ".+¥¥d+年¥¥d+月¥¥d+日";
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(subString[2]);
		if (m.find()) {
			String regStr = m.group();

			DateTimeFormatter f = DateTimeFormatter.ofPattern("Gy年M月d日")
					.withChronology(JapaneseChronology.INSTANCE);

			JapaneseDate jpnDate = JapaneseDate.from(f.parse(regStr));

			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy_MM");
			return LocalDate.from(jpnDate).format(formater);
		}
		return null;
	}

	private String tmppdfToText() {
		ZonedDateTime now = ZonedDateTime.now();
		String nowString = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		String fileName = this.path + "¥¥" + nowString
				+ this.filename;
		return parser.pdfToText(fileName);
	}

}
