package com.luannt.lap10515.demosimpleapp.view.login;

import com.luannt.lap10515.demosimpleapp.view.base.MainView;

/**
 * Created by lap10515 on 18/07/2017.
 */

public interface LoginView extends MainView{
    void loginSuccessful(String id);
    void loginFail();

}
