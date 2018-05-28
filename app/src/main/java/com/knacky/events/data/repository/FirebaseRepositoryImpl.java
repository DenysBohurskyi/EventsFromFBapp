package com.knacky.events.data.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.knacky.events.data.entities.firebase.EventModelFirebase;
import com.knacky.events.data.entities.firebase.UserModel;
import com.knacky.events.data.entities.messagge.ChatMessage;
import com.knacky.events.extensions.Prefs;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.subjects.PublishSubject;

/**
 * Created by knacky on 24.05.2018.
 */
public class FirebaseRepositoryImpl implements FirebaseRepository {

    public static PublishSubject<Uri> uploadSubject = PublishSubject.create();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://events-knacky.appspot.com");


    @Override
    public Completable setNewUser(UserModel userModel, Context context) {
        Log.i("onxSetUser", userModel.getFullName());
        FirebaseDatabase.getInstance().getReference().child("users").child(Prefs.getUser(context)).setValue(userModel);
        return Completable.complete();
    }


    @Override
    public Completable createEvent(EventModelFirebase eventModelFirebase) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Events")
                .push()
                .setValue(eventModelFirebase);
        return Completable.complete();
    }

    @Override
    public Single<Uri> upload(Uri fileUri, String child) {
        uploadFile(fileUri, child);
        return uploadSubject.take(1).toSingle()
                .doOnSubscribe(() ->  Log.d("onImageUpload", "eby"));//uploadFile(fileUri, child)
    }

    public void uploadFile(Uri fileUri, String child) {

        Log.d("onImageUpload", "startUploading()");
        StorageReference imagesRef = storageReference.child("images" + "/" + child + "/" + fileUri.getPath());
        UploadTask uploadTask = imagesRef.putFile(fileUri);
        uploadTask
                .addOnSuccessListener(taskSnapshot -> {
                    uploadSubject.onNext(taskSnapshot.getMetadata().getDownloadUrl());
                    Log.d("onImageUpload", "image upload succeed, " + taskSnapshot.getDownloadUrl().toString());
                })
                .addOnFailureListener(e -> {
                    Log.d("onImageUpload", "image upload error" + e.toString());
                    uploadSubject.onError(e);
                });
    }

    @Override
    public Single<Boolean> checkUser(String uuid) {
        return null;
    }
}
