package sql;

import crops.Crop;
import kart.Kart;
import org.hibernate.Session;
import org.hibernate.Transaction;
import users.Customer;
import users.Farmer;
import users.Person;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends Main {

    private static DBManager instance;
    private Transaction transaction = null;
    private Session session;



    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static void setInstance(DBManager instance) {
        DBManager.instance = instance;
    }

    public void add(Object p){
         session = getSession();

        try{
            transaction = session.beginTransaction();
            session.save(p);
            transaction.commit();

        }catch (RuntimeException e){
            if(transaction!=null)
                transaction.rollback();
        }
        finally {
            if(transaction!=null && transaction.isActive())
                session.flush();
            session.close();
        }
    }

    public void update(Object a) {
         session = getSession();
        try {
            transaction = session.beginTransaction();
            session.update(a);
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (transaction != null && transaction.isActive()) {
                session.flush();
            }
            session.close();
        }

    }

    public void remove(Object o){

        session = getSession();
        try {
            transaction = session.beginTransaction();

            session.remove(o);
         //   session.delete(o);
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (transaction != null && transaction.isActive()) {
                session.flush();
            }
            session.close();
        }
    }



    public Farmer getFarmer(String id) {
        session = getSession();
        Farmer farmer = null;

        try {
            farmer =(Farmer) session.get(Farmer.class, id);
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        return farmer;
    }

    public Customer getCustomer(String id) {
        session = getSession();
        Customer customer = null;

        try {
            customer = (Customer) session.get(Customer.class, id);
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        return customer;
    }

    public Kart getKart(String id){

        session = getSession();
        Kart kart = null;

        try {
            kart = (Kart) session.get(Kart.class, id);
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        return kart;
    }

    public Crop getCrop(String id, String cropOwner) {
        Session session = getSession();
        Crop crop = null;

        try {
          //  crop = session.find(Crop.class, id, cropOwner);
            crop = (Crop) session.createQuery("from Crop where name =:id and owner=:own").setParameter("id",id).setParameter("own",cropOwner);
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        return crop;
    }

    public ArrayList getCrops(String key) {
        session = getSession();

        List l = new ArrayList();

        try {
            l = session.createQuery("from Crop c where c.owner=:id").setParameter("id", key).list();

        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        ArrayList list = (ArrayList) l;
        return list;
    }

    public int checkPresent(String email, String pass){
        session = getSession();
        try {
            Farmer f = getFarmer(email);
            Customer c = getCustomer(email);
            if(f!=null)
            if (f.getPassword().compareToIgnoreCase(pass) == 0)
                return 1;
            if(c!=null)
            if (c.getPassword().compareToIgnoreCase(pass) == 0)
                return 2;
            else return 0;
        }catch(org.hibernate.UnknownEntityTypeException e){

        }catch (RuntimeException e){

        }
        return 0;
    }


    public ArrayList getCropsAll() {

        session = getSession();
        List l = new ArrayList();

        try {
            l = session.createQuery("from Crop ").list();

        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        ArrayList list = (ArrayList) l;
        return list;
    }

    public ArrayList getKartCus(String key) {

        session = getSession();
        List l = new ArrayList();

        try {
            l = session.createQuery("from Kart k where k.customerEmail=:id and k.committed=false ").setParameter("id", key).list();

        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        ArrayList list = (ArrayList) l;
        return list;
    }

    public ArrayList getKartFarm(String key) {

        session = getSession();
        List l = new ArrayList();

        try {
            l = session.createQuery("from Kart k where k.cropOwner=:id").setParameter("id", key).list();

        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        ArrayList list = (ArrayList) l;
        return list;
    }

    public void commit(Kart kart) {


        Customer cus = getCustomer(kart.getCustomerEmail());
        Farmer far = getFarmer(kart.getCropOwner());
        cus.setBalance(cus.getBalance() - kart.getAmount());
        far.setBalance(far.getBalance() + kart.getAmount());
        update(cus);
        update(far);


    }

    public ArrayList getFarmerList() {

        session = getSession();
        List list = new ArrayList();
         list = session.createQuery("from Farmer where available=true").list();

         ArrayList list1 = new ArrayList(list);
         return list1;

    }

    public ArrayList getHistory(Farmer farmer) {
        session = getSession();
        List list = new ArrayList();
        list = session.createQuery("from Kart ").list();
        ArrayList list1 = new ArrayList(list);
        return list1;

    }

    public void deleteItem(Kart kart) {

        session = getSession();
        try {
            transaction = session.beginTransaction();

          session.createQuery("delete from Kart where date=:id").setParameter("id", kart.getDate());
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (transaction != null && transaction.isActive()) {
                session.flush();
            }
            session.close();
        }
    }
}
