package users;

import crops.Crop;
import interfaces.FarmerOptions;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "farmers")
public class Farmer extends Person implements FarmerOptions {

    private String address;
    private int chatPort = new Random().nextInt(4500)+4000;

    public int getChatPort() {
        return chatPort;
    }

    public void setChatPort(int chatPort) {
        this.chatPort = chatPort;
    }

    public Farmer(String email, String fullName, String password, String address) {
        super(email, fullName, password);
        this.address = address;
    }

    public Farmer() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void addCrop() {

    }

    @Override
    public List viewAllCrops() {
        return null;
    }

    @Override
    public void updateCrop(Crop c) {

    }

    @Override
    public Crop getCropByName(String name) {
        return null;
    }

    @Override
    public void viewHistory() {

    }


}
