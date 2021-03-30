package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.MantisUser;

import java.util.List;

public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper(){

        // A SessionFactory is set up once for an application!

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public MantisUser mantisUser(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<MantisUser> result = session.createQuery("FROM MantisUser WHERE access_level = '25'").list();
        session.getTransaction().commit();
        session.close();
        return result.iterator().next();
    }
}