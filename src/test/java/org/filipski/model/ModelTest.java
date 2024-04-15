package org.filipski.model;

import org.filipski.Startup;
import org.filipski.schema.Schedule;
import org.filipski.schema.Smartphone;
import org.filipski.schema.SmartphoneRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.query.SelectionQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelTest {

    @BeforeAll
    static void initDatabase() throws Throwable {
        Startup.dataBuilder();
    }
    @SuppressWarnings({"unused"})
    @Test
    void registrySelectorTest() throws Throwable
    {
        List<SmartphoneRegistry> registryList = Model.getModel().getPhoneRegistry();

        assertEquals(9, registryList.size());
        for (SmartphoneRegistry smartphoneRegistry: registryList) {
            System.out.printf("%s, %d, %d, %f\n",
                    smartphoneRegistry.getName(), smartphoneRegistry.getDeviceCount(),
                    smartphoneRegistry.getTotalReviews(), smartphoneRegistry.getAvgRating());
        }

    }


    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    @Test
    void devicesSelectorPlayground() throws Throwable
    {
        SessionFactory sessionFactory = Startup.getSessionFactory();
        List<Smartphone> devices;
        try (Transactioner tr = new Transactioner(sessionFactory))
        {
            SelectionQuery<Smartphone> query = tr.get().createSelectionQuery(
                    "from Smartphone",
                    Smartphone.class);
            devices = query.getResultList();
            assertEquals(10, devices.size());

            for (Smartphone device: devices) {
                SelectionQuery<Schedule> ssch = tr.get().createSelectionQuery(
                        "from Schedule where smartphone.id = :deviceId and finish > :finish order by start",
                        Schedule.class);
                ssch.setParameter("deviceId", device.getId());
                ssch.setParameter("finish", Model.getModel().getReferenceDate());
                List<Schedule> sch = ssch.getResultList();

                device.setSchedulesCount(sch.size());
                System.out.printf ("%s %d\n", device.getName(), sch.size());
                if (sch.isEmpty()) device.setAvailableNow(true);
                if (!sch.isEmpty())
                {
                    Schedule sc = sch.getFirst();
                    device.setAvailableNow(false);
                    if (sc.getReviewer()  == null)
                    {
                        device.setAvailableNow(true);
                    }
                    if (sc.getStart().isAfter(Model.getModel().getReferenceDate()))
                    {
                        device.setAvailableNow(true);
                        ChronoUnit.DAYS.between(Model.getModel().getReferenceDate(), sc.getStart());
                        device.setAvailableDays((int)ChronoUnit.DAYS.between(Model.getModel().getReferenceDate(), sc.getStart()));
                    }
                }
            }

        }

        for (Smartphone dev: devices)
        {
            System.out.printf ("%s, %d\n", dev.getName(), dev.getAvailableDays());
        }

        for (Smartphone device: devices) {
            System.out.printf("%s, %d, %d, %f\n",
                    device.getName(), 1,
                    1, 1.);
        }
        assertEquals(10, devices.size());
    }


}