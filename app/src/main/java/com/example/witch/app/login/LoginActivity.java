package com.example.witch.app.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witch.R;
import com.example.witch.app.TextWatcherAdapter;
import com.example.witch.app.list.ListActivity;

import java.util.Observable;
import java.util.Observer;

import se.snylt.witch.android.Witch;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Setup;

public class LoginActivity extends AppCompatActivity implements Observer {

    private LoginViewManager viewManager = new LoginViewManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Witch.bind(this, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewManager.addObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewManager.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Witch.bind(this, this);
    }

    private LoginViewManager.LoginState state() {
        return viewManager.getState();
    }

    // Setup
    @Setup(id = R.id.loginbutton)
    void setupLoginButtonClicks(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewManager.login();
            }
        });
    }

    @Setup(id = R.id.password)
    void setupPasswordChanged(EditText et) {
        et.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
               viewManager.onPasswordChanged(s.toString());
            }
        });
    }

    @Setup(id = R.id.emailAddress)
    void setupEmailAddressChanged(EditText et) {
        et.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                viewManager.onEmailChanged(s.toString());
            }
        });
    }

    @Bind
    void loggedIn() {
        if (state().isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, ListActivity.class));
        }
    }

    // Bind
    @Data
    String emailTitle() {
        if (state().getErrors().contains(LoginManager.LoginError.EMPTY_EMAIL)) {
            return "Email address";
        } else if (state().getErrors().contains(LoginManager.LoginError.INVALID_EMAIL)) {
            return "person@company.ab";
        }
        return "Perfect!";
    }

    @Bind(id = R.id.emailTitle)
    @BindWhen(BindWhen.NOT_EQUALS)
    void bindEmailTitle(TextView textView, String emailTitle) {
        textView.setTranslationY(textView.getHeight()*1.5f);
        textView.setAlpha(0);
        textView.animate()
                .translationY(0)
                .alpha(1)
                .setInterpolator(new OvershootInterpolator()).setDuration(400).start();
        textView.setText(emailTitle);
    }

    @BindData(id = R.id.emailTitle, view = TextView.class, set="textColor")
    int emailTitleColor() {
        if (state().loginFailed() && state().hasEmailErrors()) {
            return Color.RED;
        } else {
            return getResources().getColor(R.color.primaryTextColor);
        }
    }

    @BindData(id = R.id.emailAddress, view = View.class, set = "enabled")
    boolean emailEnabled() {
        return !state().isLoggingIn();
    }

    @BindData(id = R.id.password, view = View.class, set = "enabled")
    boolean passwordEnabled() {
        return !state().isLoggingIn();
    }

    @BindData(id = R.id.passwordTitle, view = TextView.class, set="textColor")
    int passwordTitleColor() {
        if (state().loginFailed() && state().hasPasswordErrors()) {
            return Color.RED;
        } else {
            return getResources().getColor(R.color.primaryTextColor);
        }
    }

    @BindData(id = R.id.loginbutton, view = View.class, set = "enabled")
    boolean loginButtonEnabled() {
        return !state().isLoggingIn();
    }

    @BindData(id = R.id.progress, view = View.class, set = "visibility")
    int progressVisibility() {
        return state().isLoggingIn() ? View.VISIBLE : View.GONE;
    }

    @Data
    String loggedInMessage() {
        return state().isLoggedIn() ? "Logged in! :)" : null;
    }

    @Bind(id = R.id.content) @BindWhen(BindWhen.NOT_EQUALS)
    void bindLoggedInMessage(View view, String loggedInMessage) {
        Toast.makeText(view.getContext(), loggedInMessage, Toast.LENGTH_LONG).show();
    }
}
