package com.example.witch.app.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.witch.R;

public class LoginActivity extends AppCompatActivity {

    private Login login;

    private MagicLoginActivityBinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Binder
        binder = new MagicLoginActivityBinder(new LoginActivityBinder(), this);

        // Connect state to binder
        login = new Login(new Login.LoginChanged() {
            @Override
            public void onChanged(Login login) {
                binder.setLogin(login);
            }
        });

        // Updates state
        ((EditText)findViewById(R.id.password)).addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setPassword(s.toString());
            }
        });

        ((EditText)findViewById(R.id.emailAddress)).addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setEmailAddress(s.toString());
            }
        });

        findViewById(R.id.loginbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.doLogin();
            }
        });
    }
}
