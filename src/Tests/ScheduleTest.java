package Tests;
import com.example.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 9/21/12
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleTest {

    @Test
    public void ScheduleTest() {
        LapData l1 = new LapData("400m;1:08.32;31.2");
        LapData l2 = new LapData("800m;1:06.22;32.2");
        ArrayList<LapData> list = new ArrayList<LapData>();
        list.add(l1);
        list.add(l2);
        Schedule s1 =  new Schedule(list, "test");

        assertEquals("test", s1.getName());
        assertEquals("1:08.32", s1.getRound(1).getTotalTime());
        assertEquals("32.2", s1.getRound(2).getLapTime());
    }
}
