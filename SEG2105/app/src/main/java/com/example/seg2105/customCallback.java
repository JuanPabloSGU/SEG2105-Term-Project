package com.example.seg2105;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface customCallback {
    void onSuccess(Task<AuthResult> task);
    void onError(Exception err);
}
