package v45.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import util.AppCommon;
import util.StringUtil;
import v45.i.TriggerIndicator_r17;

/**
 * テクニカル指標からイベントトリガーを発火するクラスのファクトリー。
 */
public class TriggerIndicatorFactory_r17 extends AppCommon {
	/**
	 * テクニカル指標からイベントトリガーを発火するクラス名の一覧。
	 */
	private static String[] classNames;

	/**
	 * 配列の初期化。
	 */
	static {
		try {
			InputStream is = TriggerIndicatorFactory_r17.class.getResourceAsStream("TriggerIndicatorFactory_r17.properties");
			Properties prop = new Properties();
			prop.load(is);
			Map<Integer, String> map = new TreeMap<>();
			for (String key : prop.stringPropertyNames()) {
				String val = prop.getProperty(key);
				map.put(StringUtil.parseInt(key), val);
			}
			classNames = new String[map.size()];
			int i = 0;
			for (Integer key : map.keySet()) {
				String val = map.get(key);
				classNames[i++] = val;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * テクニカル指標からイベントトリガーを発火するクラスのコンストラクタ。
	 */
	private static List<Constructor<TriggerIndicator_r17>> consList = new ArrayList<>();

	/**
	 * クラスの初期化。
	 */
	static {
		for (String name : classNames) {
			try {
				@SuppressWarnings("unchecked")
				Constructor<TriggerIndicator_r17> cons = (Constructor<TriggerIndicator_r17>) Class.forName(name).getDeclaredConstructor(String.class, String.class);
				consList.add(cons);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * すべてのインスタンスを生成する。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 * @return インスタンスのリスト。
	 */
	public static List<TriggerIndicator_r17> create(String name, String bar) {
		List<TriggerIndicator_r17> calcList = new ArrayList<>();
		for (Constructor<TriggerIndicator_r17> cons : consList) {
			try {
				TriggerIndicator_r17 obj = cons.newInstance(name, bar);
				calcList.add(obj);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return calcList;
	}

}
