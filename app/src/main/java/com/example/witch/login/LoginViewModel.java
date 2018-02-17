package com.example.witch.login;

import com.example.witch.R;

import android.view.View;

import java.util.PriorityQueue;

import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;

class LoginViewModel {

    private String password;

    private String username;

    private PriorityQueue<String> message = new PriorityQueue<>(1);

    private boolean isError = false;

    private boolean isLoggingIn = false;

    LoginViewModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String password() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String username() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void setIsError(boolean isError) {
        this.isError = isError;
    }

    boolean passwordValid() {
        return !isError;
    }

    @BindData(id = R.id.login_fragment_button, view = View.class, set = "enabled")
    Boolean loginButtonEnabled() {
        return !isLoggingIn;
    }

    boolean usernameValid() {
        return !isError;
    }

    @BindData(id = R.id.login_fragment_username, view = View.class, set = "enabled")
    Boolean usernameEnabled() {
        return !isLoggingIn;
    }

    @BindData(id = R.id.login_fragment_password, view = View.class, set = "enabled")
    Boolean passwordEnabled() {
        return !isLoggingIn;
    }

    @BindData(id = R.id.login_fragment_progress_view, view = View.class, set = "visibility")
    Integer progressVisible() {
        return isLoggingIn ? View.VISIBLE : View.INVISIBLE;
    }

    void setLoggingIn(boolean isLoggingIn) {
        this.isLoggingIn = isLoggingIn;
    }

    @BindWhen(BindWhen.ALWAYS)
    String message() {
        return message.isEmpty() ? null : message.poll();
    }

    void setMessage(String alert) {
        this.message.add(alert);
    }
}
