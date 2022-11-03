package util;

/**
 * アプリの抽象クラス。
 */
public abstract class AppCommon {

	/**
	 * PUSH API(WebSocket)のURI。
	 */
	public static final String WEBSOCKET_URI = Consts.WEBSOCKET_URI;

	/**
	 * ホームパス。
	 */
	public static final String HOME_PATH = Consts.HOME_PATH;

	/**
	 * チャートディレクトリパス。
	 */
	public static final String CHART_DIR_PATH = HOME_PATH + "chart/";

	/**
	 * チャートＤＢディレクトリパス。
	 */
	public static final String DB_DIR_PATH = HOME_PATH + "db/";

	/**
	 * デバッグログディレクトリパス。
	 */
	public static final String DEBUG_DIR_PATH = HOME_PATH + "debug/";

	/**
	 * チャートデータディレクトリパス。
	 */
	public static final String DOWNLOAD_DIR_PATH = HOME_PATH + "download/";

	/**
	 * サーバーディレクトリパス。
	 */
	public static final String SERVER_DIR_PATH = HOME_PATH + "server/";

	/**
	 * サーバーチャートディレクトリパス。
	 */
	public static final String SERVER_CHART_DIR_PATH = SERVER_DIR_PATH + "chart/";

	/**
	 * サーバーチャートＤＢディレクトリパス。
	 */
	public static final String SERVER_DB_DIR_PATH = SERVER_DIR_PATH + "db/";

	/**
	 * サーバーデバッグログディレクトリパス。
	 */
	public static final String SERVER_DEBUG_DIR_PATH = SERVER_DIR_PATH + "debug/";

	/**
	 * PUSH APIで受信したティックデータファイル名。
	 */
	public static final String CHART_CSV_FILENAME = "ChartData.csv";

	/**
	 * ファイルロック管理用0バイトのファイル名。存在しなければ生成される。
	 */
	public static final String CHART_LOCK_FILENAME = "ChartData.lock";

	/**
	 * 注文パスワード。
	 */
//	public static final String TRADE_PASSWORD = SendOrderConfigUtil.getPassword();

	/**
	 * main()の冒頭の初期化。
	 * 
	 * @param clazz アプリのクラス。
	 */
//	public static void init(Class<?> clazz) {
//		ApiErrorLog.init(clazz, Consts.VERSION);
//		ErrorLog.init(clazz, Consts.VERSION);
//	}

}
