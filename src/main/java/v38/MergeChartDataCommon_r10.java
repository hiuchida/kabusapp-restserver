package v38;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import logic.CalendarLogic;
import logic.FileLockLogic;
import util.DateTimeUtil;
import util.DateUtil;
import util.FileUtil;
import util.StdoutLog;
import util.StringUtil;
import v38.bean.MergeChartInfo_r10;
import v38.factory.BarCode;
import v38.i.MergeChartData_r10;

/**
 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力する抽象クラス。
 */
public abstract class MergeChartDataCommon_r10 implements MergeChartData_r10 {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * 基準パス。
	 */
	private static final String SERVER_DIRPATH = "/tmp/server/";
	/**
	 * チャートＤＢディレクトリパス。
	 */
	private static final String SERVER_DIR_DBPATH = SERVER_DIRPATH + "db/";
	/**
	 * 4本値チャートＤＢファイル名。
	 */
	private static final String DB_FILENAME = "ChartData%s_r10.db";
	/**
	 * チャートデータディレクトリパス。
	 */
	private static final String SERVER_DIR_CHARTPATH = SERVER_DIRPATH + "chart/";
	/**
	 * PUSH APIで受信したチャートデータファイル名。
	 */
	private static final String CHART_CSV_FILENAME = "ChartData.csv";
	/**
	 * マージしたチャートデータファイル名。
	 */
	private static final String CHART_TXT_FILENAME = "ChartData%s_r10.txt";
	/**
	 * ファイルロック管理用0バイトのファイル名。存在しなければ生成される。
	 */
	private static final String LOCK_FILENAME = "ChartData.lock";

	/**
	 * ディレクトリ名。
	 */
	protected String name;
	/**
	 * 足名。
	 */
	protected String bar;
	/**
	 * チャートＤＢファイルパス。
	 */
	protected String dbFilePath;
	/**
	 * マージしたチャートデータを時系列に並べたマップ。
	 */
	protected Map<String, MergeChartInfo_r10> chartMap = new TreeMap<>();
	/**
	 * チャートデータファイルパス。
	 */
	protected String csvFilePath;
	/**
	 * マージしたチャートデータファイルパス。
	 */
	protected String txtFilePath;
	/**
	 * チャートデータロックを管理する。
	 */
	private FileLockLogic fileLockLogic;

	/**
	 * 足名の種別コードを取得する。
	 * 
	 * @return 足名の種別コード。
	 */
	abstract public BarCode getCode();

	/**
	 * 指定した時刻が前日扱いかどうか判定する。
	 * 
	 * @param time 現値の時刻。
	 * @return true:前日扱い、false:当日扱いまたは無効。
	 */
	protected boolean isYesterday(String time) {
		return false;
	}

	/**
	 * 指定した時刻が所属する時刻を検索する。
	 * 
	 * @param time 現値の時刻。
	 * @return 所属する時刻。範囲外の場合はnull。
	 */
	abstract protected String search(String time);

	/**
	 * 日中（08:45-23:59）の時刻の一覧を取得する。
	 * 
	 * @return 時刻文字列のリスト。
	 */
	abstract protected List<String> daylightTimes();

	/**
	 * 深夜(00:00-06:00)の時刻の一覧を取得する。
	 * 
	 * @return 時刻文字列のリスト。
	 */
	abstract protected List<String> midnightTimes();

	/**
	 * 時系列に並べたマップにデータなしを埋める。
	 */
	private void fillChartData() {
		List<String> keys = new ArrayList<>(chartMap.keySet());
		String first = keys.get(0);
		String firstDate = first.substring(0, 10);
		String firstTime = first.substring(11);
		String last = keys.get(keys.size() - 1);
		String lastDate = last.substring(0, 10);
		String lastTime = last.substring(11);
		String now = DateTimeUtil.nowToString().substring(0, 19);
		String nowDate = now.substring(0, 10);
		String nowTime = now.substring(11);
		int addCnt = 0;
		List<String> dayTimes = daylightTimes();
		List<String> nightTimes = midnightTimes();
		int firstIdx2 = Collections.binarySearch(nightTimes, firstTime);
		int lastIdx2 = Collections.binarySearch(nightTimes, lastTime);
		if (firstIdx2 >= 0) {
			firstDate = prevDay(firstDate);
		}
		if (lastIdx2 >= 0) {
			lastDate = prevDay(lastDate);
		}
		if (isYesterday(nowTime)) {
			nowDate = prevDay(nowDate);
		}
		String startNowTime = search(nowTime);
		if (startNowTime != null) {
			nowTime = startNowTime;
		}
		now = nowDate + " " + nowTime;
		StdoutLog.println(clazz, "fillChartData(" + name + "_" + bar + ")", "Now=" + now);
		String date = firstDate;
		while (date.compareTo(lastDate) <= 0) {
			{ // 日中のループ
				for (int i = 0; i <= dayTimes.size() - 1; i++) {
					String time = dayTimes.get(i);
					String key = date + " " + time;
					if (key.compareTo(first) < 0) {
						continue;
					}
//					if (last.compareTo(key) < 0) {
//						continue;
//					}
					if (now.compareTo(key) <= 0) {
						if (chartMap.containsKey(key)) {
							chartMap.remove(key);
						}
						continue;
					}
					MergeChartInfo_r10 mci = chartMap.get(key);
					if (mci == null) {
						mci = new MergeChartInfo_r10(key);
						chartMap.put(key, mci);
						addCnt++;
//						System.out.println(addCnt + ": " + key);
					}
				}
			}
			{ // 夜間のループ
				String date2 = nextDay(date);
				for (int i = 0; i <= nightTimes.size() - 1; i++) {
					String time = nightTimes.get(i);
					String key = date2 + " " + time;
					if (key.compareTo(first) < 0) {
						continue;
					}
//					if (last.compareTo(key) < 0) {
//						continue;
//					}
					if (now.compareTo(key) <= 0) {
						if (chartMap.containsKey(key)) {
							chartMap.remove(key);
						}
						continue;
					}
					MergeChartInfo_r10 mci = chartMap.get(key);
					if (mci == null) {
						mci = new MergeChartInfo_r10(key);
						chartMap.put(key, mci);
						addCnt++;
//						System.out.println(addCnt + ": " + key);
					}
				}
			}
			date = nextWorkday(date);
			if (date == null) { // カレンダーに登録されていない場合は、ループを打ち切る
				break;
			}
		}
		copyFromPrev();
		int delCnt = removeOld();
		StdoutLog.println(clazz, "fillChartData(" + name + "_" + bar + ")", "addCnt=" + addCnt + ", delCnt=" + delCnt);
	}

