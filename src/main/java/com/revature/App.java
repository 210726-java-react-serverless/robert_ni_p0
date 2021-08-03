package com.revature;

import com.revature.utils.AppState;

public class App {

    public static void main(String[] args) {
        AppState app = AppState.getAppState();
        app.start();
    }
}
