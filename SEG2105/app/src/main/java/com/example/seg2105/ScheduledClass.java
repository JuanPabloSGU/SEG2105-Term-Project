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
// class for the schedule classes
public class ScheduledClass extends Model.ModelHack {
    private static final String COLLECTION_NAME = "scheduled_classes";
    public String id;
    public String day_of_the_week;
    public User instructor;
    public int capacity;
    public String difficulty;
    public ClassType class_type;
    public int current_capacity;
    public String time;

    public ScheduledClass(String id, String day_of_the_week, int capacity, String difficulty, User instructor, ClassType class_type, String time, int current_capacity){
        this.id = id;
        this.day_of_the_week = day_of_the_week;
        this.capacity = capacity;
        this.difficulty = difficulty;
        this.instructor = instructor;
        this.class_type = class_type;
        this.current_capacity = current_capacity;
        this.time = time;
    }
    // creates and returns a static scheduled class , with the custom callback (FOR ADMINS)
    public static ScheduledClass create(String day_of_the_week, int capacity, String difficulty, User instructor, ClassType class_type, String time, int current_capacity, customCallback cb) throws ExecutionException, InterruptedException {
        return ScheduledClass.genericCreate(day_of_the_week, capacity, difficulty,  instructor, class_type, time, current_capacity, cb);
    }
    // creates and returns scheduled class that an instructor is using
    public static ScheduledClass create(String day_of_the_week, int capacity, String difficulty, String instructor_user_id, ClassType class_type, String time, int current_capacity, customCallback cb) throws ExecutionException, InterruptedException {
        User instructor = User.getUserByUserId(instructor_user_id);
        return ScheduledClass.genericCreate(day_of_the_week, capacity, difficulty,  instructor, class_type, time, current_capacity, cb);
    }
    // generically creates a scheduled class
    private static ScheduledClass genericCreate(String day_of_the_week, int capacity, String difficulty, User instructor, ClassType class_type, String time , int current_capacity, customCallback cb) throws ExecutionException, InterruptedException {
        ArrayList<ScheduledClass> classes = ScheduledClass.getAllScheduledClasses();
        boolean flag = true;
        for(int x=0;x<classes.size();x++){
            ScheduledClass temp=classes.get(x);
            if(temp.day_of_the_week.equals(day_of_the_week)){
                if(temp.time.equals(time)){
                    cb.onError("Class already exists");
                    flag=false;
                    return null;
                }
            }
        } if(flag) {
            Map<String, Object> data1 = new HashMap<>();
            data1.put("day_of_the_week", day_of_the_week);
            data1.put("capacity", capacity);
            data1.put("difficulty", difficulty);
            DocumentReference instructor_reference =  DB.document("/users/" + instructor.getId());
            data1.put("instructor", instructor_reference);
            DocumentReference class_type_reference =  DB.document("/class_types/" + class_type.id);
            data1.put("class_type", class_type_reference);
            data1.put("time" , time);
            DocumentReference result = Tasks.await(DB.collection(COLLECTION_NAME).add(data1)); // throws into Database
            cb.onSuccess();
            return new ScheduledClass(result.getId(), day_of_the_week, capacity, difficulty,  instructor, class_type, time, current_capacity);
        }
        return null;
    }
    // returns a list of all the scheduled classes
    public static ArrayList<ScheduledClass> getAllScheduledClasses() throws ExecutionException, InterruptedException {
        ArrayList<ScheduledClass> scheduled_classes = new ArrayList<ScheduledClass>();

        QuerySnapshot task = Tasks.await(DB.collection("scheduled_classes").get());

        for (DocumentSnapshot document : task.getDocuments()) {
            scheduled_classes.add(createUsingReference(document));
        }
        return scheduled_classes;
    }

