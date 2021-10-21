package com.example.seg2105;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ClassTypes {
    private String id;
    public String name;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String description;
    public String day;
    public int capacity;

    public ClassTypes(String id, String name, String description, String day, int capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.day = day;
        this.capacity = capacity;
    }

    public String getType(){
        return this.name;
    }

    public static ClassTypes create(FirebaseFirestore db, String name, String description, String day, int capacity){
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", name);
        data1.put("description", description);
        data1.put("day", day);
        data1.put("capacity", capacity);
        db.collection("class_types").add(data1);
        return new ClassTypes("", name, description, day, capacity);
    }

    public static void delete(String id){
        db.collection("class_types").document(id).delete();
    }
    public void delete(){
        db.collection("class_types").document(this.id).delete();
        System.out.println("Successfully deleted");
    }
    public String getId(){
        return this.id;
    }
    public static void editClassDescription(String id, String new_description){
        ClassTypes.editClassDescriptionInternally(id, new_description);
    }

    public void editClassDescription(String new_description){
        this.editClassDescriptionInternally(this.id, new_description);
    }
    private static void editClassDescriptionInternally(String id, String new_description){
        db.collection("class_types").document(id).update("description", new_description);
    }
}
