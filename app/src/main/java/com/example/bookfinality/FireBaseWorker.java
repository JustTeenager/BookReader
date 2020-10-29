package com.example.bookfinality;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FireBaseWorker{
    private static FireBaseWorker fb;

    private DatabaseReference ref;
    private ValueEventListener listener;

    private String emailTextString;
    private String nameTextString;
    private String phoneTextString;

    public Task<Void> addToDatabase(String email, String phone, String name){
        HashMap<String,Object> map=new HashMap<>();
        map.put(emailTextString,email);
        map.put(phoneTextString,phone);
        map.put(nameTextString,name);
        return ref.child(phone).setValue(map);
    }

    private FireBaseWorker(){
        ref= FirebaseDatabase.getInstance().getReference("users");
        emailTextString="email";
        nameTextString="name";
        phoneTextString="phone";
    }

    public static FireBaseWorker getWorker(){
        if (fb==null) fb=new FireBaseWorker();
        return fb;
    }
}
