package com.gmd.lessons.uisample;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class CustomActionbarActivity extends AppCompatActivity
implements View.OnClickListener{

    private Toolbar myToolbar;
    private TextView tviTitle;
    private View iviFavorite;
    private View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_actionbar);
        ui();
        buildCustomToolbar();
    }

    private void buildCustomToolbar(){
        setSupportActionBar(myToolbar);
    }

    private void ui() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        tviTitle = (TextView) findViewById(R.id.tviTitle);
        iviFavorite =  findViewById(R.id.iviFavorite);
        container =  findViewById(R.id.container);

        iviFavorite.setOnClickListener(this);
    }

    public void customTitle(String title){
        tviTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iviFavorite:
                showMessage("Item Favorito");
                customTitle("Otro Título");
                break;
        }
    }

    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
