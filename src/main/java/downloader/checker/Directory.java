package downloader.checker;

import java.io.File;

public class Directory {

	public void existDirectory() {
		// File�I�u�W�F�N�g�𐶐�����
		File f = new File("seikapdf");

		if (!f.exists()) {
			// �t�H���_�쐬���s
			f.mkdirs();
			System.out.println("�t�H���_���쐬���܂����B");
		} else {
			System.out.println("�t�H���_�͊��ɑ��݂��܂��B");
		}
	}
}
