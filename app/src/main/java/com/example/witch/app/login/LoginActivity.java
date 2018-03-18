package com.example.witch.app.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.example.witch.R;
import com.example.witch.app.App;
import com.example.witch.app.TextWatcherAdapter;

import java.util.Observable;
import java.util.Observer;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.viewbinder.Witch;

public class LoginActivity extends AppCompatActivity {

    private LoginViewBinder view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        view = new LoginViewBinder(Witch.viewFinder(this));

        App.getLogin().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (App.getLogin().getState().isLoggedIn()) {
                    // Logged in!
                }
            }
        });

        Witch.bind(this, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.getLogin().addObserver(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        App.getLogin().deleteObserver(view);
    }

    @BindData(id= R.id.loginbutton, view = View.class, set = "onClickListener")
    final View.OnClickListener loginButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            App.getLogin().doLogin();
        }
    };

    @Bind(id = R.id.password)
    void passwordChanged(EditText et) {
        et.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {
                App.getLogin().setPassword(s.toString());
            }
        });
    }

    @Bind(id = R.id.emailAddress)
    void emailChanged(EditText et) {
        et.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {
                App.getLogin().setEmailAddress(s.toString());
            }
        });
    }
}
