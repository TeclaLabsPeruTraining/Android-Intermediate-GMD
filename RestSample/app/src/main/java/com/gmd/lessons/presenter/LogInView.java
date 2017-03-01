package com.gmd.lessons.presenter;


/**
 * Created by em on 8/06/16.
 */
public interface LogInView {

    void showLoading();
    void hideLoading();

    void onMessageError(String message);
    void gotoMain();
}
