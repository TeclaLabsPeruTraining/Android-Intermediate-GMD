# Android-Intermediate-GMD
Android Intermediate (23h)

## Notificaciones :

  - Notificaciones (Toast, barra de estados, Dialogos, Snackbar)

## Snackbar
```java
    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
```
## Actionbar
Utilizar el componente Toolbar para crear un Actionbar personalizado
```java
   <android.support.v7.widget.Toolbar
        android:id="@+id/my_toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"
        android:elevation="4dp"
        android:theme="@style/ThemeOverlay.AppCompat.ActionBar" />
```
Agregamos el toolbar para que reemplaza al Actionbar que viene por defecto 

```java
   private View container;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiactionbar);
        ui();
        buildCustomToolbar();
    }

    private void buildCustomToolbar(){
        setSupportActionBar(myToolbar);
    }

    private void ui() {
        container= findViewById(R.id.container);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
    }

```
Para poder agregar items al Actionbar usamos el recurso "menu" en res/menu/my_menu.xml

```java
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <!-- "Mark Favorite", should appear as action button if possible -->
    <item
        android:id="@+id/action_favorite"
        android:icon="@mipmap/ic_favorite_48px"
        android:title="@string/action_favorite"
        app:showAsAction="ifRoom"/>

    <!-- Settings, should always be in the overflow -->
    <item android:id="@+id/action_settings"
        android:title="@string/action_settings"
        app:showAsAction="never"/>

</menu>
```
Acciones de Menu

```java
 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                showMessage("Icono de favoritos");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

```

## FragmentDialog

Para construir un Dialog personalizado , lo primero es crear la vista 
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    tools:context=".fragments.CustomDialogFragment">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:background="@color/yellow"
        android:text="@string/android_app"
        android:gravity="center"
        android:textSize="28sp"
        android:textStyle="bold"
        android:textColor="@color/white"
        android:contentDescription="@string/app_name" />
    <EditText
        android:id="@+id/eteUsername"
        android:inputType="textEmailAddress"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:layout_marginLeft="4dp"
        android:layout_marginRight="4dp"
        android:layout_marginBottom="4dp"
        android:hint="@string/username" />
    <EditText
        android:id="@+id/etePassword"
        android:inputType="textPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="4dp"
        android:layout_marginLeft="4dp"
        android:layout_marginRight="4dp"
        android:layout_marginBottom="16dp"
        android:fontFamily="sans-serif"
        android:hint="@string/password"/>


</LinearLayout>
```

Luego para poder comunicar el dialogo con una actividad o fragment, usamos una interface donde definimos todos lo métodos sean requeridos. En este caso , nos bastaría saber si acepto o cancelo .
```java
public interface CustomDialogListener {

    void onAction(Object object);
    void onDialogPositive(Object object);
    void onDialogNegative(Object object);
}
```
Nuestro DialogFragment quedaría de la siguiente manera 

```java
package com.gmd.lessons.uisample.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.gmd.lessons.uisample.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogFragment extends DialogFragment {


    private static final String TAG = "CustomDialogF";
    private CustomDialogListener mListener;
    private String username;
    private String password;
    private EditText eteUsername,etePassword;

    public CustomDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomDialogListener) {
            mListener = (CustomDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CustomDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View customView= inflater.inflate(R.layout.fragment_custom_dialog,null);
        eteUsername= (EditText) customView.findViewById(R.id.eteUsername);
        etePassword= (EditText) customView.findViewById(R.id.etePassword);

        builder.setView(customView)
                // Add action buttons
                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        if(mListener!=null){
                            if(validateForm()) {
                                String message = String.format("username %s password %s",username,password);
                                Log.v(TAG, message);

                                mListener.onDialogPositive(message);
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       CustomDialogFragment.this.getDialog().cancel();
                        if(mListener!=null){
                            mListener.onDialogNegative(null);
                        }
                    }
                });
        return builder.create();
    }

    private boolean validateForm() {
        username= eteUsername.getText().toString().trim();
        password= etePassword.getText().toString().trim();

        if(username.isEmpty())return false;
        if(password.isEmpty())return false;
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

```
Finalmente, para invocar un dialogo realizamos lo siguiente :

```java

   private void showCustomDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "CustomDialogFragment");

    }

    private void showTransparentDialog() {
        TransparentDialogFragment dialog = new TransparentDialogFragment();
        dialog.show(getSupportFragmentManager(), "TransparentDialogFragment");

    }
    
```

## Referencias 

- Appbar [https://developer.android.com/training/appbar/index.html](https://developer.android.com/training/appbar/index.html)

- Menus [https://developer.android.com/guide/topics/ui/menus.html](https://developer.android.com/guide/topics/ui/menus.html)

- Dialog [https://developer.android.com/guide/topics/ui/dialogs.html](https://developer.android.com/guide/topics/ui/dialogs.html)

- Snackbar [https://material.io/guidelines/components/snackbars-toasts.html](https://material.io/guidelines/components/snackbars-toasts.html)

- UUID [https://android-developers.googleblog.com/2011/03/identifying-app-installations.html](https://android-developers.googleblog.com/2011/03/identifying-app-installations.html)

  
  
