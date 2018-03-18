package com.example.witch.app;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends Observable {

    public class State {
        private String emailAddress = null;
        private String password = null;
        private List<Error> errors = new ArrayList<>();
        private boolean loggingIn = false;
        private boolean loggedIn = false;

        public String getEmailAddress() {
            return emailAddress;
        }

        public String getPassword() {
            return password;
        }

        public List<Error> getErrors() {
            return errors;
        }

        public boolean isLoggingIn() {
            return loggingIn;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }
    }

    public enum Error {
        INVALID_EMAIL,
        EMPTY_EMAIL,
        EMPTY_PASSWORD
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private State state = new State();

    Login() { validate(); }

    public State getState() {
        return state;
    }

    public void setPassword(String password) {
        state.password = password;
        validate();
    }

    public void setEmailAddress(String emailAddress) {
        state.emailAddress = emailAddress;
        validate();
    }

    private void validate() {
        state.errors.clear();
        if (state.emailAddress == null || state.emailAddress.isEmpty()) {
            state.errors.add(Error.EMPTY_EMAIL);
        }

        if (state.emailAddress != null && !isValidEmailAddress(state.emailAddress)) {
            state.errors.add(Error.INVALID_EMAIL);
        }

        if (state.password == null || state.password.isEmpty()) {
            state.errors.add(Error.EMPTY_PASSWORD);
        }

        onChanged();
    }

    public void doLogin() {
        state.loggingIn = state.errors.isEmpty();
        onChanged();

        if (state.loggingIn) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    state.loggingIn = false;
                    state.loggedIn = true;
                    onChanged();
                }
            }, 2000);
        }
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        o.update(this, state);
    }

    private void onChanged() {
        setChanged();
        notifyObservers();
    }

    private boolean isValidEmailAddress(String emailAddress) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailAddress);
        return matcher.find();
    }


}
