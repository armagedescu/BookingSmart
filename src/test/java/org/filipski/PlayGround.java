package org.filipski;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SuppressWarnings("NewClassNamingConvention")
public class PlayGround {
    @Test
    void dateTimeTest()
    {
        LocalDateTime dt = LocalDateTime.now(), ddt;
        Timestamp ts = Timestamp.valueOf(dt);
        ddt = ts.toLocalDateTime();
        assertEquals (dt, ddt);
        System.out.println (ts);
        ts.setNanos(0);
        System.out.println (ts);
        System.out.println  (ts.toLocalDateTime());
        ddt = LocalDateTime.of(LocalDate.of (2024, 4, 14), LocalTime.of (14, 15));
        assertEquals("2024-04-14T14:15", ddt.toString());
        ddt = LocalDateTime.of(2024, 4, 14, 14, 30);
        assertEquals("2024-04-14T14:30", ddt.toString());
        //java.sql.Date sd = new Date();
    }
    @Test
    void dateTimeCompareTest()
    {
        LocalDateTime dt = LocalDateTime.now(), ddt;
        ddt = LocalDateTime.of(dt.toLocalDate(), dt.toLocalTime());
        assertEquals (dt, ddt);
        ddt = dt.withNano(0);
        //assert
        //assertGreater (dt, ddt);

        ddt = LocalDateTime.of(LocalDate.of (2024, 4, 14), LocalTime.of (14, 15));
        assertEquals("2024-04-14T14:15", ddt.toString());
        ddt = LocalDateTime.of(2024, 4, 14, 14, 30);
        assertEquals("2024-04-14T14:30", ddt.toString());
        ddt = LocalDateTime.of(2024, 4, 14, 0, 0);
        assertEquals("2024-04-14T14:30", ddt.toString());
        //java.sql.Date sd = new Date();
    }
    @Test
    void mapTest2() {

        final LocalDateTime lowest = LocalDateTime.of(2024, 4, 14, 0, 0);
        final LocalDateTime highest = lowest.withDayOfMonth(lowest.getDayOfMonth() + 6);
        final LocalDateTime value1 = lowest.withDayOfMonth(lowest.getDayOfMonth() + 2);
        final LocalDateTime value2 = value1.withDayOfMonth(value1.getDayOfMonth() + 2);

        final LocalDateTime aboveValue1 = value1.withDayOfMonth(value1.getDayOfMonth() + 1);
        final LocalDateTime belowValue2 = value2.withDayOfMonth(value2.getDayOfMonth() - 1);
        final LocalDateTime belowLowest = lowest.withDayOfMonth(lowest.getDayOfMonth() - 2);
        final LocalDateTime aboveHighest = highest.withDayOfMonth(highest.getDayOfMonth() + 2);
        // Check expectations
        assertEquals("2024-04-12T00:00", belowLowest.toString());
        assertEquals("2024-04-14T00:00", lowest.toString());
        assertEquals("2024-04-16T00:00", value1.toString());
        assertEquals("2024-04-17T00:00", aboveValue1.toString());
        assertEquals("2024-04-17T00:00", belowValue2.toString());
        assertEquals("2024-04-18T00:00", value2.toString());
        assertEquals("2024-04-20T00:00", highest.toString());
        assertEquals("2024-04-22T00:00", aboveHighest.toString());


        TreeMap<LocalDateTime, LocalDateTime> mip = new TreeMap<>(Map.ofEntries
                (
                        Map.entry(lowest, lowest),
                        Map.entry(value1, value1),
                        Map.entry(value2, value2),
                        Map.entry(highest, highest)
                ));
        for (var k: mip.keySet()) System.out.println(k);

        assertNull(mip.ceilingKey(aboveHighest));
        assertNull(mip.floorKey(belowLowest));
        assertEquals(highest, mip.floorKey(aboveHighest));
        assertEquals(lowest, mip.ceilingKey(belowLowest));

        assertEquals(value1, mip.floorKey(value1));
        assertEquals(value1, mip.ceilingKey(value1));

        assertEquals(value1, mip.floorKey(aboveValue1));
        assertEquals(value2, mip.ceilingKey(belowValue2));


    }
    @Test
    void mapTest()
    {
        //Map<Integer, Integer> mip = new SortedMap<Integer, Integer>();

        TreeMap<Integer, Integer> mip = new TreeMap<>(Map.ofEntries
                (
                        Map.entry(10, 10),
                        Map.entry(20, 20),
                        Map.entry(30, 30),
                        Map.entry(40, 40)
                ));

        System.out.println(mip.ceilingKey(21));
        assertNull(mip.lowerKey(10));
        assertNull(mip.higherKey(40));
        assertNull(mip.floorKey(5));
        assertNull(mip.ceilingKey(45));

        assertEquals(10, mip.ceilingKey(5));
        assertEquals(20, mip.ceilingKey(20));
        assertEquals(30, mip.ceilingKey(21));

        assertEquals(20, mip.floorKey(20));
        assertEquals(20, mip.floorKey(21));
        assertEquals(40, mip.floorKey(100));

        assertEquals(10, mip.lowerKey(20));
        assertEquals(20, mip.higherKey(10));


    }
}
