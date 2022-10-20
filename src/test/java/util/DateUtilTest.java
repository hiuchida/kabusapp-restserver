package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

	@Test
	public void prevDayTest() {
		String[] sa = { "2022/01/02", "2022/02/01", "2022/03/01", "2020/03/01", "2000/03/01", "2023/01/01", };
		String[] ea = { "2022/01/01", "2022/01/31", "2022/02/28", "2020/02/29", "2000/02/29", "2022/12/31", };
		for (int i = 0; i < sa.length; i++) {
			String s1 = sa[i];
			String e1 = ea[i];
			String a1 = DateUtil.prevDay(s1);
			assertEquals(e1, a1);
		}
	}

	@Test
	public void nextDayTest() {
		String[] sa = { "2022/01/01", "2022/01/31", "2022/02/28", "2020/02/28", "2000/02/28", "2022/12/31", };
		String[] ea = { "2022/01/02", "2022/02/01", "2022/03/01", "2020/02/29", "2000/02/29", "2023/01/01", };
		for (int i = 0; i < sa.length; i++) {
			String s1 = sa[i];
			String e1 = ea[i];
			String a1 = DateUtil.nextDay(s1);
			assertEquals(e1, a1);
		}
	}

}
