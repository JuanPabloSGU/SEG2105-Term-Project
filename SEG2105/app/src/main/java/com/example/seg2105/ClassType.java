package com.example.seg2105;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ClassType {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String id;
    public String name;
    public String description;
    public ClassType(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static ClassType create(String name, String description, String day, int capacity, String user_id) throws ExecutionException, InterruptedException {
        // checkClasses();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", name);
        data1.put("description", description);
        DocumentReference result = Tasks.await(db.collection("class_types").add(data1));

        return new ClassType(result.getId(), name, description);
    }

    public static ArrayList<ClassType> getAllClassTypes()  throws ExecutionException, InterruptedException {
        ArrayList<ClassType> class_types = new ArrayList<ClassType>();

        QuerySnapshot task = Tasks.await(db.collection("class_types").get());

        for (DocumentSnapshot document : task.getDocuments()) {

            String class_type_name = document.get("name").toString();
            String class_type_description = document.get("description").toString();
            ClassType temp_class = new ClassType(document.getId(), class_type_name, class_type_description);
            class_types.add(temp_class);
        }
        return class_types;
    }
    public void delete(){
        if(UserView.current_user.role.equals("admin")) {
            db.collection("class_types").document(this.id).delete();
            System.out.println("Successfully deleted");
        }
        else{
            System.out.println("Cant delete");
        }
    }
    public void delete(customCallback cb) throws ExecutionException, InterruptedException {
        if(UserView.current_user.role.equals("admin")) {
            Void document = Tasks.await(db.collection("class_types").document(this.id).delete());
            cb.onSuccess();
        }
        else{
            cb.onError("You must be an admin to do this operation");
        }
    }


    // EDIT DESCRIPTION


    public static void editClassDescription(String id, String new_description) throws ExecutionException, InterruptedException {
        if(UserView.getCurrentUser().role.equals("admin")) {
            ClassType.editClassDescriptionInternally(id, new_description);
        }
        else{
            System.out.println("Cant edit");
        }
    }

    public static void editClassDescription(String id, String new_description, customCallback cb) throws ExecutionException, InterruptedException {
        if(UserView.getCurrentUser().role.equals("admin")) {
            ClassType.editClassDescriptionInternally(id, new_description);
            cb.onSuccess();
        }
        else{
            cb.onError("You need to be an administrator");
        }
    }


    public void editClassDescription(String new_description) throws ExecutionException, InterruptedException {
        editClassDescriptionInternally(this.id, new_description);
    }
    private static void editClassDescriptionInternally(String id, String new_description) throws ExecutionException, InterruptedException {
        Tasks.await(db.collection("class_types").document(id).update("description", new_description));
    }

    // EDIT NAME
    public static void editClassName(String id, String new_name, customCallback cb) throws ExecutionException, InterruptedException {
        if(UserView.getCurrentUser().role.equals("admin")) {
            ClassType.editClassNameInternally(id, new_name);
            cb.onSuccess();
        }
        else{
            cb.onError("You need to be an administrator");
        }
    }

    public void editClassName(String new_name) throws ExecutionException, InterruptedException {
        editClassNameInternally(this.id, new_name);
    }
    private static void editClassNameInternally(String id, String new_name) throws ExecutionException, InterruptedException {
        Tasks.await(db.collection("class_types").document(id).update("name", new_name));
    }

    // SEARCH BY NAME

    public static ClassType searchByClassName(String class_name) throws ExecutionException, InterruptedException {
        QuerySnapshot task = Tasks.await(db.collection("class_types").whereEqualTo("name", class_name).get());
        DocumentSnapshot document = task.getDocuments().get(0);
        String name = document.get("name").toString();
        String description = document.get("description").toString();
        return new ClassType(document.getId(), name, description);
    }


    public static ClassType getByID(String class_type_id) throws ExecutionException, InterruptedException {
        QuerySnapshot task = Tasks.await(db.collection("class_types").whereEqualTo("id", class_type_id).get());
        DocumentSnapshot document = task.getDocuments().get(0);
        String name = document.get("name").toString();
        String description = document.get("description").toString();
        return new ClassType(document.getId(), name, description);
    }


    public static ClassType createUsingSnapshot(DocumentSnapshot class_type_snapshot)  throws ExecutionException, InterruptedException {
        String id = class_type_snapshot.getId();
        String name = class_type_snapshot.get("name").toString();
        String description = class_type_snapshot.get("description").toString();


        return new ClassType(id, name, description);
    }

}
