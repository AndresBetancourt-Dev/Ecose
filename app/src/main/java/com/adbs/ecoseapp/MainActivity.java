package com.adbs.ecoseapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adbs.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

   Button loginButton, register, cancelButton;
   Snackbar snackbar;
   TextView registerButton, forgotPasword;
   FirebaseAuth auth;
   FirebaseDatabase firebaseDatabase;
   ConstraintLayout mainLayout;
   DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (ConstraintLayout)findViewById(R.id.rootLayout);

        //Init Firebase components
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");

        /*Link Objects with it's XML components*/
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (TextView)findViewById(R.id.registerButton);
        forgotPasword = (TextView)findViewById(R.id.forgot_password);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        final TextInputEditText email = findViewById(R.id.loginUser);
        final TextInputEditText password = findViewById(R.id.loginPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    snackbar = Snackbar.make(mainLayout,"No ingresaste tu e-mail",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }


                if(TextUtils.isEmpty(password.getText().toString())){
                    snackbar = Snackbar.make(mainLayout,"No ingresaste tu contraseña",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }

                if((password.getText().toString().length())<6){
                    snackbar = Snackbar.make(mainLayout,"La contraseña debe ser de almenos de 6 caracteres",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(MainActivity.this,Menu.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        snackbar = Snackbar.make(mainLayout,"Este usuario no se encuentra en el sistema",Snackbar.LENGTH_SHORT);
                        snackbar = customizeSnackBar(snackbar);
                        snackbar.show();
                    }
                });
            }
        });

    }



    public void showRegisterDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = LayoutInflater.from(this);
        View registerLayout = inflater.inflate(R.layout.register_layout,null);

        final TextInputEditText email = registerLayout.findViewById(R.id.registerUser);
        final TextInputEditText name = registerLayout.findViewById(R.id.registerName);
        final TextInputEditText phone = registerLayout.findViewById(R.id.registerPhone);
        final TextInputEditText password = registerLayout.findViewById(R.id.registerPassword);

        register = registerLayout.findViewById(R.id.registerDone);
        cancelButton = registerLayout.findViewById(R.id.registerCancel);

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                dialog.dismiss();



                if(TextUtils.isEmpty(email.getText().toString())){
                    snackbar = Snackbar.make(mainLayout,"No registraste tu e-mail",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }

                if(TextUtils.isEmpty(name.getText().toString())){
                    snackbar = Snackbar.make(mainLayout,"No registraste tu nombre",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }

                if(TextUtils.isEmpty(phone.getText().toString())){
                    snackbar = Snackbar.make(mainLayout,"No registraste tu teléfono",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }

                if(TextUtils.isEmpty(password.getText().toString())){
                    snackbar = Snackbar.make(mainLayout,"No registraste tu contraseña",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }

                if((password.getText().toString().length())<6){
                    snackbar = Snackbar.make(mainLayout,"La contraseña debe ser de almenos de 6 caracteres",Snackbar.LENGTH_SHORT);
                    snackbar = customizeSnackBar(snackbar);
                    snackbar.show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setName(name.getText().toString());
                        user.setPhone(phone.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setPoints(user.getINITIAL_POINTS());

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                snackbar = Snackbar.make(mainLayout,"¡Registro Exitoso!",Snackbar.LENGTH_SHORT);
                                snackbar = customizeSnackBar(snackbar);
                                snackbar.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                snackbar = Snackbar.make(mainLayout,"¡Registro Fallido! "+e.getMessage(),Snackbar.LENGTH_SHORT);
                                snackbar = customizeSnackBar(snackbar);
                                snackbar.show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        snackbar = Snackbar.make(mainLayout,"¡Registro Fallido! "+e.getMessage(),Snackbar.LENGTH_SHORT);
                        snackbar = customizeSnackBar(snackbar);
                        snackbar.show();
                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                return;
            }
        });

        dialog.setView(registerLayout);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Snackbar customizeSnackBar(Snackbar snackbar){
        View snackbarView = snackbar.getView();
        snackbarView.setBackground(getResources().getDrawable(R.drawable.snackbar_bg));
        TextView textView = (TextView)snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        Typeface font = getResources().getFont(R.font.poppins_medium);
        textView.setTypeface(font);
        textView.setTextColor(Color.parseColor("#00BABA"));
        return snackbar;
    }
}
