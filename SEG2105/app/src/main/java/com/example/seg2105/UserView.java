package com.example.seg2105;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserView {
    private String username;
    public static UserView current_user;
    public String role;
    public String email;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth =  FirebaseAuth.getInstance();;
    public String id;
    public String user_id;

    public UserView(String username, String role, String user_id, String email, String id){ // Generic constructor
        this.username = username;
        this.email = email;
        this.role = role;
        this.user_id = user_id;
        this.id = id;
    }

    // Generic getters for class variables
    public String getUsername(){
        return this.username;
    }
    public String getRole(){return this.role;}

    // Method used to create Users, returns the completed UserView at the end.
    public static UserView createUser(String username, String user_email, String user_password, String user_role, customCallback... cb){
        DocumentReference finalUser_role = null;
        // Pulls different information from FireBase based on which role the user is.
        switch(user_role){
            case "instructor":
                finalUser_role = db.document("roles/3xQrDfZhc7Kdjr9TTueY");
                break;
            case "member":
                finalUser_role = db.document("/roles/KhXfzrrVCK2dJtSoQeWX");
                break;
        }
        DocumentReference finalUser_role1 = finalUser_role;
        final String[] user_id = new String[1];
        final String[] new_user_id = new String[1];
        // Creating user in FireBase with inputs from user
        mAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            // After creating user, it adds user to FireBase

            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("Created user successfully");
                    user_id[0] = task.getResult().getUser().getUid();
                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("user_id", user_id[0]);
                    data1.put("username", username);
                    data1.put("role", finalUser_role1);
                    Task<DocumentReference> inserted_user = db.collection("users").add(data1);
                    new_user_id[0] = inserted_user.getResult().getId();
                    if(cb[0] != null){
                        cb[0].onSuccess(task);
                    }

                } else {
                    if(cb[0] != null) {
                        cb[0].onError(task.getException());
                    }
                }
            }
        });

        return new UserView(username, user_role, user_id[0], user_email, new_user_id[0]);

    }

    public static interface GetUserInterface{
        void onSuccess(UserView user);
    }

    public static ArrayList<UserView> getAllUsers()  throws ExecutionException, InterruptedException {
        ArrayList<UserView> users = new ArrayList<UserView>();

        QuerySnapshot task = Tasks.await(db.collection("users").get());

        for (DocumentSnapshot document : task.getDocuments()) {

            DocumentSnapshot role_task = Tasks.await(document.getDocumentReference("role").get());
            String user_name = document.get("username").toString();
            String user_id = document.get("user_id").toString();
            String user_role = role_task.get("name").toString();
            String user_email = document.get("email").toString();
            UserView temp_user = new UserView(user_name, user_role, user_id, user_email, document.getId());
            users.add(temp_user);
        }
        return users;
    }

    public static UserView getUserByID(String user_id)  throws ExecutionException, InterruptedException {
        QuerySnapshot task = Tasks.await(db.collection("users").whereEqualTo("user_id", user_id).get());
        DocumentSnapshot instructor_data = task.getDocuments().get(0);
        DocumentSnapshot role_task = Tasks.await(instructor_data.getDocumentReference("role").get());
        String user_name = instructor_data.get("username").toString();
        String user_role = role_task.get("name").toString();
        String user_email = instructor_data.get("email").toString();

        return new UserView(user_name, user_role, user_id, user_email, instructor_data.getId());
    }

    public static UserView getUserByUsername(String user_id)  throws ExecutionException, InterruptedException {
        QuerySnapshot task = Tasks.await(db.collection("users").whereEqualTo("username", user_id).get());
        DocumentSnapshot instructor_data = task.getDocuments().get(0);
        DocumentSnapshot role_task = Tasks.await(instructor_data.getDocumentReference("role").get());
        String user_name = instructor_data.get("username").toString();
        String user_role = role_task.get("name").toString();
        String user_email = instructor_data.get("email").toString();

        return new UserView(user_name, user_role, user_id, user_email, instructor_data.getId());
    }


    // Method that grabs UserView class from FireBase for comparison when logging in. Uses username
    // to compare, and then later compares password credentials.
    public static void getUserByUsername(String username, GetUserInterface method) throws ExecutionException, InterruptedException, InvocationTargetException, IllegalAccessException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<UserView> users = new ArrayList<UserView>();

                    QuerySnapshot task = Tasks.await(db.collection("users").whereEqualTo("username", username).get());


                    DocumentSnapshot document = task.getDocuments().get(0);
                    DocumentSnapshot role_task = Tasks.await(document.getDocumentReference("role").get());
                    String user_role = role_task.get("name").toString();
                    String user_id = document.get("user_id").toString();
                    System.out.println(document);
                    String user_email = document.get("email").toString();
                    String id = document.getId();
                    UserView temp_user = new UserView(username, user_role, user_id, user_email, id);
                    method.onSuccess(temp_user);
                    // Generic exception catchers
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // Another method that grabs UserView class from FireBase for comparison when logging in. Uses
    // id instead of username to compare with the database.
    public static void getUserByID(String user_id, GetUserInterface method) throws ExecutionException, InterruptedException, InvocationTargetException, IllegalAccessException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("calling");
                try {
                    System.out.println("loading users");
                    ArrayList<UserView> users = new ArrayList<UserView>();

                    QuerySnapshot task = Tasks.await(db.collection("users").whereEqualTo("user_id", user_id).get());


                    DocumentSnapshot document = task.getDocuments().get(0);
                    DocumentSnapshot role_task = Tasks.await(document.getDocumentReference("role").get());
                    String user_name = document.get("username").toString();
                    String user_role = role_task.get("name").toString();
                    String user_email = document.get("email").toString();
                    UserView temp_user = new UserView(user_name, user_role, user_id,user_email, document.getId());
                    method.onSuccess(temp_user);
                    // Generic exception catchers
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void setCurrentUser(UserView user) {
        UserView.current_user = user;
    }

    public static UserView getCurrentUser(){
        return UserView.current_user;
    }

}
