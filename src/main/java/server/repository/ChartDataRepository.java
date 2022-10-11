package server.repository;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Repository;

import server.model.ChartData;
import util.FileUtil;

@Repository
public class ChartDataRepository {
	/**
	 * 基準パス。
	 */
	private static final String DIRPATH = "/tmp/server/chart/";
	/**
	 * チャートデータファイル名。
	 */
	private static final String DB_FILENAME = "ChartData.csv";

	/**
	 * チャートデータをファイルに追記する。
	 * 
	 * @param cd チャートデータ。
	 * @return 追記したレコード数。
	 * @throws IOException 
	 */
	public int append(ChartData cd) throws IOException {
		String dirpath = DIRPATH + cd.code;
		FileUtil.mkdirs(dirpath);
		String filepath = dirpath + "/" + DB_FILENAME;
		try (PrintWriter pw = FileUtil.writer(filepath, FileUtil.UTF8, true)) {
			int writeCnt = 0;
			for (String s : cd.list) {
				pw.println(s);
				writeCnt++;
			}
			return writeCnt;
		}
	}

}
