package com.example.androidfitnessapp.data;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class UserData implements Serializable
{
    private String id;

    public UserData(UserData user)
    {
        if(user == null)
            return;
        id = user.id;
    }

    public UserData()
    {
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
