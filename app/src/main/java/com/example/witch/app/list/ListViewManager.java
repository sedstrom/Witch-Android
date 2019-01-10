package com.example.witch.app.list;

import com.example.witch.app.ListDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class ListViewManager extends Observable {

    static class ListState {
        final List<String> data;
        final boolean isLoading;

        private ListState(List<String> data, boolean isLoading) {
            this.data = data;
            this.isLoading = isLoading;
        }
    }

    private ListState state = new ListState(new ArrayList<String>(), false);

    ListState getState() {
        return state;
    }

    private void setState(ListState state) {
        this.state = state;
        setChanged();
        notifyObservers();
    }

    void update() {
        setState(new ListState(state.data, true));
        ListDataRepository.getData(new ListDataRepository.Callback() {
            @Override
            public void success(List<String> data) {
                setState(new ListState(data, false));
            }
        });
    }
}
