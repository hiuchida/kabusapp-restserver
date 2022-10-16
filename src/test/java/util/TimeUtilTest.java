package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TimeUtilTest {

	@Test
	public void time_valATest() {
		for (int hour = -48; hour <= 48; hour++) {
			for (int min = 0; min < 60; min++) {
				for (int sec = 0; sec < 60; sec++) {
					int e1 = hour * 60 * 60 + min * 60 + sec;
					int a1 = TimeUtil.time_val(hour, min, sec);
					assertEquals(e1, a1);
				}
			}
		}
	}

	@Test
	public void time_valBTest() {
		for (int hour = 0; hour <= 48; hour++) {
			for (int min = 0; min < 60; min++) {
				for (int sec = 0; sec < 60; sec++) {
					String s1 = String.format("%02d:%02d:%02d", hour, min, sec);
					int e1 = hour * 60 * 60 + min * 60 + sec;
					int a1 = TimeUtil.time_val(s1);
					assertEquals(e1, a1);
				}
			}
		}
	}

	@Test
	public void hourValueTest() {
		for (int hour = -48; hour <= 48; hour++) {
			for (int min = 0; min < 60; min++) {
				for (int sec = 0; sec < 60; sec++) {
					int tim = TimeUtil.time_val(hour, min, sec);
					int e1 = (hour + 48) % 24;
					int a1 = TimeUtil.hourValue(tim);
					assertEquals(e1, a1);
				}
			}
		}
	}

	@Test
	public void toStringATest() {
		for (int hour = -48; hour <= 48; hour++) {
			for (int min = 0; min < 60; min++) {
				for (int sec = 0; sec < 60; sec++) {
					int tim = TimeUtil.time_val(hour, min, sec);
					String e1 = String.format("%02d:%02d:%02d", (hour + 48) % 24, min, sec);
					String a1 = TimeUtil.toString(tim);
					assertEquals(e1, a1);
				}
			}
		}
	}

	@Test
	public void toStringB1Test() {
		for (int hour = -48; hour <= 120; hour++) {
			for (int min = 0; min < 60; min++) {
				for (int sec = 0; sec < 60; sec++) {
					int tim = TimeUtil.time_val(hour, min, sec);
					String e1 = String.format("%02d:%02d:%02d", (hour + 48) % 24, min, sec);
					String a1 = TimeUtil.toString(tim, false);
					assertEquals(e1, a1);
				}
			}
		}
	}

	@Test
	public void toStringB2Test() {
		for (int hour = -48; hour <= 120; hour++) {
			for (int min = 0; min < 60; min++) {
				for (int sec = 0; sec < 60; sec++) {
					int tim = TimeUtil.time_val(hour, min, sec);
					int hh = hour;
					while (hh < 0) {
						hh += 24;
					}
					while (hh > 99) {
						hh -= 24;
					}
					String e1 = String.format("%02d:%02d:%02d", hh, min, sec);
					String a1 = TimeUtil.toString(tim, true);
					assertEquals(e1, a1);
				}
			}
		}
	}

}
