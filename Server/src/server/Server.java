package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server  {

    Socket sock, chatt;
     ServerSocket serSock, serverChat;
     ArrayList list = new ArrayList();
    ObjectOutputStream os;
    ObjectInputStream is ;


    private static Logger logger = LogManager.getRootLogger();

    public Server(){
        try {
            serSock = new ServerSocket(1347, 5);
            logger.info("server started");

            while (true) {
                sock = serSock.accept();
                logger.info("Client connected");
                os = new ObjectOutputStream(sock.getOutputStream());
                is = new ObjectInputStream(sock.getInputStream());
                Thread t = new Thread(new ServerCore(sock, os,is));
                t.start();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {


        new Server();

    }


}


