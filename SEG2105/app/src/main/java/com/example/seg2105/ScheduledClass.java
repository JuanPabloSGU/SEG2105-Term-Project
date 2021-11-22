package com.example.seg2105;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ScheduledClass {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private String id;
    public String day_of_the_week;
    public UserView instructor;
    public int capacity;
    public String difficulty;
    public ClassType class_type;

    public ScheduledClass(String id, String day_of_the_week, int capacity, String difficulty, UserView instructor, ClassType class_type){
        this.id = id;
        this.day_of_the_week = day_of_the_week;
        this.capacity = capacity;
        this.difficulty = difficulty;
        this.instructor = instructor;
        this.class_type = class_type;
    }

    public static ScheduledClass create(String day_of_the_week, int capacity, String difficulty, UserView instructor, ClassType class_type, customCallback cb) throws ExecutionException, InterruptedException {
        return ScheduledClass.genericCreate(day_of_the_week, capacity, difficulty,  instructor, class_type, cb);
    }

    public static ScheduledClass create(String day_of_the_week, int capacity, String difficulty, String instructor_user_id, ClassType class_type, customCallback cb) throws ExecutionException, InterruptedException {
        UserView instructor = UserView.getUserByID(instructor_user_id);
        return ScheduledClass.genericCreate(day_of_the_week, capacity, difficulty,  instructor, class_type, cb);
    }

    private static ScheduledClass genericCreate(String day_of_the_week, int capacity, String difficulty, UserView instructor, ClassType class_type, customCallback cb) throws ExecutionException, InterruptedException {
        if(searchByClassTypeNameAndDayOfTheWeek(class_type.name, day_of_the_week) != null){
            cb.onError("class already exists");
            return null;
        } else {
            Map<String, Object> data1 = new HashMap<>();
            data1.put("day_of_the_week", day_of_the_week);
            data1.put("capacity", capacity);
            data1.put("difficulty", difficulty);
            DocumentReference instructor_reference =  db.document("/users/" + instructor.id);
            data1.put("instructor", instructor_reference);
            DocumentReference class_type_reference =  db.document("/class_types/" + class_type.id);
            data1.put("class_type", class_type_reference);
            DocumentReference result = Tasks.await(db.collection("scheduled_classes").add(data1));
            cb.onSuccess();
            return new ScheduledClass(result.getId(), day_of_the_week, capacity, difficulty,  instructor, class_type);
        }
    }

    public static ArrayList<ScheduledClass> getAllScheduledClasses() throws ExecutionException, InterruptedException {
        ArrayList<ScheduledClass> scheduled_classes = new ArrayList<ScheduledClass>();

        QuerySnapshot task = Tasks.await(db.collection("scheduled_classes").get());

        for (DocumentSnapshot document : task.getDocuments()) {
            scheduled_classes.add(createUsingReference(document));
        }
        return scheduled_classes;
    }

    public static ScheduledClass getByID(String id) throws ExecutionException, InterruptedException {
        QuerySnapshot task = Tasks.await(db.collection("scheduled_classes").whereEqualTo("id", id).get());
        DocumentSnapshot document = task.getDocuments().get(0);
        return createUsingReference(document);

    }

    public static ScheduledClass createUsingReference(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        String day_of_the_week = document.get("day_of_the_week").toString();
        String difficulty = document.get("difficulty").toString();
        int capacity = Integer.parseInt(document.get("capacity").toString());

        DocumentSnapshot instructor_reference = Tasks.await(document.getDocumentReference("instructor").get());
        UserView instructor = UserView.createUsingSnapshot(instructor_reference);

        DocumentSnapshot class_type_reference = Tasks.await(document.getDocumentReference("class_type").get());
        ClassType class_type = ClassType.createUsingSnapshot(class_type_reference);

        return new ScheduledClass(document.getId(), day_of_the_week, capacity, difficulty, instructor, class_type);
    }

    public static void delete(String id, customCallback cb) throws ExecutionException, InterruptedException {
        ScheduledClass scheduled_class_to_delete = getByID(id);
        internalDelete(scheduled_class_to_delete, cb);
    }

    public void delete(customCallback cb) throws ExecutionException, InterruptedException {
        internalDelete(this, cb);
    }

    private static void internalDelete(ScheduledClass scheduled_class_to_delete, customCallback cb){
        if(scheduled_class_to_delete.instructor.equals(UserView.getCurrentUser())){
            db.collection("scheduled_classes").document(scheduled_class_to_delete.id).delete();
            cb.onSuccess();
        } else {
            cb.onError("You must be the instructor of this class in order to delete it");
        }
    }


    public static ArrayList<ScheduledClass> searchByInstructorUsername(String instructor_username) throws ExecutionException, InterruptedException {
        UserView instructor = UserView.getUserByUsername(instructor_username);
        DocumentReference ref = db.document("users" + instructor.id);
        QuerySnapshot task = Tasks.await(db.collection("scheduled_classes").whereEqualTo("instructor", ref).get());
        ArrayList<ScheduledClass> class_list = new ArrayList<ScheduledClass>();
        for (DocumentSnapshot document : task.getDocuments()) {
            class_list.add(createUsingReference(document));
        }
        return class_list;
    }

    public static ArrayList<ScheduledClass> searchByClassTypeName(String class_name) throws ExecutionException, InterruptedException {
        ClassType classType = ClassType.searchByClassName(class_name);
        DocumentReference ref = db.document("/class_types/" + classType.id);
        QuerySnapshot task = Tasks.await(db.collection("scheduled_classes").whereEqualTo("class_type", ref).get());
        ArrayList<ScheduledClass> class_list = new ArrayList<ScheduledClass>();
        for (DocumentSnapshot document : task.getDocuments()) {
            class_list.add(createUsingReference(document));
        }
        return class_list;
    }

    public static ScheduledClass searchByClassTypeNameAndDayOfTheWeek(String class_name, String day_of_the_week) throws ExecutionException, InterruptedException {
        ClassType classType = ClassType.searchByClassName(class_name);
        DocumentReference ref = db.document("/class_types/" + classType.id);
        List<DocumentSnapshot> documents = Tasks.await(db.collection("scheduled_classes").whereEqualTo("class_type", ref).whereEqualTo("day_of_the_week", day_of_the_week).get()).getDocuments();
        if(documents.isEmpty()){
            System.out.println("RETURNING EMPTY");
            return null;
        }
        return createUsingReference(documents.get(0));
    }
}
