package sql;

import crops.Crop;
import javafx.scene.image.Image;
import kart.Kart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import users.Customer;
import users.Farmer;
import users.Person;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class SqlAccessor {

    /*ObservableList<Object>*/ ArrayList proData;
    private static Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    private static String qst;
    private static Logger logger = LogManager.getRootLogger();


    private DBManager dbManager = new DBManager();

    public SqlAccessor() {
//        try {
//            //   Class.forName("Client").newInstance();
//            //   String URL = "jdbc:mysql://localhost:1347/FarmBigData";
//            //   connection = DriverManager.getConnection(URL,"root","alpine");
//
//
//            Class.forName("org.sqlite.JDBC");
//          //  Class.forName("com.mysql.jdbc.Driver");
//
//            File exist = new File("Farm.sqlite");
//          //  File exist = new File("Farm2.sql");
//            if (exist.exists())
//                connection = DriverManager.getConnection("jdbc:sqlite:Farm.sqlite");
//              //  connection = DriverManager.getConnection("jdbc:mysql:Farm2.sql");
////            connect = DriverManager
////                    .getConnection("jdbc:mysql://localhost/feedback?"
////                            + "user=sqluser&password=sqluserpw");
//            else{
//                exist.createNewFile();
//                connection = DriverManager.getConnection("jdbc:sqlite:Farm.sqlite");
//              //  connection = DriverManager.getConnection("jdbc:mysql:Farm2.sql");
//
//                String createFarmers = "create table farmers (email varchar(25) primary key, password varchar(25), name varchar(50), address varchar(50),type varchar(15),image blob,available boolean,balance float, chatPort long)";
//                String createCustomers = "create table customers (email varchar(25) primary key, password varchar(25), name varchar(50), type varchar(15),image blob, balance float)";
//                String createCrops = "create table crops ( name varchar(50), weight float, cost float, quantity float, image blob, availabe boolean, farmerEmail varchar (50))";
//                String createKart = "create table kart (cropName varchar(20), customerName varchar (20), farmerName varchar(20), amount float)";
//
//                preparedStatement = connection.prepareStatement(createFarmers);
//                preparedStatement.execute();
//                preparedStatement = connection.prepareStatement(createCrops);
//                preparedStatement.execute();
//                preparedStatement = connection.prepareStatement(createCustomers);
//                preparedStatement.execute();
//                preparedStatement = connection.prepareStatement(createKart);
//                preparedStatement.execute();
//
//            }
//
//
//
//        } catch (ClassNotFoundException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        } catch (SQLException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
    }

    public int signUp(Person p) {

     /*   qst = null;
        if (p instanceof Customer) {
            qst = "insert into customers (name,email,password,type, balance) values('" + p.getFullName() + "','" + p.getEmail() + "','" + p.getPassword() + "','customer','00.00')";
        }
        if (p instanceof Farmer) {
            Farmer farmer = (Farmer) p;
            // qst = "insert into customers (name,address,email,password,type) values('" + p.getFullName() + "','" + p.getAddress() + "','" + p.getEmail() + "','" + p.getPassword() + "',farmer')";
            qst = "insert into farmers (email,password,name,address,type, balance, available, chatPort) values('" + farmer.getEmail() + "','" + farmer.getPassword() + "','" + farmer.getFullName() + "','" + farmer.getAddress() + "','farmer','00.00','0','"+farmer.getChatPort()+"')";
        }
        try {
            PreparedStatement pp = connection.prepareStatement(qst);
            int rs = pp.executeUpdate();
            return rs;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }*/

     dbManager.add(p);


        return 0;
    }


    public int signIn(String email, String password) {

        return dbManager.checkPresent(email, password);

//        int h = 0;
//        String variable = "farmers";
//        while (true) {
//
//            if (h == 1)
//                variable = "customers";
//
//            qst = "select email, password, type from " + variable + " where email ='" + email + "'";
//            ResultSet rs = null;
//            PreparedStatement pp = null;
//            try {
//                pp = connection.prepareStatement(qst);
//                rs = pp.executeQuery();
//                //   rs.next();
//
//                if (rs.next()) {
//                    if (rs.getString(1).compareTo(email) == 0 && rs.getString(2).compareTo(password) == 0) {
//                        // System.out.println("access grandted");
//                        if (rs.getString(3).compareTo("farmer") == 0)
//                            return 1;
//                        else
//                            return 2;
//
//                    }
//                }
//
//            } catch (SQLException e) {
//                logger.error(e.getMessage());
//                e.printStackTrace();
//            }
//
//            if (h == 1)
//                break;
//            h++;
//        }
//
//
//        return 0;
    }

    public Person getPerson(String text, int a) {

        if (a == 1) {

            return dbManager.getFarmer(text);
        } else {

            return dbManager.getCustomer(text);
        }







    }


    public void uploadImage(Person user, File ill) {




        try {
        String qst = null;
        FileInputStream f = new FileInputStream(ill);
   //     ObjectInputStream fil = new ObjectInputStream(f);

        if (user instanceof Customer) {

            qst = "update customers  set image = ? where email ='" + user.getEmail() + "'";
        }
        if (user instanceof Farmer) {
            // InputStream fp = new InputStream(user.getImage());
            qst = "update farmers  set image = ? where email ='" + user.getEmail() + "'";
        }

            PreparedStatement pp = connection.prepareStatement(qst);
          //  pp.setBlob(1, f);
            pp.setBinaryStream(1,f,(int)ill.length());
            pp.executeUpdate();

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public Image getCropImage(String cropName, Person person) {
        qst = "select image from crops where farmer = '" + person.getFullName() + "' &&  name = '" + cropName + "'";
        PreparedStatement pp = null;
        try {
            pp = connection.prepareStatement(qst);
            ResultSet rs = pp.executeQuery();
            if (!rs.next()) {
                return null;
            }


            File file = new File("src/images/cropImage.png");
            System.out.println(file.createNewFile());
            System.out.println(file.exists());
            FileOutputStream fos = new FileOutputStream(file);

            InputStream input = rs.getBinaryStream(1);
            byte[] buffer = new byte[1024];
            while (input.read(buffer) > 0) {
                fos.write(buffer);
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


        return new Image("/images/cropImage.png");

    }

    public File getImage(Person user) {
        qst = null;
        if (user instanceof Customer) {

            qst = "select image from customers  where email ='" + user.getEmail() + "'";
        }
        if (user instanceof Farmer) {
            // InputStream fp = new InputStream(user.getImage());
            qst = "select image from farmers  where email ='" + user.getEmail() + "'";
        }
        try {
            PreparedStatement pp = connection.prepareStatement(qst);
            ResultSet rs = pp.executeQuery();
            if (!rs.next()) {
                return null;
            }


            File file = new File("src/images/userImage.png");
            if(!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            InputStream input = rs.getBinaryStream(1);
            byte[] buffer = new byte[1024];
            while (input.read(buffer) > 0) {
                fos.write(buffer);
            }

            return file;

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        //    return new Image("/images/userImage.png");

        }


     //   return new Image("/images/userImage.png");
        return null;
    }

    public boolean createCrop(Crop c) {

        dbManager.add(c);
        return true;
    }

    public ArrayList getCrops(Person user) {
      //  ArrayList<Object> list = new ArrayList<Object>();

        return dbManager.getCrops(user.getEmail());
    }

    public int checkAgainstCropAndUpdate(Crop c,String updateval) {

        dbManager.update(c);


        if(updateval.equalsIgnoreCase("name")==true) {
            //name
            qst = "Update Crop Set name= '" + c.getName() + "'where name= '" + c.getName() + "'";
        }

        if(updateval.equalsIgnoreCase("quantity")==true) {
            //quantity
            qst = "Update Crop Set quantity= '" + c.getQuantity() + "'where name= '" + c.getName() + "'";
        }

        if(updateval.equalsIgnoreCase("cost")==true) {
            //cost
            qst = "Update Crop Set cost= '" + c.getCost() + "'where name= '" + c.getName() + "'";
        }

        if(updateval.equalsIgnoreCase("weight")==true) {
            //weight
            qst = "Update Crop Set weight= '" + c.getWeight() + "'where name= '" + c.getName() + "'";
        }

        if(updateval.equalsIgnoreCase("available")==true) {
            //available
            qst = "Update Crop Set available= '" + c.isAvailable() + "'where name= '" + c.getName() + "'";

        }try {
            PreparedStatement pp = connection.prepareStatement(qst);
            int rs = pp.executeUpdate();
            return rs;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        for (int i = 0; i < proData.size(); i++) {

            //todo check crop and update

        }
        return 0;

    }

    public int updateCustomerBal(Customer c){

        dbManager.update(c);
        qst = "Update customers Set Balance = '" + c.getBalance()+ "'where email= '" + c.getEmail() + "'";

        try {
            PreparedStatement pp = connection.prepareStatement(qst);
            int rs = pp.executeUpdate();
            return rs;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


        return 0;

    }

    public ArrayList SearchCropByFarmer(String search){

        ArrayList<Object> list = new ArrayList<Object>();

        //	connection = sqliteConnection.dbConnector();
        try {
            qst = "select * from crops c,farmer f where f.name = '"+search+"'";// where email ='"+user.getEmail()+"'";

            PreparedStatement pst = connection.prepareStatement(qst);
            ResultSet rs = pst.executeQuery();

            if (!rs.isClosed()) {
                while (rs.next()) {

                    Crop S = new Crop();

                    S.setName(rs.getString(1));
                    S.setWeight(Double.parseDouble(rs.getString(2)));
                    S.setCost(Double.parseDouble(rs.getString(3)));
                    S.setQuantity(Double.parseDouble(rs.getString(4)));
                    S.setAvailable(rs.getBoolean(6));


                    list.add(S);
                }


            }

            return list;


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


        return new ArrayList();

    }


    public void updateCrop(Crop c) {

        dbManager.update(c);

    }

    public ArrayList getFarmersList() {

        return dbManager.getFarmerList();

//        ArrayList list = new ArrayList();
//        qst = "select * from farmers where available = '1'";
//        try {
//            PreparedStatement pst = connection.prepareStatement(qst);
//            rs = pst.executeQuery();
//
//            while(rs.next()){
//                Farmer p = new Farmer();
//                p.setFullName(rs.getString(3));
//                p.setChatPort((int) rs.getLong(9));
//
//                list.add(p);
//
//            }
//
//            return list;
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public void updateSelected(String email, Boolean aBoolean) {
        qst = "update farmers set available = '"+aBoolean+"' where email = '"+email+"'";

        try {
            connection.prepareStatement(qst).executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ArrayList getCropsForCustomer() {

        return dbManager.getCropsAll();

//        ArrayList<Object> list = new ArrayList<Object>();
//        //	connection = sqliteConnection.dbConnector();
//        try {
//            qst = "select * from crops";
//
//            PreparedStatement pst = connection.prepareStatement(qst);
//            ResultSet rs = pst.executeQuery();
//
//            if (!rs.isClosed()) {
//                while (rs.next()) {
//
//                    Crop S = new Crop();
//
//                    S.setName(rs.getString(1));
//                    S.setWeight(Double.parseDouble(rs.getString(2)));
//                    S.setCost(Double.parseDouble(rs.getString(3)));
//                    S.setQuantity(Double.parseDouble(rs.getString(4)));
//                    S.setAvailable(rs.getBoolean(6));
//                    S.setOwner(rs.getString(7));
//
//
//                    list.add(S);
//                }
//            }
//
//            return list;
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//
//
//        return new ArrayList();
    }

    public void saveKart(Crop crop, Double amount){

    }


    public void update(Object obj) {

        dbManager.update(obj);
    }
 public void updateFarmer(Farmer farmer) {

        dbManager.update(farmer);
    }

    public void saveKart(Kart kart) {
        dbManager.add(kart);
    }

    public ArrayList getKart(String s) {
        return dbManager.getKartCus(s);
    }

    public void remove(Kart kart) {
        dbManager.remove(kart);
    }

    public void commit(Kart kart) {
        dbManager.commit(kart);
    }

    public ArrayList getHistory(Farmer farmer) {
        return dbManager.getHistory(farmer);
    }

    public Crop getCrop(String cropName, String cropOwner) {
        return dbManager.getCrop(cropName, cropOwner);
    }

    public void update(Person farmer, File file) {


        Image img = new Image(file.toURI().toString());
        farmer.setImage(img.toString().getBytes());
        dbManager.update(farmer);
    }
}
