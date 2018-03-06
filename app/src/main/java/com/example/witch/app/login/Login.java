package com.example.witch.app.login;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Login {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private LoginChanged loginChanged;

    public interface LoginChanged {
        void onChanged(Login login);
    }

    private String emailAddress = null;

    private String password = null;

    private List<Error> errors = new ArrayList<>();

    private boolean loggingIn = false;

    private boolean loggedIn = false;

    Login(LoginChanged loginChanged) {
        this.loginChanged = loginChanged;
        validate();
    }

    void setPassword(String password) {
        this.password = password;
        validate();
    }

    void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        validate();
    }

    private void validate() {
        errors.clear();
        if (emailAddress == null || emailAddress.isEmpty()) {
            errors.add(Error.EMPTY_EMAIL);
        }

        if (emailAddress != null && !isValidEmailAddress(emailAddress)) {
            errors.add(Error.INVALID_EMAIL);
        }

        if (password == null || password.isEmpty()) {
            errors.add(Error.EMPTY_PASSWORD);
        }

        onChanged();
    }

    void doLogin() {
        loggingIn = errors.isEmpty();
        onChanged();

        if (isLoggingIn()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loggingIn = false;
                    loggedIn = true;
                    onChanged();
                }
            }, 2000);
        }
    }

    List<Error> getErrors() {
        return errors;
    }

    boolean isLoggingIn() {
        return loggingIn;
    }

    boolean isLoggedIn() {
        return loggedIn;
    }

    private void onChanged() {
        loginChanged.onChanged(this);
    }

    private boolean isValidEmailAddress(String emailAddress) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailAddress);
        return matcher.find();
    }

    public enum Error {
        INVALID_EMAIL,
        EMPTY_EMAIL,
        EMPTY_PASSWORD
    }
}
