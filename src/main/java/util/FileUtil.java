package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * ファイルに関するユーティリティクラス。
 */
public class FileUtil {

	/**
	 * エンコーディング：UTF-8。
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * ディレクトリを作成する。
	 * 
	 * @param dirpath ディレクトリパス。
	 */
	public static void mkdirs(String dirpath) {
		File f = new File(dirpath);
		f.mkdirs();
	}

	/**
	 * 出力ファイルを開く。
	 * 
	 * @param filepath ファイルパス。
	 * @param encoding エンコーディング。
	 * @param bAppend  true:追記、false:上書き
	 * @return 出力ファイル。
	 * @throws IOException 例外。
	 */
	public static PrintWriter writer(String filepath, String encoding, boolean bAppend) throws IOException {
		return new PrintWriter(new OutputStreamWriter(new FileOutputStream(filepath, bAppend), encoding));
	}

}
