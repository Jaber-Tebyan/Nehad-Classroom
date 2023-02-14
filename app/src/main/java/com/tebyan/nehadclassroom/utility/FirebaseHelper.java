package com.tebyan.nehadclassroom.utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.tebyan.nehadclassroom.data.User;

public class FirebaseHelper {

    public static String TAG=FirebaseHelper.class.getName();
    //region Interfaces
    public interface OnChildrenDataReceived {
        void onDataReceived(Iterable<DataSnapshot> children);
    }

    public interface OnDataReceived {
        void onDataReceived(DataSnapshot snapshot);
    }

    public interface OnTransactionResult {
        void onSuccess();

        void onFail(@NonNull Exception exception);
    }

    public interface OnDataExists {
        boolean onTeacherExists(int teacherID);
    }
    public interface OnCompleteCallback{
        void onCompleted();
    }
    //endregion


    public static void deleteUser(Context context,
                                  FirebaseDatabase database,
                                  FirebaseAuth auth,
                                  String email,
                                  String pass,
                                  OnTransactionResult onTransactionSuccessFail) {
        if (true) return;
        if (!Utility.isNetworkAvailable(context)) {
            onTransactionSuccessFail.onFail(new Exception("No Internet Connection"));
        }
        AuthCredential credential = EmailAuthProvider.getCredential(email, pass);
        FirebaseUser user = auth.getCurrentUser();
        String UID = "";
        if (user != null) {
            auth.signOut();
        }
        database.getReference().child("user_paths").child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                } else {

                }
            }
        });
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    auth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            if (onTransactionSuccessFail != null)
                                                onTransactionSuccessFail.onSuccess();
                                        } else {
                                            if (onTransactionSuccessFail != null)
                                                onTransactionSuccessFail.onFail(task.getException());
                                        }
                                    }
                                });
                            } else {
                                if (onTransactionSuccessFail != null)
                                    onTransactionSuccessFail.onFail(task.getException());
                            }
                        }
                    });
                } else {
                    if (onTransactionSuccessFail != null) {
                        onTransactionSuccessFail.onFail(task.getException());
                    }
                }
            }
        });


    }

    public static void SignInAccount(Context context,
                                     FirebaseAuth auth,
                                     String email,
                                     String pass,
                                     OnTransactionResult onTransactionSuccessFail) {
        if (!Utility.isNetworkAvailable(context)) {
            if (onTransactionSuccessFail != null)
                onTransactionSuccessFail.onFail(new Exception("Network UnAvailable"));
        }
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (onTransactionSuccessFail != null) onTransactionSuccessFail.onSuccess();
                } else {
                    if (onTransactionSuccessFail != null)
                        onTransactionSuccessFail.onFail(task.getException());
                }
            }
        });

    }

    public static void createUserData(int teacherID,
                                      int studentsCount,
                                      boolean isTeacher,
                                      OnTransactionResult onTransactionResult) {

        User user;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            if (onTransactionResult != null)
                onTransactionResult.onFail(new Exception("No User Logged In"));

        } else {


            String email = currentUser.getEmail();
            if (isTeacher) {
                user = new User(email.split("@")[0], 0, teacherID, User.UserType.TEACHER);
                final String path = "users/" + teacherID + "/teacher/" + currentUser.getUid();
                database.getReference().child("users").child(teacherID + "").child("teacher").child(currentUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            database.getReference().child("user_paths").child(currentUser.getUid()).setValue(path).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (onTransactionResult != null)
                                            onTransactionResult.onSuccess();
                                    } else {
                                        if (onTransactionResult != null)
                                            onTransactionResult.onFail(task.getException());
                                    }
                                }
                            });
                        } else {
                            if (onTransactionResult != null)
                                onTransactionResult.onFail(task.getException());
                        }
                    }
                });

            } else {
                final String path = "users/" + teacherID + "/students/" + currentUser.getUid();
                user = new User(email.split("@")[0], 0, studentsCount, User.UserType.STUDENT);
                database.getReference().child("users").child(teacherID + "").child("students").child(currentUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            database.getReference().child("user_paths").child(currentUser.getUid()).setValue(path).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        database.getReference().child("users").child(teacherID + "").child("students_count").setValue(ServerValue.increment(1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (onTransactionResult != null)
                                                        onTransactionResult.onSuccess();
                                                } else {
                                                    if (onTransactionResult != null)
                                                        onTransactionResult.onFail(task.getException());
                                                }
                                            }
                                        });
                                    } else {
                                        if (onTransactionResult != null)
                                            onTransactionResult.onFail(task.getException());
                                    }
                                }
                            });
                        } else {
                            if (onTransactionResult != null)
                                onTransactionResult.onFail(task.getException());
                        }
                    }
                });

            }
        }


    }


    public static void getUserPath(FirebaseDatabase database, FirebaseAuth auth, OnTransactionResult onTransactionResult, OnDataReceived onDataReceived) {
        if (auth.getCurrentUser() == null) {
            if (onTransactionResult != null)
                onTransactionResult.onFail(new Exception("Is not Signed in"));
            return;
        }
        String uid = auth.getCurrentUser().getUid();
        database.getReference().child("user_paths").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();

                    if (!snapshot.exists()) {
                        if (onTransactionResult != null) {
                            onTransactionResult.onFail(new Exception("No Path Exists"));
                        }
                    } else {
                        if (onDataReceived != null) onDataReceived.onDataReceived(snapshot);
                        if (onTransactionResult != null) onTransactionResult.onSuccess();
                    }
                } else {
                    if (onTransactionResult != null)
                        onTransactionResult.onFail(task.getException());
                }
            }
        });

    }






    public static void SignupAccount(Context context,
                                     String email,
                                     String pass,
                                     int teacherID,
                                     boolean isTeacher,
                                     OnTransactionResult onTransactionSuccessFail,
                                     @NonNull OnDataExists onDataExists) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        if (!Utility.isNetworkAvailable(context)) {
            if (onTransactionSuccessFail != null)
                onTransactionSuccessFail.onFail(new Exception("Network UnAvailable"));
        }
        boolean teacherExists = onDataExists.onTeacherExists(teacherID);

        if (!isTeacher) {
            if (!teacherExists) {
                if (onTransactionSuccessFail != null) {
                    onTransactionSuccessFail.onFail(new Exception("There Is No Such Teacher With Given ID: " + teacherID));
                }
                return;
            }
        } else {
            if (teacherExists) {
                if (onTransactionSuccessFail != null) {
                    onTransactionSuccessFail.onFail(new Exception("A Teacher With Given ID Already Exists"));
                }
                return;
            }

        }


        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = auth.getCurrentUser();
                    if (currentUser == null) {
                        if (onTransactionSuccessFail != null)
                            onTransactionSuccessFail.onFail(new Exception("Some really Unexpected Error Has Happened. User is null"));
                    } else {
                        database.getReference().child("users").child(teacherID + "").child("students_count").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {

                                    DataSnapshot snapshot = task.getResult();
                                    if (!snapshot.exists()) {
                                        snapshot.getRef().setValue(0);
                                    }
                                    final int studentsCount = !snapshot.exists() || snapshot.getValue(int.class) == null ? 0 : snapshot.getValue(int.class);

                                    createUserData(teacherID, studentsCount, isTeacher, new OnTransactionResult() {
                                        @Override
                                        public void onSuccess() {

                                            if (onTransactionSuccessFail != null)
                                                onTransactionSuccessFail.onSuccess();
                                        }

                                        @Override
                                        public void onFail(@NonNull Exception exception) {
                                            if (onTransactionSuccessFail != null)
                                                onTransactionSuccessFail.onFail(exception);
                                        }
                                    });


                                } else {
                                    if (onTransactionSuccessFail != null)
                                        onTransactionSuccessFail.onFail(task.getException());
                                }
                            }

                        });
                    }
                } else {
                    if (onTransactionSuccessFail != null)
                        onTransactionSuccessFail.onFail(task.getException());
                }
            }
        });

    }


}







