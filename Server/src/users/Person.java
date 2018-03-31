package users;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import org.hibernate.engine.jdbc.BinaryStream;

import javax.imageio.ImageIO;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@MappedSuperclass
public class Person implements Serializable {

    private String email;
    private boolean available;
    private String fullName;
    private String password;
    private byte[] image;
    private double balance = 0.0;
    private String alias;





//    private int chatPort = 4000;

    public Person() {

    }

    public Person(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.image = image;

    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
       // return (Byte[]) image;
       return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", image=" + image +
                '}';
    }
}
