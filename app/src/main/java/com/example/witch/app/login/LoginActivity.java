package com.example.witch.app.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.witch.R;

import se.snylt.witch.viewbinder.Witch;

public class LoginActivity extends AppCompatActivity {

    private LoginOutputBinder output;

    private LoginInputBinder input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        output = new LoginOutputBinder(Witch.viewFinder(this));
        input = new LoginInputBinder(Witch.viewFinder(this));

        // Subscribe state changes
        new LoginState(new LoginState.LoginChanged() {
            @Override
            public void onChanged(LoginState loginState) {
                output.setLoginState(loginState);
                input.setLoginState(loginState);
            }
        });
    }
}
