package users;

import crops.Crop;
import interfaces.CustomerOptions;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends Person implements CustomerOptions {


    public Customer(String email, String fullName, String password) {
        super(email, fullName, password);
    }

    public Customer() {

    }

    @Override
    public void addFunds() {

    }

    @Override
    public void viewAllCrops() {

    }

    @Override
    public List<Crop> searchAll(String key) {
        return null;
    }

    @Override
    public List<Crop> searchOne(String key) {
        return null;
    }

    @Override
    public void addToBasket(Crop c, float quantity) {

    }
}
