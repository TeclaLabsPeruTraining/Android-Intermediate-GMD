package com.gmd.lessons.asynctaskloader.presenter;

import android.content.Context;

import com.gmd.lessons.asynctaskloader.model.UserEntity;

/**
 * Created by em on 8/06/16.
 */
public interface LogInView {

    void showLoading();
    void hideLoading();

    void onMessageError(String message);
    void gotoMain(UserEntity userEntity);
}
