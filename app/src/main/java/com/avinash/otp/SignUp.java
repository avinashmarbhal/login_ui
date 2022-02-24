package com.avinash.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        regName = findViewById(R.id.reg_Name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_Email);
        regPhoneNo = findViewById(R.id.reg_PhoneNumber);
        regPassword = findViewById(R.id.reg_Password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateName() | !validateEmail() | !validatePassword() | !validatePhoneNo() | validateUsername()){
                    return;
                }

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");
                //get values
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name,username,email,phoneNo,password);
                reference.child(phoneNo).setValue(helperClass);


            }
        });

    }



    private Boolean validateName(){
        String val1 = regName.getEditText().getText().toString();

        if (val1.isEmpty()){
            regName.setError("Field can't be empty!");
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateUsername(){
        String val2 = regUsername.getEditText().getText().toString();
        String noWhiteSpace="\\A\\w{1,20}\\z";

        if (val2.isEmpty()){
            regUsername.setError("Field can't be empty!");
            return false;
        }else if(val2.length() >= 15){

            regUsername.setError("Username too long");
            return false;

        }else if(!val2.matches(noWhiteSpace)){

            regUsername.setError("white spaces are not allowed");
            return false;

        }else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail(){
        String val3 = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val3.isEmpty()){
            regEmail.setError("Field can't be empty!");
            return false;
        }else if (!val3.matches(emailPattern)){
            regEmail.setError("Invalid mail !");
            return false;

        }else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePhoneNo(){
        String val4 = regPhoneNo.getEditText().getText().toString();

        if (val4.isEmpty()){
            regPhoneNo.setError("Field can't be empty!");
            return false;
        }else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val5 = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val5.isEmpty()){
            regPassword.setError("Field can't be empty!");
            return false;
        }else if (!val5.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;

        }else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }

    }

}