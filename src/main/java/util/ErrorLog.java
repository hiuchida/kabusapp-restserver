package util;

public class ErrorLog {
	/**
	 * 実行アプリクラス。
	 */
	private static Class<?> clazz;

	/**
	 * モジュールバージョン情報。
	 */
	private static String version;

	/**
	 * 初期化する。
	 * 
	 * @param clazz   実行アプリクラス。
	 * @param version モジュールバージョン情報。
	 */
	public static void init(Class<?> clazz, String version) {
		ErrorLog.clazz = clazz;
		ErrorLog.version = version;
		ErrorLog.printVersion();
	}

	/**
	 * モジュールバージョン情報を表示する。
	 */
	public static void printVersion() {
		String now = DateTimeUtil.nowToString();
		System.out.println("--- " + now + " " + clazz.getName() + " " + version + " ---");
	}

	private ErrorLog() {
	}

}
