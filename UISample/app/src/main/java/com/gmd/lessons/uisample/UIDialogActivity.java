package com.gmd.lessons.uisample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gmd.lessons.uisample.fragments.CustomDialogFragment;
import com.gmd.lessons.uisample.fragments.CustomDialogListener;
import com.gmd.lessons.uisample.fragments.TransparentDialogFragment;

public class UIDialogActivity extends AppCompatActivity implements CustomDialogListener {

    private Button btnDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uidialog);
        ui();
    }

    private void ui() {

        btnDialog= (Button) findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
                //showTransparentDialog();
            }
        });
    }

    private void showCustomDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "CustomDialogFragment");

    }

    private void showTransparentDialog() {
        TransparentDialogFragment dialog = new TransparentDialogFragment();
        dialog.show(getSupportFragmentManager(), "TransparentDialogFragment");

    }

    @Override
    public void onAction(Object object) {

    }

    @Override
    public void onDialogPositive(Object object) {

    }

    @Override
    public void onDialogNegative(Object object) {

    }
}
