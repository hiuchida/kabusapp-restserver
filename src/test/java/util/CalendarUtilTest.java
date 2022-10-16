package util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalendarUtilTest {

	@Test
	public void prevDayTest() {
		List<String> calendarList = calendarList();
		String[] sa = { "2021/12/30", "2022/01/04", "2022/01/05", };
		String[] ea = { null, "2021/12/30", "2022/01/04", };
		for (int i = 0; i < sa.length; i++) {
			String s1 = sa[i];
			String e1 = ea[i];
			String a1 = CalendarUtil.prevWorkday(calendarList, s1);
			assertEquals(e1, a1);
		}
	}

	@Test
	public void nextDayTest() {
		List<String> calendarList = calendarList();
		String[] sa = { "2021/12/30", "2022/01/04", "2022/01/05", };
		String[] ea = { "2022/01/04", "2022/01/05", null };
		for (int i = 0; i < sa.length; i++) {
			String s1 = sa[i];
			String e1 = ea[i];
			String a1 = CalendarUtil.nextWorkday(calendarList, s1);
			assertEquals(e1, a1);
		}
	}

	private List<String> calendarList() {
		List<String> list = new ArrayList<>();
		list.add("2021/12/30");
		list.add("2022/01/04");
		list.add("2022/01/05");
		return list;
	}

}
