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
		logger.info(downloadTime.toString() + "�̃f�[�^�擾���J�n���܂�");

		// pdf�t�@�C���̃_�E�����[�h
		try {
			PdfDownload pdf = new PdfDownload();
			pdf.getFIle();
			logger.info("pdf�̃_�E�����[�h���������܂����B");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("pdfDownloadError :", e);
		}

		// pdf�t�@�C�����̕ύX
		parserWrapper parser = new parserWrapper();
		boolean result = parser.changeFileName();
		if (result) {
			logger.info("pdf�̃t�@�C�����ύX���������܂���");
			boolean delResult = parser.deleteFile();
			if (delResult) {
				logger.info("pdf�t�@�C���̍폜���������܂���");
			}
		}
	}
}
