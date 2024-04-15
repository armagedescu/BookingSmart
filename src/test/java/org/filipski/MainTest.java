package org.filipski;

import jakarta.persistence.Tuple;
import org.filipski.model.DataGenerator;
import org.filipski.model.Model;
import org.filipski.model.Transactioner;
import org.filipski.schema.Smartphone;
import org.filipski.schema.SmartphoneRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.SelectionQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SameParameterValue")
class MainTest {

    @BeforeAll
    static void initDatabase() throws Throwable {
        Startup.dataBuilder();
    }
    @Test
    void testTest()
    {
        assertTrue(true);
    }

    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    @Test
    void registrySelectorTest() throws Throwable
    {

        StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();
        registry.loadProperties("hibernate.load.properties");

        MetadataSources srcs = new MetadataSources(registry.build());
        for (var cls : DataGenerator.classes) srcs.addAnnotatedClass(cls);
        Metadata metadata = srcs.buildMetadata();

        SessionFactory sessionFactory = metadata.buildSessionFactory();
        try (Transactioner tr = new Transactioner(sessionFactory))
        {
            SelectionQuery<Tuple> qry = tr.get().createSelectionQuery("select count(*) xx, 123 xxx from  SmartphoneRegistry", Tuple.class);
            List<Tuple> ls = qry.getResultList();
            for (Tuple l: ls)
                System.out.printf("xx = %s; xxx = %s\n", l.get("xx"), l.get("xxx"));
            for (Tuple l: ls)
                System.out.printf("xx = %s; xxx = %s\n", l.get(0), l.get(1));

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
            for (SmartphoneRegistry smartphoneRegistry: registryList) {
                System.out.printf("%s, %d, %d, %f\n",
                        smartphoneRegistry.getName(), smartphoneRegistry.getDeviceCount(),
                        smartphoneRegistry.getTotalReviews(), smartphoneRegistry.getAvgRating());
            }
        }
    }


    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    @Test
    void devicesSelectorTest() throws Throwable
    {

        StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();
        registry.loadProperties("hibernate.load.properties");

        MetadataSources srcs = new MetadataSources(registry.build());
        for (var cls : DataGenerator.classes) srcs.addAnnotatedClass(cls);
        Metadata metadata = srcs.buildMetadata();

        SessionFactory sessionFactory = metadata.buildSessionFactory();
        try (Transactioner tr = new Transactioner(sessionFactory))
        {
            SelectionQuery<Tuple> qry = tr.get().createSelectionQuery("select count(*) xx, 123 xxx from  SmartphoneRegistry", Tuple.class);
            List<Tuple> ls = qry.getResultList();
            for (Tuple l: ls)
                System.out.printf("xx = %s; xxx = %s\n", l.get("xx"), l.get("xxx"));
            for (Tuple l: ls)
                System.out.printf("xx = %s; xxx = %s\n", l.get(0), l.get(1));

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
            for (SmartphoneRegistry smartphoneRegistry: registryList) {
                System.out.printf("%s, %d, %d, %f\n",
                        smartphoneRegistry.getName(), smartphoneRegistry.getDeviceCount(),
                        smartphoneRegistry.getTotalReviews(), smartphoneRegistry.getAvgRating());
            }
        }
    }

}