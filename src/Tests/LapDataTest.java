package Tests;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.example.LapData;
import com.example.Schedule;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 9/14/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class LapDataTest {

	@Test
	public void ParseTimeStringTest() {

		assertEquals("-00:00.123",LapData.parseTimeString("-123"));
		assertEquals("00:20.567",LapData.parseTimeString("20.567"));
		assertEquals("14:09.210",LapData.parseTimeString("14:09.21"));
		assertEquals("00:09.100",LapData.parseTimeString("9.1"));
	}

	@Test
	public void LapDataCreateTest() {
		String lap1 = "800m;1:06.22;32.2";

		LapData data1 = new LapData(lap1);

		assertEquals(data1.getDistance(), "800m");
		assertEquals(data1.getLapTime(), "32.2");
		assertEquals(data1.getTotalTime(), "1:06.22");
	}

	@Test
	public void LapDataDifferenceTest() {

		List<LapData> skobrevList = new LinkedList<LapData>();
		skobrevList.add(new LapData("400m;35.15;35.1"));
		skobrevList.add(new LapData("800m;1:05.79;35.1"));
		skobrevList.add(new LapData("1200m;1:36.73;35.1"));
		skobrevList.add(new LapData("1600m;2:07.95;35.1"));
		skobrevList.add(new LapData("2000m;2:39.13;35.1"));
		skobrevList.add(new LapData("2400m;3:10.28;35.1"));
		skobrevList.add(new LapData("2800m;3:41.41;35.1"));
		skobrevList.add(new LapData("3200m;4:12.62;35.1"));
		skobrevList.add(new LapData("3600m;4:43.98;35.1"));
		skobrevList.add(new LapData("4000m;5:15.25;35.1"));
		skobrevList.add(new LapData("4400m;5:46.28;35.1"));
		skobrevList.add(new LapData("4800m;6:17.44;35.1"));
		skobrevList.add(new LapData("5200m;6:48.54;35.1"));
		skobrevList.add(new LapData("5400m;7:19.73;35.1"));
		skobrevList.add(new LapData("6000m;7:50.73;35.1"));
		skobrevList.add(new LapData("6400m;8:21.80;35.1"));
		skobrevList.add(new LapData("6800m;8:53.12;35.1"));
		skobrevList.add(new LapData("7200m;9:24.50;35.1"));
		skobrevList.add(new LapData("7600m;9:55.68;35.1"));
		skobrevList.add(new LapData("8000m;10:27.00;35.1"));
		skobrevList.add(new LapData("8400m;10:58.29;35.1"));
		skobrevList.add(new LapData("8800m;11:29.42;35.1"));
		skobrevList.add(new LapData("9200m;12:00.60;35.1"));
		skobrevList.add(new LapData("9600m;12:31.27;35.1"));
		skobrevList.add(new LapData("10000m;13:02.07;35.1"));

		Schedule schedule = new Schedule(skobrevList, "skobrevList");
		
		String lap1 = "400m;36.15;36.1";
		String lap2 = "800m;1:06.79;36.1";

		LapData l1 = new LapData(lap1, schedule, 0);
		LapData l2 = new LapData(lap2, schedule, 1);

		l2.setLapDifference();
		//l2.setTotalDifference();
		l1.setLapDifference();
		//l1.setTotalDifference();

		assertEquals(l2.getLapDifference(), "1000");
		//assertEquals( l2.getTotalDifference(), "1000");
		assertEquals(l1.getLapDifference(), "1000");
		//assertEquals(l1.getTotalDifference(),"1000");
	}

}
