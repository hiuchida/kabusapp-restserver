package v45.factory;

/**
 * 足名の種別コード。
 */
public enum BarCode {
	M1("1m"), M3("3m"), M5("5m"), M10("10m"), M15("15m"), M20("20m"), M30("30m"), M60("60m");

	private String code;

	private BarCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	public static BarCode valueOfCode(String code) {
		for (BarCode e : values()) {
			if (e.code.equals(code)) {
				return e;
			}
		}
		throw new IllegalArgumentException("No enum constant code=" + code);
	}

}
