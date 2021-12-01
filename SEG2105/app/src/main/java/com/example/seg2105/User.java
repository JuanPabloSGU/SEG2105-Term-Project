package com.example.seg2105;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class User extends Model.ModelHack {
    private static String COLLECTION_NAME = "users";
    private String id;
    private static User current_user;
    private Role role;
    private String email;
    private String user_id;
    private String username;
    public User(String id, String username, String email, String user_id, Role role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.user_id = user_id;
        this.role = role;
    }

    private static void create(String username, String password, String email, Role role, customCallback cb) throws ExecutionException, InterruptedException {
        User.genericCreate(username, password, email, role, cb);
    }

    private static void create(String username, String password, String email, String role_name, customCallback cb) throws ExecutionException, InterruptedException {
        Role role = Role.getRoleByName(role_name);
        User.genericCreate(username, password, email, role, cb);
    }

    private static void genericCreate(String username, String password, String email, Role role, customCallback cb) throws ExecutionException, InterruptedException {
        if (getUserByEmail(email) != null) {
            cb.onError("Email is already in use");
        } else if(getUserByUsername(username) != null){
            cb.onError("Username is already in use");
        }
        else {
            M_AUTH.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                // After creating user, it adds user to FireBase

                public void onComplete(@NonNull Task<AuthResult> task) {
                    String user_id = task.getResult().getUser().getUid();
                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("username", username);
                    data1.put("email", user_id);
                    data1.put("user_id", user_id);
                    DocumentReference role_reference = DB.document("/roles/" + role.getId());
                    data1.put("role", role_reference);
                    try {
                        DocumentReference result = Tasks.await(DB.collection(User.COLLECTION_NAME).add(data1));
                        User created_user = new User(result.getId(), email, username, user_id, role);
                        cb.onSuccess(created_user);
                    } catch (ExecutionException e) {
                        cb.onError(e);
                        e.printStackTrace();


                    } catch (InterruptedException e) {
                        cb.onError(e);
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public String getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUserId(){
        return this.user_id;
    }

    public Role getRole(){
        return this.role;
    }

    public void joinClass(ScheduledClass scheduledClass, customCallback cb) {
        Map<String, Object> data1 = new HashMap<>();
        DocumentReference user_reference = DB.document("/users/" + this.getId());
        DocumentReference scheduled_class_reference = DB.document("/scheduled_classes/" + scheduledClass.id);

        data1.put("user", user_reference);
        data1.put("scheduled_class", scheduled_class_reference);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("calling");
                DB.collection("joined_classes").add(data1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        cb.onSuccess();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        cb.onError(e);

                    }
                });
            }
        }).start();
    }

    public ArrayList<ScheduledClass> getEnrolledClasses() throws ExecutionException, InterruptedException {
        ArrayList<ScheduledClass> scheduledClasses = new ArrayList<ScheduledClass>();
        DocumentReference user_reference = DB.document("/users/" + this.getId());

        QuerySnapshot task = Tasks.await(DB.collection("joined_classes").whereEqualTo("user",user_reference).get());

        for (DocumentSnapshot document : task.getDocuments()) {
            scheduledClasses.add(ScheduledClass.createUsingReference(Tasks.await(document.getDocumentReference("scheduled_class").get())));
        }
        return scheduledClasses;
    }

    public static User createUsingReference(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        String id = document.getId();
        String username = document.get("username").toString();
        String email = document.get("email").toString();
        String user_id = document.get("user_id").toString();

        DocumentSnapshot role_reference = Tasks.await(document.getDocumentReference("role").get());
        Role role = Role.createUsingReference(role_reference);

        return new User(id, username, email, user_id, role);
    }

    public static ArrayList<User> getAllUsers() throws ExecutionException, InterruptedException {
        ArrayList<User> users = new ArrayList<User>();

        QuerySnapshot task = Tasks.await(DB.collection(User.COLLECTION_NAME).get());

        for (DocumentSnapshot document : task.getDocuments()) {
            users.add(createUsingReference(document));
        }
        return users;
    }

    public static User getUserById(String id) throws ExecutionException, InterruptedException {
        return genericGetOne("id", id);
    }

    public static User getUserByUsername(String username) throws ExecutionException, InterruptedException {
        return genericGetOne("username", username);
    }

    public static User getUserByEmail(String email) throws ExecutionException, InterruptedException {
        return genericGetOne("email", email);
    }

    public static User getUserByUserId(String user_id) throws ExecutionException, InterruptedException {
        return genericGetOne("user_id", user_id);
    }

    public static User genericGetOne(String field_name, String field_value) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = User.genericGetOneDocument(field_name, field_value, COLLECTION_NAME);
        assert document != null;
        return createUsingReference(document);
    }

    public static void setCurrentUser(User user) {
        User.current_user = user;
    }

    public static User getCurrentUser(){
        return User.current_user;
    }


}
