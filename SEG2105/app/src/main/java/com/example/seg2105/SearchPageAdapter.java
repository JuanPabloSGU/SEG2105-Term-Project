package com.example.seg2105;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class SearchPageAdapter extends RecyclerView.Adapter<com.example.seg2105.SearchPageAdapter.ViewHolder> {

        private List<ScheduledClass> user_list;
        //contains a list of all the current users
        public SearchPageAdapter(List<ScheduledClass> user_list) {
            this.user_list = user_list;
        }


        @NonNull
        @Override
        public com.example.seg2105.SearchPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_user, parent, false);

            // Return a new holder instance
            com.example.seg2105.SearchPageAdapter.ViewHolder viewHolder = new com.example.seg2105.SearchPageAdapter.ViewHolder(contactView);
            return viewHolder;
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(com.example.seg2105.SearchPageAdapter.ViewHolder holder, int position) {
            // Get the data model based on position
            ScheduledClass scheduledClass = user_list.get(position);

            // Set item views based on your views and data model
            TextView textView = holder.nameTextView;
            textView.setText("Class Type : " + scheduledClass.class_type.name + ", Instructor : " + scheduledClass.instructor.getUsername() + ", Day of the week: " + scheduledClass.day_of_the_week);
            Button button = holder.deleteButton;
            User current_user = User.getCurrentUser();
            if(current_user.getRole().getName().equals("member")){
                button.setText("Join");

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        customCallback cb = new customCallback<ScheduledClass>() {


                            @Override
                            public void onSuccess() {

                                Toast.makeText(view.getContext(), "Class successfully deleted!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onSuccess(Task<AuthResult> task) {

                            }

                            @Override
                            public void onError(Exception err) {

                            }
                            // pop up for error
                            @Override
                            public void onError(String err) {
                                Toast.makeText(view.getContext(), err, Toast.LENGTH_SHORT).show();
                            }
                        };
                        current_user.joinClass(scheduledClass, cb);

                    };
                });
            } else {
                button.setText("Delete");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customCallback cb = new customCallback<ScheduledClass>() {


                            @Override
                            public void onSuccess() {
                                Toast.makeText(view.getContext(), "Class successfully deleted!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onSuccess(Task<AuthResult> task) {

                            }

                            @Override
                            public void onError(Exception err) {

                            }
                            // pop up for error
                            @Override
                            public void onError(String err) {
                                Toast.makeText(view.getContext(), err, Toast.LENGTH_SHORT).show();
                            }
                        };
                        try {
                            scheduledClass.delete(cb);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                });
            }

        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return user_list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView nameTextView;
            public Button deleteButton;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                nameTextView = (TextView) itemView.findViewById(R.id.user_name);
                deleteButton = (Button) itemView.findViewById(R.id.delete_button);
            }
        }
    }

