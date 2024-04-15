package org.filipski;

import org.filipski.model.DataGenerator;
import org.filipski.model.Transactioner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Startup {
    static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void dataBuilder() throws Throwable
    {

        StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();
        registry.loadProperties("hibernate.create.properties");

        MetadataSources srcs = new MetadataSources(registry.build());
        for (var cls : DataGenerator.classes) srcs.addAnnotatedClass(cls);
        Metadata metadata = srcs.buildMetadata();

        sessionFactory = metadata.buildSessionFactory();
        try (Transactioner tr = new Transactioner(sessionFactory))
        {
            for (var r: DataGenerator.registry.values()) tr.get().persist(r);
            for (var s: DataGenerator.smartphones.values()) tr.get().persist (s);
            for (var s: DataGenerator.testers.values()) tr.get().persist (s);
            for (var s: DataGenerator.testPlan.getPlan()) tr.get().persist (s);
            for (var r: DataGenerator.reviews) tr.get().persist (r);
        }
        /* //keep it so far
        sessionFactory.inTransaction(session -> {
            for (var r: DataGenerator.registry.values()) session.persist(r);
            for (var s: DataGenerator.smartphones.values()) session.persist (s);
            for (var s: DataGenerator.testers.values()) session.persist (s);
            for (var s: DataGenerator.testPlan.getPlan()) session.persist (s);
            for (var r: DataGenerator.reviews) tr.get().persist (r);
        });*/

    }
    public static void dataLoader() throws Throwable
    {

        StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();
        registry.loadProperties("hibernate.load.properties");

        MetadataSources srcs = new MetadataSources(registry.build());
        for (var cls : DataGenerator.classes) srcs.addAnnotatedClass(cls);
        Metadata metadata = srcs.buildMetadata();

        sessionFactory = metadata.buildSessionFactory();

    }
}
