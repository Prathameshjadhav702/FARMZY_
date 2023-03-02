package com.simmi.foundation.farmtofresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LOGIN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button sub, reg;
        EditText phn, pass;
        sub=findViewById(R.id.submit);
        phn=findViewById(R.id.phn);
        pass=findViewById(R.id.password);
        reg=findViewById(R.id.neww);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LOGIN.this, REGISTER.class);
                startActivity(intent);
                finish();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=pass.getEditableText().toString();
                String phone=phn.getEditableText().toString();

                                Intent intent = new Intent(LOGIN.this, FramHomescren.class);
                                startActivity(intent);
                                finish();


            }
        });
    }
}