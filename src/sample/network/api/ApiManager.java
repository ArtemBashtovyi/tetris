package sample.network.api;

import sample.model.user.User;

import java.util.ArrayList;
import java.util.List;

// TODO : impl GET queries to server
public class ApiManager implements Api{

    @Override
    public List<User> getUkrGamers() {
        return new ArrayList<>();
    }

    @Override
    public List<User> getAllGamers() {
        return new ArrayList<>();
    }
}
