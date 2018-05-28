package com.knacky.events.data.repository;

import android.content.Context;
import android.net.Uri;

import com.knacky.events.data.entities.firebase.EventModelFirebase;
import com.knacky.events.data.entities.firebase.UserModel;

import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * Created by knacky on 24.05.2018.
 */
public interface FirebaseRepository {
    Completable setNewUser(UserModel userModel, Context context);
    Completable createEvent(EventModelFirebase eventModelFirebase);
    Single<Uri> upload(Uri fileUri, String child);
    void uploadFile(Uri fileUri, String child);
    Single<Boolean> checkUser(String uuid);
}
