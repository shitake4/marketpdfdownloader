package downloader.parser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
		LocalDate file_date = extractFileDate();
		String yyyy_mm = file_date.format(DateTimeFormatter.ofPattern("yyyy_MM"));

		// 一時保存ファイルの取得
		String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File fOld = new File(this.path + FS + now + this.filename);

		Directory directory = new Directory(this.path);
		directory.mkdir(yyyy_mm);

		if (yyyy_mm == null) yyyy_mm = now.substring(0,6);
		String fileDateStr = file_date == null ? now : file_date.toString();
		// 新規ファイル名の作成
		File fNew = new File(this.path + FS + yyyy_mm + FS + fileDateStr + this.filename);
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
		File f = new File(this.path + FS + nowString + this.filename);

		if (f.exists()) {
			// 削除実行
			f.delete();
			return true;
		}
		return false;
	}

	private LocalDate extractFileDate() {
		String pdfDate = pdfToText();
		pdfDate = StringUtils.deleteWhitespace(pdfDate);

		String[] subString = StringUtils.split(pdfDate, "\r\n");
		String regex = getWarekiFormat();

		Pattern p = Pattern.compile(regex);

		List<String> matchedStr = Arrays.stream(subString).map(s -> p.matcher(s)).filter(matcher -> matcher.find()).map(matcher -> matcher.group()).collect(Collectors.toList());
		if (0 < matchedStr.size()){
			DateTimeFormatter f = DateTimeFormatter.ofPattern("Gy年M月d日")
					.withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);

			JapaneseDate d2 = JapaneseDate.from(f.parse(matchedStr.get(0)));
			return LocalDate.from(d2);// 2015-05-16
		}
		return null;
	}

	private String getWarekiFormat() {
		DateTimeFormatter f2 = DateTimeFormatter.ofPattern("G").withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);
		JapaneseDate d = JapaneseDate.now();
		return f2.format(d) + "\\d+年\\d+月\\d+日";
	}

	private String pdfToText() {
		String nowStr = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String filePath = this.path + FS + nowStr + this.filename;
		return parser.pdfToText(filePath);
	}

}
