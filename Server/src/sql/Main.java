package sql;

import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public abstract class Main {
    private static SessionFactory ourSessionFactory;
    private static final Configuration configuration = new Configuration();


    private static SessionFactory getSessionFactory() throws HibernateException {
        if (ourSessionFactory == null) {
            configuration.configure("hibernate.cfg.xml");
            ourSessionFactory = configuration.buildSessionFactory();
        }

        return ourSessionFactory;
    }

    protected static Session getSession(){
        SessionFactory factory = null;
        try{
            factory = getSessionFactory();
        }catch(HibernateException e){
            e.printStackTrace();
        }
        return factory != null ? factory.openSession() : null;
    }

}