	/**
	 * データなしへ前のデータをコピーする。
	 */
	private void copyFromPrev() {
		List<String> keys = new ArrayList<>(chartMap.keySet());
		for (int i = 1; i < chartMap.size(); i++) {
			MergeChartInfo_r10 mci = chartMap.get(keys.get(i));
			if (mci.flag == 0) {
				MergeChartInfo_r10 prev = chartMap.get(keys.get(i - 1));
				mci = new MergeChartInfo_r10(mci.date, prev.closePrice, 0, 3);
				chartMap.put(mci.date, mci);
			}
		}
	}

	/**
	 * 古いデータを削除する。
	 * 
	 * @return 削除した件数。
	 */
	private int removeOld() {
		int remainCnt = 24 * 60;
		int delCnt = 0;
		List<String> keys = new ArrayList<>(chartMap.keySet());
		if (keys.size() <= remainCnt) {
			return delCnt;
		}
		for (int i = 0; i < keys.size() - remainCnt; i++) {
			chartMap.remove(keys.get(i));
			delCnt++;
		}
		return delCnt;
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public MergeChartDataCommon_r10(String name, String bar) {
		this.name = name;
		this.bar = bar;
		String code = StringUtil.parseString(name, "_");
		String dirDbPath = SERVER_DIR_DBPATH + code;
		this.dbFilePath = dirDbPath + "/" + String.format(DB_FILENAME, bar);
		String dirChartPath = SERVER_DIR_CHARTPATH + name;
		this.csvFilePath = dirChartPath + "/" + CHART_CSV_FILENAME;
		this.txtFilePath = dirChartPath + "/" + String.format(CHART_TXT_FILENAME, bar);
		this.fileLockLogic = new FileLockLogic(dirChartPath + "/" + LOCK_FILENAME);
	}

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージする。
	 */
	public void execute() {
		readDbChartData();
		List<String> lines = readCsvChartData();
		mergeCsvChartData(lines);
		writeChartMap();
	}

	/**
	 * チャートＤＢファイルから4本値を読み込む。
	 */
	public void readDbChartData() {
		List<String> lines = FileUtil.readAllLines(dbFilePath);
		int readCnt = 0;
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitComma(s);
			if (cols.length != MergeChartInfo_r10.MAX_DB_COLS) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			MergeChartInfo_r10 mci = new MergeChartInfo_r10(cols);
			String key = mci.getKey();
			chartMap.put(key, mci);
			readCnt++;
		}
		StdoutLog.println(clazz, "readDbChartData(" + name + "_" + bar + ")", "readCnt=" + readCnt);
	}

	/**
	 * PUSH APIで受信したチャートデータファイルから現値を読み込む。
	 * 
	 * @return チャートデータ。
	 */
	public List<String> readCsvChartData() {
		fileLockLogic.lockFile();
		try {
			List<String> lines;
			lines = FileUtil.readAllLines(csvFilePath);
			String prev = null;
			for (int i = 0; i < lines.size(); i++) {
				String s = lines.get(i);
				String[] cols = StringUtil.splitComma(s);
				String cur = cols[0];
				if (prev != null && prev.compareTo(cur) > 0) { // 前のレコードと日時が反転している
					String date = cur.substring(0, 10);
					String time = cur.substring(11);
					String prevTime = prev.substring(11);
					if (time.startsWith("00:") && prevTime.startsWith("23:")) { // 2022-08-09 23:59:58から2022-08-09 00:00:00に戻る場合がある
						date = DateUtil.nextDay(date.replaceAll("-", "/"));
						date = date.replaceAll("/", "-") + " " + time;
						String n = date + s.substring(19);
						lines.set(i, n);
						System.out.println("Warning: REPLACE line=" + s + ", new=" + date + ", prev=" + prev);
					}
					continue;
				}
				prev = cur;
			}
			StdoutLog.println(clazz, "readCsvChartData(" + name + "_" + bar + ")", "readCnt=" + lines.size());
			return lines;
		} finally {
			fileLockLogic.unlockFile();
		}
	}

