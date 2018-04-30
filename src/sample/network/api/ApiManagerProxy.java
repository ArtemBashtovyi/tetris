package sample.network.api;

import sample.model.user.User;

import java.util.List;

public class ApiManagerProxy implements Api {

    private ApiManager apiManager;
    private List<User> uaGamers = null;
    private List<User> allGamers = null;


    @Override
    public List<User> getUkrGamers() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }

        if (uaGamers == null) {
            uaGamers = apiManager.getUkrGamers();
        }

        return uaGamers;
    }

    @Override
    public List<User> getAllGamers() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        if (allGamers == null) {
            allGamers = apiManager.getAllGamers();
        }
        return allGamers;
    }
}
