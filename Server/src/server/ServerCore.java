package server;

import crops.Crop;
import kart.Kart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.SqlAccessor;
import users.Customer;
import users.Farmer;
import users.Person;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerCore implements Runnable {

    private Socket connection;
    static String command="";
     ObjectOutputStream os;
     ObjectInputStream is ;
    static SqlAccessor accessor = new SqlAccessor();
    private static Logger logger = LogManager.getLogger(ServerCore.class );
    private int chatPort;
    private ArrayList<Person> clients = new ArrayList();

    ArrayList<String> users;
    public ServerCore(Socket socket) throws IOException {
        this.connection = socket;
        os = new ObjectOutputStream(connection.getOutputStream());
        is = new ObjectInputStream(connection.getInputStream());


        //todo toolkit not initialized

    }

    public ServerCore(Socket sock, ObjectOutputStream os, ObjectInputStream is) {
        this.connection = sock;
        this.os = os;
        this.is = is;
    }


    @Override
    public void run() {
        while(command.compareTo("exit")!=0){

            if(command.compareToIgnoreCase("something")==0){


            }



            try {
                command = (String) is.readObject();


                if(command.compareToIgnoreCase("updateFarmer")==0){
                    accessor.update((Farmer) is.readObject());

                }


                if(command.compareTo("addCrop")==0) {
                    accessor.createCrop((Crop) is.readObject());
                    System.out.println("creating crops");
                }

                if(command.compareToIgnoreCase("signup")==0 ){

                    accessor.signUp((Person) is.readObject());
                    os.writeObject(1);
                }

                if(command.compareToIgnoreCase("signin")==0 ){

                    os.writeObject(accessor.signIn((String) is.readObject(),(String)is.readObject()));
                }

                if(command.compareToIgnoreCase("getuser")==0 ){

                    Person p = accessor.getPerson((String)is.readObject(),(Integer) is.readObject());
                    clients.add(p);
                    os.writeObject(p);
                }

                if(command.compareToIgnoreCase("getObservableList")==0 ){

                    os.writeObject(accessor.getCrops((Person) is.readObject()));
                }

                if(command.compareToIgnoreCase("uploadUserImage")==0 ){

                    accessor.uploadImage((Person)is.readObject(), (File)is.readObject());
                }

                if(command.compareToIgnoreCase("getImage")==0 ){

                    os.writeObject(accessor.getImage((Person)is.readObject()));
                }

                if(command.compareToIgnoreCase("get farmer list")==0 ){

                    os.writeObject(accessor.getFarmersList());
                }

                if(command.compareToIgnoreCase("startChat")==0 ){
                    Farmer p = (Farmer) is.readObject();
                    chatPort = p.getChatPort();
                    //todo start new chat server;
                    os.writeObject(chatPort);
                }

                if(command.compareToIgnoreCase("updateCrop")==0 ){

                    Crop c = (Crop) is.readObject();
                    accessor.updateCrop(c);
                }

                if(command.compareToIgnoreCase("update selected")==0 ){

                    accessor.updateSelected((String)is.readObject(),(Boolean) is.readObject());
                }

                if(command.compareToIgnoreCase("getCropsForCustomer")==0 ){

                    os.writeObject(accessor.getCropsForCustomer());
                }

                if(command.compareToIgnoreCase("savetokart")==0 ){

                    accessor.saveKart((Kart)is.readObject());
                }

                if(command.compareToIgnoreCase("getkart")==0 ){

                    os.writeObject(accessor.getKart((String)is.readObject()));
                }

                if(command.compareToIgnoreCase("addCredit")==0 ){

                   // accessor.addCredit((int)is.readObject(), (Person)is.readObject());
                    accessor.updateCustomerBal((Customer) is.readObject());
                }

                if(command.compareToIgnoreCase("search by farmer")==0){

                    accessor.SearchCropByFarmer((String)is.readObject());
                }

                if(command.compareToIgnoreCase("updatefarmer")==0){

                    Farmer farmer = (Farmer) is.readObject();
                    farmer.setAvailable(!farmer.isAvailable());
                    accessor.updateFarmer(farmer);
                }

                if(command.compareToIgnoreCase("update")==0){

                    accessor.update((Person) is.readObject());
                }

                if(command.compareToIgnoreCase("updateFarmerImage")==0){

                    accessor.update((Farmer) is.readObject(), (File) is.readObject());
                }

                if(command.compareToIgnoreCase("remove")==0){

                     accessor.remove((Kart) is.readObject());
                 }

                if(command.compareToIgnoreCase("commit")==0){

                    accessor.commit((Kart) is.readObject());
                }
                if(command.compareToIgnoreCase("updateKart")==0){

                                    accessor.update((Kart) is.readObject());
                                }

                if(command.compareToIgnoreCase("gethistory")==0){

                                    os.writeObject(accessor.getHistory((Farmer) is.readObject()));
                                }


                if(command.compareToIgnoreCase("getcrop")==0){

                    os.writeObject(accessor.getCrop((String) is.readObject(), (String) is.readObject()));
                }














            } catch(SocketException e){
                closeStream();

                logger.info("Client Disconnected");
               // 0bg0 e.printStackTrace();
                return;
            } catch (IOException e) {
                logger.error(e.getMessage());
                logger.info("Client Disconnected");
                e.printStackTrace();
                return;

            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage());
                logger.info("Client Disconnected");
                e.printStackTrace();
                return;

            }catch (Exception e){
                logger.error(e.getMessage());

                logger.info("Client Disconnected");
                e.printStackTrace();
                return;

            }
        }

    }


    private void closeStream(){
        try {
            os.close();
            is.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


}
