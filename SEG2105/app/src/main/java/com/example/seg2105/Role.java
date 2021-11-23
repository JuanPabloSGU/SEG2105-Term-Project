package com.example.seg2105;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Role extends Model.ModelHack {
    private static final String COLLECTION_NAME = "roles";
    private String id;
    private String name;
    public Role(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public static Role getRoleByName(String name) throws ExecutionException, InterruptedException {
        return genericGetOne("name", name);
    }

    public static Role getRoleById(String id) throws ExecutionException, InterruptedException {
        return genericGetOne("id", id);
    }


    public static Role genericGetOne(String field_name, String field_value) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = Role.genericGetOneDocument(field_name, field_value, COLLECTION_NAME);
        assert document != null;
        return createUsingReference(document);
    }

    public static Role createUsingReference(DocumentSnapshot document) {
        String id = document.getId();
        String name = document.get("name").toString();
        return new Role(id, name);
    }
}
