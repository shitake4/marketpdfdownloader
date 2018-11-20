package downloader.parser;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PdfParser {

  // Extract text from PDF Document
  public String pdfToText(String fileName) {
    File file = new File(fileName);
    String parsedText = null;
    if (!file.isFile()) {
      System.err.println("File " + fileName + " does not exist.");
      return null;
    }
    try {

      PDDocument pdDoc = PDDocument.load(file); // throws
      // IOException
      PDFTextStripper pdfStripper = new PDFTextStripper();// throws
      // IOException

      pdfStripper.setStartPage(1);
      pdfStripper.setEndPage(5);

      parsedText = pdfStripper.getText(pdDoc); // throws
      // IOException
    } catch (IOException e) {
      e.printStackTrace();
    }
    return parsedText;
  }
}
