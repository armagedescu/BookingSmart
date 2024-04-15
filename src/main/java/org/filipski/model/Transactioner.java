package org.filipski.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Closeable;

public class Transactioner implements Closeable
{
    Session session;
    Transaction trans;
    public Transactioner (SessionFactory sessionFactory)
    {
        this.session = sessionFactory.getCurrentSession();
        trans = session.getTransaction();
        trans.begin();
    }

    public Session get(){return session;}

    @Override
    public void close() {
        this.session.getTransaction().commit();
        this.session.close();
    }
}
