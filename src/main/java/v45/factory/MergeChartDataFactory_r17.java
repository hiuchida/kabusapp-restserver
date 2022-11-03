package v45.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import util.AppCommon;
import v38.factory.BarNameFactory_r10;
import v45.i.MergeChartData_r17;

/**
 * チャートデータをマージするクラスのファクトリー。
 */
public class MergeChartDataFactory_r17 extends AppCommon {
	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値を出力するクラス名のマップ。
	 */
	private static Map<String, String> classNameMap = new TreeMap<>();

	/**
	 * マップの初期化。
	 */
	static {
		try {
			InputStream is = MergeChartDataFactory_r17.class.getResourceAsStream("MergeChartDataFactory_r17.properties");
			Properties prop = new Properties();
			prop.load(is);
			for (String key : prop.stringPropertyNames()) {
				String val = prop.getProperty(key);
				classNameMap.put(key, val);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値を出力するクラスのコンストラクタ。
	 */
	private static Map<String, Constructor<MergeChartData_r17>> consMap = new TreeMap<>();

	/**
	 * クラスの初期化。
	 */
	static {
		String[] barNames = BarNameFactory_r10.getBarNames();
		for (int i = 0; i < barNames.length; i++) {
			String key = barNames[i];
			String name = classNameMap.get(key);
			try {
				@SuppressWarnings("unchecked")
				Constructor<MergeChartData_r17> cons = (Constructor<MergeChartData_r17>) Class.forName(name).getDeclaredConstructor(String.class, String.class);
				consMap.put(key, cons);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * すべてのインスタンスを生成する。
	 * 
	 * @param name ディレクトリ名。
	 * @return インスタンスのマップ。
	 */
	public static Map<String, MergeChartData_r17> create(String name) {
		Map<String, MergeChartData_r17> mergeMap = new TreeMap<>();
		for (String key : consMap.keySet()) {
			Constructor<MergeChartData_r17> cons = consMap.get(key);
			try {
				MergeChartData_r17 obj = cons.newInstance(name, key);
				// 後からBarCodeを追加したため、properties設定と矛盾がないことをチェックする
				if (!key.equals(obj.getCode().toString())) {
					throw new RuntimeException("Illegal BarCode. key=" + key + ", obj=" + obj.getCode().toString());
				}
				mergeMap.put(key, obj);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return mergeMap;
	}

}
