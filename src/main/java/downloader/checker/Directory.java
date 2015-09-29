package downloader.checker;

import java.io.File;

public class Directory {

	public void existDirectory() {
		// Fileオブジェクトを生成する
		File f = new File("seikapdf");

		if (!f.exists()) {
			// フォルダ作成実行
			f.mkdirs();
			System.out.println("フォルダを作成しました。");
		} else {
			System.out.println("フォルダは既に存在します。");
		}
	}
}
