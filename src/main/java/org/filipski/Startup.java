package org.filipski;

import org.filipski.model.DataGenerator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Startup {
    static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    static void dataBuilder() throws Throwable
    {

        StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();
        registry.loadProperties("hibernate.create.properties");

        MetadataSources srcs = new MetadataSources(registry.build());
        for (var cls : DataGenerator.classes) srcs.addAnnotatedClass(cls);
        Metadata metadata = srcs.buildMetadata();

        sessionFactory = metadata.buildSessionFactory();

        sessionFactory.inTransaction(session -> {
            for (var r: DataGenerator.registry.values()) session.persist(r);
            for (var s: DataGenerator.smartphones.values()) session.persist (s);
            for (var s: DataGenerator.testers.values()) session.persist (s);
            for (var s: DataGenerator.testPlan.getPlan()) session.persist (s);
        });
    }
    static void dataLoader() throws Throwable
    {

        StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();
        registry.loadProperties("hibernate.load.properties");

        MetadataSources srcs = new MetadataSources(registry.build());
        for (var cls : DataGenerator.classes) srcs.addAnnotatedClass(cls);
        Metadata metadata = srcs.buildMetadata();

        sessionFactory = metadata.buildSessionFactory();

    }
}
