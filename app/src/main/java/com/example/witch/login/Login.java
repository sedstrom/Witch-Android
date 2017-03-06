package com.example.witch.login;

import android.os.Handler;

public class Login {

    public interface LoginListener {
        void loginSuccessful();
        void loginFailed();
    }

    public static void login(final String username, final String password, final LoginListener loginListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(username.equals("snylt") && password.equals("password")){
                    loginListener.loginSuccessful();
                } else {
                    loginListener.loginFailed();
                }
            }
        }, 3000);
    }

}
