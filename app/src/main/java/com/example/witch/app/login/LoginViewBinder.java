package com.example.witch.app.login;

import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witch.R;
import com.example.witch.app.App;
import com.example.witch.app.Login;
import com.example.witch.app.witch.WitchBinder;

import java.util.Observable;
import java.util.Observer;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.Data;
import se.snylt.witchcore.viewfinder.ViewFinder;

class LoginViewBinder extends WitchBinder implements Observer {

    LoginViewBinder(ViewFinder viewFinder) {
        super(viewFinder);
    }

    private Login.State state() {
        return App.getLogin().getState();
    }

    @Data
    String emailTitle() {
        if (state().getErrors().contains(Login.Error.EMPTY_EMAIL)) {
            return "Email address";
        } else if (state().getErrors().contains(Login.Error.INVALID_EMAIL)) {
            return "person@company.ab";
        }
        return "Perfect!";
    }

    @Bind(id = R.id.emailTitle)
    void emailTitle(TextView textView, String title) {
        textView.setTranslationY(textView.getHeight()*0.75f);
        textView.setAlpha(0);
        textView.animate()
                .translationY(0)
                .alpha(1)
                .setInterpolator(new OvershootInterpolator(2f)).setDuration(500).start();
        textView.setText(title);
    }

    @BindData(id = R.id.emailAddress, view = View.class, set = "enabled")
    boolean emailEnabled() {
        return !state().isLoggingIn();
    }

    @BindData(id = R.id.password, view = View.class, set = "enabled")
    boolean passwordEnabled() {
        return !state().isLoggingIn();
    }

    @BindData(id = R.id.loginbutton, view = View.class, set = "enabled")
    boolean loginButtonEnabled() {
        return state().getErrors().isEmpty() && !state().isLoggingIn();
    }

    @BindData(id = R.id.progress, view = View.class, set = "visibility")
    int progressVisibility() {
        return state().isLoggingIn() ? View.VISIBLE : View.GONE;
    }

    @Data
    String loggedInMessage() {
        return state().isLoggedIn() ? "Logged in! :)" : null;
    }

    @Bind(id = R.id.container)
    void loggedInMessage(View view, String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void update(Observable o, Object arg) {
        bind();
    }
}