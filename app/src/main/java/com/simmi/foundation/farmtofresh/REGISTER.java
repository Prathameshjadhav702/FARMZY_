package com.simmi.foundation.farmtofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class REGISTER extends AppCompatActivity {
 String role="Driver";
    EditText phonenumber,pass,confpass,firstname,lastname,adress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RadioGroup radioGroup = findViewById(R.id.Radiogrp);
        Button submit=findViewById(R.id.buttoon);
        phonenumber=findViewById(R.id.phn);
        pass=findViewById(R.id.password2);
        confpass=findViewById(R.id.passc);
        firstname=findViewById(R.id.name);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                String radio1= String.valueOf(radioButton.getText());
                if(radio1.equals("Farmer")){
                    role="Farmer";
                }
                if(radio1.equals("Driver")){
                    role="Driver";
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phnnum=phonenumber.getEditableText().toString();
                String fname=firstname.getEditableText().toString();

                String pass1=pass.getEditableText().toString();
                String pass2=confpass.getEditableText().toString();
                if(phnnum.equals("")){
                    Toast.makeText(REGISTER.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if(fname.equals("")){
                    Toast.makeText(REGISTER.this, "Please enter Firstname", Toast.LENGTH_SHORT).show();
                }
                else if(role.isEmpty()){
                    Toast.makeText(REGISTER.this, "Please select your role", Toast.LENGTH_SHORT).show();
                }
                else if(pass1.equals("")){
                    Toast.makeText(REGISTER.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                else if(pass2.equals(null)){
                    Toast.makeText(REGISTER.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                }
                else if(!pass1.equals(pass2)){
                    Toast.makeText(REGISTER.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }else{
                    if(role.equals("Farmer")) {
                        Intent intent = new Intent(REGISTER.this, FarmerDetails.class);
                        intent.putExtra("name",fname);
                        intent.putExtra("pass",pass1);
                        intent.putExtra("phn",phnnum);
                        intent.putExtra("role",role);
                        startActivity(intent);
                        finish();
                    }else if(role.equals("Driver")){
                        Intent intent = new Intent(REGISTER.this, DriverDetails.class);
                        Toast.makeText(REGISTER.this, "You are a driver", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
        });
    }
}