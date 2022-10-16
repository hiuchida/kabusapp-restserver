package v38.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import util.StringUtil;

/**
 * 足名の一覧のファクトリー。
 */
public class BarNameFactory_r10 {
	/**
	 * 足名の一覧。
	 */
	private static String[] barNames;

	/**
	 * 配列の初期化。
	 */
	static {
		try {
			InputStream is = BarNameFactory_r10.class.getResourceAsStream("BarNameFactory_r10.properties");
			Properties prop = new Properties();
			prop.load(is);
			Map<Integer, String> map = new TreeMap<>();
			for (String key : prop.stringPropertyNames()) {
				String val = prop.getProperty(key);
				map.put(StringUtil.parseInt(key), val);
			}
			barNames = new String[map.size()];
			int i = 0;
			for (Integer key : map.keySet()) {
				String val = map.get(key);
				barNames[i++] = val;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 足名の一覧を取得する。
	 * 
	 * @return 足名の配列。
	 */
	public static String[] getBarNames() {
		return barNames;
	}

}