	/**
	 * 4本値チャートデータの終値と、PUSH APIで受信したチャートデータをマージする。
	 * 
	 * @param lines チャートデータ。
	 */
	public void mergeCsvChartData(List<String> lines) {
		int updateCnt = 0;
		int prevVolume = -1;
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitComma(s);
			if (cols.length < 2) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			String datetime = cols[0];
			int price = (int) StringUtil.parseDouble(cols[1]);
			int volume = 0;
			if (cols.length > 2) { // 指数には売買高は存在しない
				volume = (int) StringUtil.parseDouble(cols[2]);
			}
			if (prevVolume < 0) { // CSVの先頭行の売買高は不明のため0とする
				prevVolume = volume;
				volume = 0;
			} else {
				int delta = volume - prevVolume;
				if (delta < 0) { // 差が減っている場合は、16:30をまたいで翌日扱い
					delta = volume;
				}
				prevVolume = volume;
				volume = delta;
			}
			String date = datetime.substring(0, 10).replaceAll("-", "/");
			String time = datetime.substring(11);
			if (isYesterday(time)) {
				date = DateUtil.prevDay(date);
			}
			String startTime = search(time);
			if (startTime == null) {
				continue;
			}
			String key = date + " " + startTime;
			MergeChartInfo_r10 mci = chartMap.get(key);
			if (mci == null) {
				mci = new MergeChartInfo_r10(key, price, volume);
				chartMap.put(key, mci);
				updateCnt++;
			} else {
				// 4本値は確定値なので上書きしない
				if (mci.flag != 1) {
					// 高値が更新された場合のみ上書きする
					if (mci.highPrice < price) {
						mci.highPrice = price;
					}
					// 安値が更新された場合のみ上書きする
					if (mci.lowPrice > price) {
						mci.lowPrice = price;
					}
					// PUSH APIのデータは、時系列にソートされている前提で、常に新しい現値で上書きする
					mci.closePrice = price;
					// 売買高は加算する
					mci.tradeVolume += volume;
					updateCnt++;
				}
			}
		}
		StdoutLog.println(clazz, "mergeCsvChartData(" + name + "_" + bar + ")", "updateCnt=" + updateCnt);
		if (chartMap.size() > 0) {
			fillChartData();
		}
	}

	/**
	 * マージしたチャートデータファイルを書き込む。
	 */
	public void writeChartMap() {
		List<String> lines = new ArrayList<>();
		lines.add(MergeChartInfo_r10.toHeaderString());
		StdoutLog.println(clazz, "writeChartMap(" + name + "_" + bar + ")", "chartMap.size=" + chartMap.size());
		for (String key : chartMap.keySet()) {
			MergeChartInfo_r10 mci = chartMap.get(key);
			lines.add(mci.toLineString());
		}
		FileUtil.writeAllLines(txtFilePath, lines);
	}

	/**
	 * 対象のチャートデータが先物/OPかどうか判定する。
	 * 
	 * @return true:先物/OP、false:現物/指数。
	 */
	public boolean isFuture() {
		return name.indexOf("_") >= 0;
	}

	/**
	 * 翌営業日を取得する。
	 * 
	 * @param date 基準となる日付文字列(yyyy/MM/dd)。
	 * @return 翌営業日の日付文字列(yyyy/MM/dd)。
	 */
	public String nextWorkday(String date) {
		return CalendarLogic.nextWorkday(date);
	}

	/**
	 * 前日の日付を検索する。
	 * 
	 * @param date 今日の日付。
	 * @return 前日の日付。
	 */
	public String prevDay(String date) {
		return DateUtil.prevDay(date);
	}

	/**
	 * 翌日の日付を検索する。
	 * 
	 * @param date 今日の日付。
	 * @return 翌日の日付。
	 */
	public String nextDay(String date) {
		return DateUtil.nextDay(date);
	}

	/**
	 * マージしたチャートデータを取得する。
	 * 
	 * @return マージしたチャートデータのマップ。
	 */
	public Map<String, MergeChartInfo_r10> getChartMap() {
		return chartMap;
	}

	@Override
	public String toString() {
		return "MergeChartDataCommon_r10 [name=" + name + ", bar=" + bar + ", dbFilePath=" + dbFilePath + ", chartMap.size="
				+ chartMap.size() + ", csvFilePath=" + csvFilePath + ", txtFilePath=" + txtFilePath + ", fileLockLogic="
				+ fileLockLogic + "]";
	}

}
