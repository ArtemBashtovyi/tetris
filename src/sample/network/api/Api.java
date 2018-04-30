package sample.network.api;

import sample.model.user.User;

import java.util.List;

public interface Api {

    List<User> getUkrGamers();
    List<User> getAllGamers();
}
