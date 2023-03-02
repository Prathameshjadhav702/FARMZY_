package com.simmi.foundation.farmtofresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.FrameMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class FarmerDetails extends AppCompatActivity {
    private GoogleMap mMap;
    private Marker mMarker;
    double latitude ;
    double longitude;
    Uri downloadUri;
    Button sub;
    ImageView Image;
    EditText Framname;
    int SELECT_PICTURE=200;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_details);
        Framname=findViewById(R.id.phn3);
        String name = getIntent().getStringExtra("name");
        String Phone = getIntent().getStringExtra("phn");
        String pass = getIntent().getStringExtra("pass");
        String ROLE = getIntent().getStringExtra("role");

Image=findViewById(R.id.shapeableImageView);
sub=findViewById(R.id.button2);

sub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String farmername= Framname.getEditableText().toString();
         if(farmername.equals(null)|| farmername.equals("")){
            Toast.makeText(FarmerDetails.this, "Upload your Farm Name", Toast.LENGTH_SHORT).show();
        }
else {
            DatabaseReference name1= FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("Name");
            name1.setValue(name);
            DatabaseReference ph= FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("Phone");
            ph.setValue(Phone);
            DatabaseReference passs= FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("Password");
            passs.setValue(pass);
            DatabaseReference farmname= FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("Farmname");
            farmname.setValue(farmername);
            DatabaseReference role= FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("Role");
            role.setValue(ROLE);
            DatabaseReference lat= FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("Latitude");
            lat.setValue(String.valueOf(latitude));
            DatabaseReference longi= FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("Longitude");
            longi.setValue(String.valueOf(longitude));

            Intent intent = new Intent(FarmerDetails.this, otp_authentication.class);
            startActivity(intent);
            finish();
        }
    }
});
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
    calllocation();
    Image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imageChooser();
        }
    });
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    Glide.with(FarmerDetails.this)
                            .load(selectedImageUri)
                            .into(Image);
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference().child("images/" + UUID.randomUUID().toString());
                    UploadTask uploadTask = storageRef.putFile(selectedImageUri);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return storageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                 downloadUri = task.getResult();
                                Toast.makeText(FarmerDetails.this, "Image uploaded sucessfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            //progressDialog.dismiss();
                            Toast.makeText(FarmerDetails.this,
                                            "Failed to upload image" + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
                }
            }
        }
    }

    private void calllocation() {
mapFragment.getMapAsync(new OnMapReadyCallback() {
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at a specific location with the draggable property set to true
        LatLng latLng = new LatLng(19.01782 , 72.84955);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        mMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Drag me").draggable(true));

          // Set up the marker's drag listener
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                // Do something when the marker drag starts
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // Do something while the marker is being dragged
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // Get the updated location when the marker drag ends
                LatLng position = marker.getPosition();
                 latitude = position.latitude;
                 longitude = position.longitude;
                // Log.d("MapsActivity", "Marker location: " + latitude + ", " + longitude);

            }
        });

    }
});
    }
}


