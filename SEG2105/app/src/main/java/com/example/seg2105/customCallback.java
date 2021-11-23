package com.example.seg2105;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface customCallback<E> {
    default void onSuccess(){

    };
    default void onSuccess(Task<AuthResult> task){

    };
    default void onSuccess(E object) {

    };
    default void onError(Exception err){

    };
    default void onError(String err){

    };
}