    public static ScheduledClass genericGetOne(String field_name, String field_value) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = ScheduledClass.genericGetOneDocument(field_name, field_value, COLLECTION_NAME);
        assert document != null;
        return createUsingReference(document);
    }
    // returns a scheduled class from it's ID
    public static ScheduledClass getByID(String id) throws ExecutionException, InterruptedException {
        return genericGetOne("id", id);
    }
    //creates a schedule class using a reference
    public static ScheduledClass createUsingReference(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        String day_of_the_week = document.get("day_of_the_week").toString();
        String difficulty = document.get("difficulty").toString();
        int capacity = Integer.parseInt(document.get("capacity").toString());
        String time_of_the_day = document.get("time").toString();
        int current_capacity = Integer.parseInt(document.get("current_capacity").toString());

        DocumentSnapshot instructor_reference = Tasks.await(document.getDocumentReference("instructor").get());
        User instructor = User.createUsingReference(instructor_reference);

        DocumentSnapshot class_type_reference = Tasks.await(document.getDocumentReference("class_type").get());
        ClassType class_type = ClassType.createUsingReference(class_type_reference);

        return new ScheduledClass(document.getId(), day_of_the_week, capacity, difficulty, instructor, class_type, time_of_the_day, current_capacity);
    }

    // deletes a schedule class
    public static void delete(String id, customCallback cb) throws ExecutionException, InterruptedException {
        ScheduledClass scheduled_class_to_delete = getByID(id);
        internalDelete(scheduled_class_to_delete, cb);
    }
    // deletes a schedule class using for custom callback
    public void delete(customCallback cb) throws ExecutionException, InterruptedException {
        internalDelete(this, cb);
    }
    // internally deletes a schedule class from the database
    private static void internalDelete(ScheduledClass scheduled_class_to_delete, customCallback cb){
        if(scheduled_class_to_delete.instructor.getId().equals(User.getCurrentUser().getId())){
            DB.collection(COLLECTION_NAME).document(scheduled_class_to_delete.id).delete();
            cb.onSuccess();
        } else {
            cb.onError("You must be the instructor of this class in order to delete it");
        }
    }

    // returns a list of all schedule classes under a instructors username
    public static ArrayList<ScheduledClass> searchByInstructorUsername(String instructor_username) throws ExecutionException, InterruptedException {
        User instructor = User.getUserByUsername(instructor_username);
        DocumentReference ref = DB.document("/users/" + instructor.getId());
        QuerySnapshot task = Tasks.await(DB.collection("scheduled_classes").whereEqualTo("instructor", ref).get());
        ArrayList<ScheduledClass> class_list = new ArrayList<ScheduledClass>();
        for (DocumentSnapshot document : task.getDocuments()) {
            class_list.add(createUsingReference(document));
        }
        return class_list;
    }
    // returns a list of all classes by its class name
    public static ArrayList<ScheduledClass> searchByClassTypeName(String class_name) throws ExecutionException, InterruptedException {
        ClassType classType = ClassType.searchByClassName(class_name);
        DocumentReference ref = DB.document("/class_types/" + classType.id);
        QuerySnapshot task = Tasks.await(DB.collection("scheduled_classes").whereEqualTo("class_type", ref).get());
        ArrayList<ScheduledClass> class_list = new ArrayList<ScheduledClass>();
        for (DocumentSnapshot document : task.getDocuments()) {
            class_list.add(createUsingReference(document));
        }
        return class_list;
    }
    // returns a list of all classes by its class name
    public static ArrayList<ScheduledClass> searchByClassTypeName(String class_name,User user) throws ExecutionException, InterruptedException {
        ArrayList<ScheduledClass> enrolledClasses =  user.getEnrolledClasses();
        ClassType classType = ClassType.searchByClassName(class_name);
        DocumentReference ref = DB.document("/class_types/" + classType.id);
        QuerySnapshot task = Tasks.await(DB.collection("scheduled_classes").whereEqualTo("class_type", ref).get());
        ArrayList<ScheduledClass> class_list = new ArrayList<ScheduledClass>();
        for (DocumentSnapshot document : task.getDocuments()) {
            ScheduledClass potential_class = createUsingReference(document);
            boolean flag = false;
            for(ScheduledClass enrolledClass : enrolledClasses){
                if(enrolledClass.id.equals(potential_class.id)){
                    flag = true;
                }
            }
            if(!flag){
                class_list.add(createUsingReference(document));
            }
        }
        return class_list;
    }
    // returns a scheduled class from its class type and day of the week
    public static ScheduledClass searchByClassTypeNameAndDayOfTheWeek(String class_name, String day_of_the_week) throws ExecutionException, InterruptedException {
        ClassType classType = ClassType.searchByClassName(class_name);
        DocumentReference ref = DB.document("/class_types/" + classType.id);
        List<DocumentSnapshot> documents = Tasks.await(DB.collection("scheduled_classes").whereEqualTo("class_type", ref).whereEqualTo("day_of_the_week", day_of_the_week).get()).getDocuments();
        if(documents.isEmpty()){
            System.out.println("RETURNING EMPTY");
            return null;
        }
        return createUsingReference(documents.get(0));
    }

    public static ArrayList<ScheduledClass> searchByDayOfTheWeek(String day_of_the_week) throws ExecutionException, InterruptedException {
        QuerySnapshot task = Tasks.await(DB.collection("scheduled_classes").whereEqualTo("day_of_the_week", day_of_the_week).get());
        ArrayList<ScheduledClass> class_list = new ArrayList<ScheduledClass>();
        for (DocumentSnapshot document : task.getDocuments()) {
            class_list.add(createUsingReference(document));
        }
        return class_list;
    }
    public static ArrayList<ScheduledClass> searchByDayOfTheWeek(String day_of_the_week, User user) throws ExecutionException, InterruptedException {
        ArrayList<ScheduledClass> enrolledClasses =  user.getEnrolledClasses();
        QuerySnapshot task = Tasks.await(DB.collection("scheduled_classes").whereEqualTo("day_of_the_week", day_of_the_week).get());
        ArrayList<ScheduledClass> class_list = new ArrayList<ScheduledClass>();
        for (DocumentSnapshot document : task.getDocuments()) {
            ScheduledClass potential_class = createUsingReference(document);
            boolean flag = false;
            for(ScheduledClass enrolledClass : enrolledClasses){
                if(enrolledClass.id.equals(potential_class.id)){
                    flag = true;
                }
            }
            if(!flag){
                class_list.add(createUsingReference(document));
            }
        }
        return class_list;
    }
}
