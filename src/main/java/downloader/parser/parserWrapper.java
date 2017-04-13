package downloader.parser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import downloader.checker.Directory;

public class parserWrapper {
	private Logger logger = LoggerFactory.getLogger(parserWrapper.class
			.getName());
	private ResourceBundle rb = ResourceBundle.getBundle("properties");
	private PdfParser parser = new PdfParser();
	private String filename;
	private String url;
	private String path;
	private String FS = File.separator; 
	public String pdfToText(String fileName) {
		return parser.pdfToText(fileName);
	}

	private parserWrapper(){

	}
	public parserWrapper(String targetFilename, String targetUrl, String targetPath){
		this.filename = targetFilename;
		this.url = targetUrl;
		this.path = targetPath;
	}

	public boolean changeFileName(){
		String getDate = getCreateDate();
		String fileDate = getyyyyDD();

		// 一時保存ファイルの取得
		ZonedDateTime now = ZonedDateTime.now();
		String nowString = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File fOld = new File(this.path + FS + nowString
				+ this.filename);

		Directory directory = new Directory(this.path);
		directory.existDirectory(fileDate);

		if (fileDate == null) fileDate = nowString.substring(0,6);
		if (getDate == null) getDate = nowString;

		// 新規ファイル名の作成
		File fNew = new File(this.path + FS + fileDate + FS
				+ getDate + this.filename);
		if (fOld.exists()) {
			// ファイル名変更実行
			try {
				FileUtils.moveFile(fOld, fNew);
			} catch (IOException e) {
				logger.error("同じファイルが存在してます",e);
			}
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
		File f = new File(this.path + FS + nowString
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
		pdfDate = StringUtils.deleteWhitespace(pdfDate);

		String[] subString = StringUtils.split(pdfDate, "\r\n");
		DateTimeFormatter f2 = DateTimeFormatter.ofPattern("G")
				.withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);
		JapaneseDate d = JapaneseDate.now();

		String regex =  f2.format(d) + "\\d+年\\d+月\\d+日";
		Pattern p = Pattern.compile(regex);

		List<String> matchedStr = Arrays.stream(subString).map(s -> p.matcher(s)).filter(matcher -> matcher.find()).map(matcher -> matcher.group()).collect(Collectors.toList());

		if (0 < matchedStr.size()){
			DateTimeFormatter f = DateTimeFormatter.ofPattern("Gy年M月d日")
					.withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);

			JapaneseDate d2 = JapaneseDate.from(f.parse(matchedStr.get(0)));
			return LocalDate.from(d2).toString();// 2015-05-16
		}


//		Matcher m = p.matcher(subString[2]);
//		if (m.find()) {
//			String regStr = m.group();
//
//			DateTimeFormatter f = DateTimeFormatter.ofPattern("Gy年M月d日")
//					.withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);
//
//			JapaneseDate d2 = JapaneseDate.from(f.parse(regStr));
//			return LocalDate.from(d2).toString();// 2015-05-16
//		}
		return null;
	}

	private String getyyyyDD() {
		String pdfDate = tmppdfToText();
		pdfDate = StringUtils.deleteWhitespace(pdfDate);

		String[] subString = StringUtils.split(pdfDate, "\r\n");
		DateTimeFormatter f2 = DateTimeFormatter.ofPattern("G")
				.withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);
		JapaneseDate d = JapaneseDate.now();

		String regex =  f2.format(d) + "\\d+年\\d+月\\d+日";
		Pattern p = Pattern.compile(regex);

		List<String> matchedStr = Arrays.stream(subString).map(s -> p.matcher(s)).filter(matcher -> matcher.find()).map(matcher -> matcher.group()).collect(Collectors.toList());

		if (0 < matchedStr.size()){
			DateTimeFormatter f = DateTimeFormatter.ofPattern("Gy年M月d日")
					.withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);

			JapaneseDate jpnDate = JapaneseDate.from(f.parse(matchedStr.get(0)));

			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy_MM");
			return LocalDate.from(jpnDate).format(formater);
		}

//		Matcher m = p.matcher(subString[2]);
//		if (m.find()) {
//			String regStr = m.group();
//
//			DateTimeFormatter f = DateTimeFormatter.ofPattern("Gy年M月d日")
//					.withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);
//
//			JapaneseDate jpnDate = JapaneseDate.from(f.parse(regStr));
//
//			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy_MM");
//			return LocalDate.from(jpnDate).format(formater);
//		}
		return null;
	}

	private String tmppdfToText() {
		ZonedDateTime now = ZonedDateTime.now();
		String nowString = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		String fileName = this.path + FS + nowString
				+ this.filename;
		return parser.pdfToText(fileName);
	}

}
