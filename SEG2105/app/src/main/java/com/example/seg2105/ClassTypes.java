package com.example.seg2105;

import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ClassTypes {
    private String id;
    public String name;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    public String description;
    public String day;
    public UserView user;
    public int capacity;

    public ClassTypes(String id, String name, String description, String day, int capacity, UserView user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.day = day;
        this.capacity = capacity;
        this.user = user;
    }

    public String getType(){
        return this.name;
    }

    // Admin can create classes using this constructor
    public static ClassTypes create(FirebaseFirestore db, String name, String description, String day, int capacity, String user_id) throws ExecutionException, InterruptedException {
        // checkClasses();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", name);
        data1.put("description", description);
        data1.put("day", day);
        data1.put("capacity", capacity);
        UserView instructor = UserView.getUserByUsername(user_id);
        DocumentReference instructor_reference =  db.document("/users/" + instructor.id);
        data1.put("instructor", instructor_reference);
        db.collection("class_types").add(data1);

        return new ClassTypes("", name, description, day,  capacity, instructor);
    }

    public static ArrayList<ClassTypes> getAllClassTypes()  throws ExecutionException, InterruptedException {
        ArrayList<ClassTypes> class_types = new ArrayList<ClassTypes>();

        QuerySnapshot task = Tasks.await(db.collection("class_types").get());

        for (DocumentSnapshot document : task.getDocuments()) {

            String class_type_name = document.get("name").toString();
            String class_type_description = document.get("description").toString();
            String class_type_day = document.get("day").toString();
            DocumentSnapshot class_type_instructor_reference = Tasks.await(document.getDocumentReference("instructor").get());
            String class_instructor = class_type_instructor_reference.get("user_id").toString();
            int class_type_capacity = Integer.parseInt(document.get("capacity").toString());
            UserView instructor = UserView.getUserByID(class_instructor);
            System.out.println("INSTRUCTOR: "+ instructor.getUsername());
            ClassTypes temp_class = new ClassTypes(document.getId(), class_type_name, class_type_description, class_type_day, class_type_capacity, instructor);
            class_types.add(temp_class);
        }
        return class_types;
    }

    // Method used to delete existing classes
    public static void delete(String id){
        db.collection("class_types").document(id).delete();
    }
    public void delete(){
        if(this.user.id.equals(UserView.getCurrentUser().id) || user.getRole().equals("admin")) {
            db.collection("class_types").document(this.id).delete();
            System.out.println("Successfully deleted");
        }
        else{
            System.out.println("Cant delete");
        }
    }
    public String getId(){
        return this.id;
    }
    public static void editClassDescription(String id, String new_description, UserView user){
        if(user.getUsername().equals(WelcomePage.getCurrentUser()) || user.getRole().equals("admin")) {
            ClassTypes.editClassDescriptionInternally(id, new_description);
        }
        else{
            System.out.println("Cant edit");
        }
    }

    // Used for editing class description of self (stored in class variables)
    public void editClassDescription(String new_description){
        this.editClassDescriptionInternally(this.id, new_description);
    }
    private static void editClassDescriptionInternally(String id, String new_description){
        db.collection("class_types").document(id).update("description", new_description);
    }

    public static ArrayList<ClassTypes> searchByInstructor(String instructor_username) throws ExecutionException, InterruptedException {
        UserView instructor = UserView.getUserByUsername(instructor_username);
        QuerySnapshot task = Tasks.await(db.collection("class_Types").whereEqualTo("instructor", "/users/"+instructor.id).get());
        ArrayList<ClassTypes> class_list = new ArrayList<ClassTypes>();
        for (DocumentSnapshot document : task.getDocuments()) {
            String class_type_name = document.get("name").toString();
            String class_type_description = document.get("description").toString();
            String class_type_day = document.get("day").toString();
            int class_type_capacity = Integer.parseInt(document.get("capacity").toString());
            class_list.add(new ClassTypes(document.getId(), class_type_name, class_type_description, class_type_day, class_type_capacity, instructor));
        }
        return class_list;
    }


    public static ArrayList<ClassTypes> searchByClassName(String class_name) throws ExecutionException, InterruptedException {
        QuerySnapshot task = Tasks.await(db.collection("class_Types").whereEqualTo("name", class_name).get());
        ArrayList<ClassTypes> class_list = new ArrayList<ClassTypes>();
        for (DocumentSnapshot document : task.getDocuments()) {
            String class_type_name = document.get("name").toString();
            String class_type_description = document.get("description").toString();
            String class_type_day = document.get("day").toString();
            DocumentSnapshot class_type_instructor_reference = Tasks.await(document.getDocumentReference("instructor").get());
            String class_instructor = class_type_instructor_reference.get("user_id").toString();
            int class_type_capacity = Integer.parseInt(document.get("capacity").toString());
            UserView instructor = UserView.getUserByID(class_instructor);
            System.out.println("INSTRUCTOR: " + instructor.getUsername());
            class_list.add(new ClassTypes(document.getId(), class_type_name, class_type_description, class_type_day, class_type_capacity, instructor));

        }
        return  class_list;
    }
    
}
