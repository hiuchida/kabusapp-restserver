package bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MergeChartInfoTest_r10 {

	@Test
	public void mergeChartInfo1Test() {
		String date = "2022/10/21 08:45:00";
		MergeChartInfo_r10 a1 = new MergeChartInfo_r10(date);
		assertEquals(date, a1.date);
		assertEquals(0, a1.openPrice);
		assertEquals(0, a1.highPrice);
		assertEquals(0, a1.lowPrice);
		assertEquals(0, a1.closePrice);
		assertEquals(0, a1.tradeVolume);
		assertEquals(0, a1.flag);
	}

	@Test
	public void mergeChartInfo2Test1() {
		String date = "2022/10/21 08:45:00";
		String[] cols = { date, "26945", "26960", "26930", "26935", "134" };
		MergeChartInfo_r10 a1 = new MergeChartInfo_r10(cols);
		assertEquals(date, a1.date);
		assertEquals(26945, a1.openPrice);
		assertEquals(26960, a1.highPrice);
		assertEquals(26930, a1.lowPrice);
		assertEquals(26935, a1.closePrice);
		assertEquals(134, a1.tradeVolume);
		assertEquals(1, a1.flag);
	}

	@Test
	public void mergeChartInfo2Test2() {
		String date = "2022/10/21 08:45:00";
		String[] cols = { date, "26945", "26960", "26930", "26935", "134", "2" };
		MergeChartInfo_r10 a1 = new MergeChartInfo_r10(cols);
		assertEquals(date, a1.date);
		assertEquals(26945, a1.openPrice);
		assertEquals(26960, a1.highPrice);
		assertEquals(26930, a1.lowPrice);
		assertEquals(26935, a1.closePrice);
		assertEquals(134, a1.tradeVolume);
		assertEquals(2, a1.flag);
	}

	@Test
	public void mergeChartInfo3Test() {
		String date = "2022/10/21 08:45:00";
		int price = 26945;
		int volume = 134;
		MergeChartInfo_r10 a1 = new MergeChartInfo_r10(date, price, volume);
		assertEquals(date, a1.date);
		assertEquals(price, a1.openPrice);
		assertEquals(price, a1.highPrice);
		assertEquals(price, a1.lowPrice);
		assertEquals(price, a1.closePrice);
		assertEquals(volume, a1.tradeVolume);
		assertEquals(2, a1.flag);
	}

	@Test
	public void mergeChartInfo4Test() {
		String date = "2022/10/21 08:45:00";
		int price = 26945;
		int volume = 134;
		int flag = 3;
		MergeChartInfo_r10 a1 = new MergeChartInfo_r10(date, price, volume, flag);
		assertEquals(date, a1.date);
		assertEquals(price, a1.openPrice);
		assertEquals(price, a1.highPrice);
		assertEquals(price, a1.lowPrice);
		assertEquals(price, a1.closePrice);
		assertEquals(volume, a1.tradeVolume);
		assertEquals(flag, a1.flag);
	}

	@Test
	public void mergeChartInfo5Test() {
		String date = "2022/10/21 08:45:00";
		String[] cols = { date, "26945", "26960", "26930", "26935", "134", "2" };
		MergeChartInfo_r10 m1 = new MergeChartInfo_r10(cols);
		MergeChartInfo_r10 a1 = new MergeChartInfo_r10(m1);
		assertEquals(date, a1.date);
		assertEquals(26945, a1.openPrice);
		assertEquals(26960, a1.highPrice);
		assertEquals(26930, a1.lowPrice);
		assertEquals(26935, a1.closePrice);
		assertEquals(134, a1.tradeVolume);
		assertEquals(2, a1.flag);
	}

}
