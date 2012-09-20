
package Tests;

import com.example.*;
import org.junit.*;

import static org.junit.Assert.*;

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

        assertEquals("-00:00.123", LapData.parseTimeString("-123"));
        assertEquals("00:20.567", LapData.parseTimeString("20.567"));
        assertEquals("14:09.210", LapData.parseTimeString("14:09.21"));
        assertEquals("00:09.100", LapData.parseTimeString("9.1"));
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
        String lap1 = "800m;1:06.22;32.0";
        String lap2 = "800m;1:08.50;31.500";

        LapData l1 = new LapData(lap1);
        LapData l2 = new LapData(lap2);

        l2.setLapDifference(l1);
        l2.setTotalDifference(l1);
        l1.setLapDifference(l2);
        l1.setTotalDifference(l2);

        assertEquals(l2.getLapDifference(), "-500");
        assertEquals("2.280", l2.getTotalDifference());
        assertEquals(l1.getLapDifference(), "500");
        assertEquals(l1.getTotalDifference(), "-2.280");
    }

}
