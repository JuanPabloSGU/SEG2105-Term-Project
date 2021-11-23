package com.example.seg2105;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Model {
    public static class ModelHack {
//        public String COLLECTION_NAME;
        public final static FirebaseFirestore DB = FirebaseFirestore.getInstance();
        public final static FirebaseAuth M_AUTH = FirebaseAuth.getInstance();

        public static List<DocumentSnapshot> query(ArrayList<String> field_names, ArrayList<String> field_values, String collection_name) throws ExecutionException, InterruptedException {
            CollectionReference queryObject = DB.collection(collection_name);
            Query new_query = queryObject;
            for (int i = 0; i < field_names.size(); i++) {
                new_query = new_query.whereEqualTo(field_names.get(i), field_values.get(i));
            }
            return Tasks.await(new_query.get()).getDocuments();
        }

        //    public abstract Model createUsingReference(DocumentSnapshot document) throws ExecutionException, InterruptedException;
        public static DocumentSnapshot genericGetOneDocument(String field_name, String field_value, String collection_name) throws ExecutionException, InterruptedException {
            ArrayList<String> field_names = new ArrayList<String>();
            field_names.add(field_name);
            ArrayList<String> field_values = new ArrayList<String>();
            field_values.add(field_value);

            List<DocumentSnapshot> documents = User.query(field_names, field_values, collection_name);
            System.out.println(documents.size());
            if(documents.isEmpty()){
                return null;
            }
            return documents.get(0);
        }

        public static List<DocumentSnapshot> genericGetMultipleDocuments(String field_name, String field_value, String collection_name) throws ExecutionException, InterruptedException {
            ArrayList<String> field_names = new ArrayList<String>();
            field_names.add(field_name);
            ArrayList<String> field_values = new ArrayList<String>();
            field_values.add(field_value);

            List<DocumentSnapshot> documents = User.query(field_names, field_values, collection_name);
            System.out.println(documents.size());
            if(documents.isEmpty()){
                return null;
            }
            return documents;
        }

    }
}
