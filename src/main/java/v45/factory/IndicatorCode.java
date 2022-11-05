package v45.factory;

/**
 * テクニカル指標の種別コード。
 */
public enum IndicatorCode {
	SMA(1), BollingerBands(2), MACD(3), HV(4), Pivot(5), DMI(6), Parabolic(7),
	Turning(8);

	private int id;

	private IndicatorCode(int id) {
		this.id = id;
	}

	public int intValue() {
		return id;
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}

	public static IndicatorCode valueOf(int id) {
		for (IndicatorCode e : values()) {
			if (e.id == id) {
				return e;
			}
		}
		throw new IllegalArgumentException("No enum constant id=" + id);
	}

}
