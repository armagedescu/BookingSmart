package org.filipski.model;

import jakarta.persistence.Tuple;
import org.filipski.Startup;
import org.filipski.schema.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.SelectionQuery;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("GrazieInspection")
public class Model {
    //Singleton instance, returned by getModel
    static Model model = null;

    //Hibernate SessionFactory used throughout the program to interact with database
    //private SessionFactory sessionFactory;

    //Reference Date
    //Used instead of today
    private LocalDateTime referenceDate = LocalDateTime.of(2024, 4, 14, 0, 0);

    //Current User is not null if user is logged in.
    //If user is logged out, current user is set back to null.
    //This variable is used to detect if a user is logged in.
    private Tester currentUser = null;

    //Not callable outside the class.
    private Model ()
    {
    }

    public static Model getModel()
    {
        if (model == null) model = new Model ();
        return model;
    }


    @SuppressWarnings("SpellCheckingInspection")
    public List<SmartphoneRegistry> getPhoneRegistry() {
        try (Transactioner tr = new Transactioner(getSessionFactory())){
            SelectionQuery<SmartphoneRegistry> query = tr.get().createSelectionQuery(
                    "from SmartphoneRegistry",
                    SmartphoneRegistry.class);
            List<SmartphoneRegistry> registryList = query.getResultList();

            for (SmartphoneRegistry smartphoneRegistry: registryList) {
                SelectionQuery<Long> scount = tr.get().createSelectionQuery("select count(*) from Smartphone where registry.name = :name", Long.class);
                scount.setParameter("name", smartphoneRegistry.getName());

                Long count = scount.getSingleResult();
                smartphoneRegistry.setDeviceCount(count.intValue());

                SelectionQuery<Tuple> savg = tr.get().createSelectionQuery("select count(*), ifnull(avg(rate), 0.0) from Review where registry.name = :name", Tuple.class);
                savg.setParameter("name", smartphoneRegistry.getName());
                Tuple t = savg.getSingleResult();
                Long revcnt = (Long)t.get(0);
                Double avg = (Double)t.get(1);

                smartphoneRegistry.setTotalReviews(revcnt.intValue());
                smartphoneRegistry.setAvgRating(avg.floatValue());

            }

            return registryList;
        }
    }
    public List<Tester> getUsers() {
        try (Transactioner tr = new Transactioner(getSessionFactory())) {
            SelectionQuery<Tester> query = tr.get().createSelectionQuery(
                    "from Tester",
                    Tester.class);
            return query.getResultList();
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public List<Smartphone> getDevices() {
        try (Transactioner tr = new Transactioner(getSessionFactory())) {
            SelectionQuery<Smartphone> query = tr.get().createSelectionQuery(
                    "from Smartphone",
                    Smartphone.class);
            List<Smartphone> devices = query.getResultList();

            for (Smartphone device: devices) {

                //Check schedules count ahead and if busy righ now
                SelectionQuery<Schedule> ssch = tr.get().createSelectionQuery(
                        "from Schedule where smartphone.id = :deviceId and finish > :finish order by start",
                        Schedule.class);
                ssch.setParameter("deviceId", device.getId());
                ssch.setParameter("finish", Model.getModel().getReferenceDate());
                List<Schedule> sch = ssch.getResultList();

                device.setSchedulesCount(sch.size());

                if (sch.isEmpty()) device.setAvailableNow(true);
                if (!sch.isEmpty())
                {
                    Schedule sc = sch.getFirst();
                    device.setAvailableNow(false);

                    if (sc.getStart().isAfter(Model.getModel().getReferenceDate()))
                    {
                        device.setAvailableNow(true);
                        device.setAvailableDays((int)ChronoUnit.DAYS.between(Model.getModel().getReferenceDate(), sc.getStart()));
                    }
                }
            }
            return devices;
        }
    }

    public Tester getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Tester currentUser) {
        this.currentUser = currentUser;
    }
    public void logOut()
    {
        setCurrentUser(null);
    }
    public boolean isLoggedIn()
    {
        return currentUser != null;
    }

    public LocalDateTime getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(LocalDateTime referenceDate) {
        this.referenceDate = referenceDate;
    }
    //update today to real today
    public void resetReferenceDate() {
        setReferenceDate (LocalDateTime.now());
    }

    public SessionFactory getSessionFactory() {
        return Startup.getSessionFactory();
    }

    public void bookNow(Smartphone device, int book) {
        Schedule schedule = new Schedule(device, getCurrentUser(), getReferenceDate(), book);
        try (Transactioner tr = new Transactioner(getSessionFactory()))
        {
            tr.get().persist (schedule);
        }
    }
    public void bookNow(Schedule schedule) {
        try (Transactioner tr = new Transactioner(getSessionFactory()))
        {
            tr.get().persist (schedule);
        }
    }

    public List<Review> getReviews(SmartphoneRegistry smartModel) {
        List<Review> reviews;
        try (Transactioner tr = new Transactioner(getSessionFactory())) {
            SelectionQuery<Review> query = tr.get().createSelectionQuery(
                    "from Review where registry.name = :registry",
                    Review.class);
            query.setParameter("registry", smartModel.getName());
            reviews= query.getResultList();
        }
        return reviews;
    }

    public List<Schedule> getSchedules(Smartphone device) {
        List<Schedule> schedules = new ArrayList<>();
        try (Transactioner tr = new Transactioner(getSessionFactory())) {
            SelectionQuery<Schedule> query = tr.get().createSelectionQuery(
                    "from Schedule where smartphone.id = :id and finish > :referenceDate order by start",
                    Schedule.class);
            query.setParameter("id", device.getId());
            query.setParameter("referenceDate", Model.getModel().getReferenceDate());
            List<Schedule> stmp = query.getResultList();
            Schedule prev = null;
            for (int i = 0; i < stmp.size(); i++)
            {
                Schedule schedule = stmp.get(i);
                if (prev != null && prev.getFinish() != null)
                {
                    int daysAvailable =(int)ChronoUnit.DAYS.between(prev.getFinish(), schedule.getStart());
                    if (daysAvailable > 1)
                    {
                        //(Smartphone smartphone, Tester reviewer, LocalDateTime start, LocalDateTime finish)
                        Schedule unassigned = new Schedule(device, Model.getModel().getCurrentUser(), prev.getFinish().plusDays(1), daysAvailable);
                        unassigned.setUnallocated(true);
                        schedules.add(unassigned);
                    }
                }
                schedules.add(schedule);
                prev = schedule;
            }




        }
        return schedules;
    }
}
