package util;

/**
 * stdoutログを管理する。
 */
public class StdoutLog {
	/**
	 * ログ出力する（改行なし）。
	 * 
	 * @param clazz  実行クラス。
	 * @param method メソッド名。
	 * @param msg    メッセージ文字列。
	 */
	public static void print(Class<?> clazz, String method, String msg) {
		String name = clazz.getName();
		int idx = name.lastIndexOf(".");
		if (idx >= 0) {
			name = name.substring(idx + 1);
		}
		System.out.print(name + "." + method + ": " + msg);
	}

	/**
	 * ログ出力する（改行あり）。
	 * 
	 * @param clazz  実行クラス。
	 * @param method メソッド名。
	 * @param msg    メッセージ文字列。
	 */
	public static void println(Class<?> clazz, String method, String msg) {
		String name = clazz.getName();
		int idx = name.lastIndexOf(".");
		if (idx >= 0) {
			name = name.substring(idx + 1);
		}
		System.out.println(name + "." + method + ": " + msg);
	}

	/**
	 * タイムスタンプをつけて、ログ出力する（改行あり）。
	 * 
	 * @param clazz  実行クラス。
	 * @param method メソッド名。
	 * @param msg    メッセージ文字列。
	 */
	public static void timeprintln(Class<?> clazz, String method, String msg) {
		String now = DateTimeUtil.nowToString();
		String name = clazz.getName();
		int idx = name.lastIndexOf(".");
		if (idx >= 0) {
			name = name.substring(idx + 1);
		}
		System.out.println(now + " " + name + "." + method + ": " + msg);
	}

}
