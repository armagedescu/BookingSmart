package org.filipski.model;

import org.filipski.Startup;
import org.filipski.schema.SmartphoneRegistry;
import org.filipski.schema.Tester;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import java.io.Closeable;
import java.util.List;

public class Model {
    SessionFactory sessionFactory;
    private Tester currentUser = null;


    class Transactioner implements Closeable
    {
        Session session;
        Transaction trans;
        Transactioner ()
        {
            this.session = sessionFactory.getCurrentSession();
            trans = session.getTransaction();
            trans.begin();
        }

        Session get(){return session;}

        @Override
        public void close() {
            this.session.getTransaction().commit();
            this.session.close();
        }
    }

    private Model (SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    static Model model = null;
    public static Model getModel()
    {
        if (model == null) model = new Model (Startup.getSessionFactory());
        return model;
    }


    public List<SmartphoneRegistry> getRegistry() {
        try (Transactioner trs = new Transactioner()) {
            SelectionQuery<SmartphoneRegistry> query = trs.get().createSelectionQuery(
                    "from SmartphoneRegistry",
                    SmartphoneRegistry.class);
            return query.getResultList();
        }
    }
    public List<Tester> getUsers() {
        try (Transactioner trs = new Transactioner()) {
            SelectionQuery<Tester> query = trs.get().createSelectionQuery(
                    "from Tester",
                    Tester.class);
            return query.getResultList();
        }
    }

    public Tester getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Tester currentUser) {
        this.currentUser = currentUser;
    }

}
