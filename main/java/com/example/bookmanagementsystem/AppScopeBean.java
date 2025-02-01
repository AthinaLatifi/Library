package com.example.bookmanagementsystem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")

public class AppScopeBean {
    private int numberofusers;

    public AppScopeBean() { numberofusers = 0; }

    public int getNumberofUsers() {
        return numberofusers;
    }
    public void setNumberofUsers(int numberofusers) {
        this.numberofusers = numberofusers;
    }
}
