package com.example.witch.app.login;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witch.R;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindNull;
import se.snylt.witch.annotations.Data;

class LoginActivityBinder {

    private Login login;

    void setLogin(Login login) {
        this.login = login;
    }

    @BindData(id = R.id.emailTitle, view = TextView.class, set = "text")
    @BindNull
    String emailTitle() {
        return "Email address" + (login.getErrors().isEmpty() ? "" : " x ");
    }

    @BindData(id = R.id.emailAddress, view = View.class, set = "enabled")
    boolean emailEnabled() {
        return !login.isLoggingIn();
    }

    @BindData(id = R.id.passwordTitle, view = TextView.class, set = "text")
    @BindNull
    String passwordTitle() {
        return "Password";
    }

    @BindData(id = R.id.password, view = View.class, set = "enabled")
    boolean passwordEnabled() {
        return !login.isLoggingIn();
    }

    @BindData(id = R.id.loginbutton, view = View.class, set = "enabled")
    boolean loginButtonEnabled() {
        return login.getErrors().isEmpty() && !login.isLoggingIn();
    }

    @BindData(id = R.id.progress, view = View.class, set = "visibility")
    int progressVisibility() {
        return login.isLoggingIn() ? View.VISIBLE : View.GONE;
    }

    @Data
    String loggedInMessage() {
        return login.isLoggedIn() ? "Logged in! :)" : null;
    }

    @Bind(id = R.id.container)
    void loggedInMessage(View view, String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

}