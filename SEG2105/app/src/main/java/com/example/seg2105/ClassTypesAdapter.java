package com.example.seg2105;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassTypesAdapter extends RecyclerView.Adapter<ClassTypesAdapter.ViewHolder> {

    private List<ClassType> user_list;

    public ClassTypesAdapter(List<ClassType> user_list) {
        this.user_list = user_list;
    }


    @NonNull
    @Override
    public ClassTypesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_class_type, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ClassTypesAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        ClassType class_type = user_list.get(position);
        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;

        textView.setText(class_type.name + " : " + class_type.description);
        Button button = holder.deleteButton;
        button.setText("Delete");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class_type.delete();
            }
        });

        Button descriptionButton = holder.description;
        descriptionButton.setText("Edit Class Description");

        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), EditClasses.class);
                intent.putExtra("classId", class_type.id);
                ((Activity)view.getContext()).startActivityForResult(intent,1);

            }
        });

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
        public Button description;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.class_type_name);
            deleteButton = (Button) itemView.findViewById(R.id.delete_button);
            description = (Button) itemView.findViewById(R.id.editDescription);
        }
    }
}
