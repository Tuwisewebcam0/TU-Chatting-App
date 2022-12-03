package com.example.tuchattingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    private EditText mnewusername;
    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseFirestore firebaseFirestore;

    private ImageView mgetnewuserimageinimageview;

    private StorageReference storageReference;

    private String ImageURIaccessToken;

    private androidx.appcompat.widget.Toolbar mtoolbarofupdateprofile;
    private ImageButton mbackbuttonofupdateprofile;


   private FirebaseStorage firebaseStorage;

   ProgressBar mprogressbarofupdateprofile;

   private Uri imagepath;

   Intent intent;

   private static int PICK_IMAGE=123;

   android.widget.Button mupdateprofilebutton;

   String newname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mtoolbarofupdateprofile=findViewById(R.id.toolbarofupdateprofile);
        mbackbuttonofupdateprofile=findViewById(R.id.backbuttonofupdateprofile);
        mgetnewuserimageinimageview=findViewById(R.id.getnewuserimageinimageview);
        mprogressbarofupdateprofile=findViewById(R.id.progressbarofupdateprofile);
        mnewusername=findViewById(R.id.getnewusername);
        mupdateprofilebutton=findViewById(R.id.updateprofilebutton);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        intent=getIntent();

        setSupportActionBar(mtoolbarofupdateprofile);

        mbackbuttonofupdateprofile.setOnClickListener(view -> finish());


        mnewusername.setText(intent.getStringExtra("nameofuser"));


        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        mupdateprofilebutton.setOnClickListener(view -> {

            newname=mnewusername.getText().toString();
            if(newname.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Name is Empty", Toast.LENGTH_SHORT).show();
            }
            else if(imagepath!=null)
            {
                mprogressbarofupdateprofile.setVisibility(View.VISIBLE);
                userprofile muserprofile = new userprofile(newname,firebaseAuth.getUid());
                databaseReference.setValue(muserprofile);

                updateimageStorage();

                Toast.makeText(getApplicationContext(), "Updated",Toast.LENGTH_SHORT).show();
                mprogressbarofupdateprofile.setVisibility(View.VISIBLE);
                Intent intent=new Intent(UpdateProfile.this,chatActivity.class);
                startActivity(intent);
                finish();


            }
            else
            {
                mprogressbarofupdateprofile.setVisibility(View.VISIBLE);
                userprofile muserprofile = new userprofile(newname,firebaseAuth.getUid());
                databaseReference.setValue(muserprofile);
                updatenameoncloudfirestore();
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();

                mprogressbarofupdateprofile.setVisibility(View.VISIBLE);
                Intent intent=new Intent(UpdateProfile.this,chatActivity.class);
                startActivity(intent);
                finish();









            }
        });


        mgetnewuserimageinimageview.setOnClickListener(view -> {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        storageReference=firebaseStorage.getReference();
        storageReference.child("Image").child(firebaseAuth.getUid()).child("Profile Pic").getDownloadUrl().addOnSuccessListener(uri -> {
            ImageURIaccessToken=uri.toString();
            Picasso.get().load(uri).into(mgetnewuserimageinimageview);
        });



    }

    private void updatenameoncloudfirestore() {

        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        Map<String , Object> userdata=new HashMap<>();
        userdata.put("name", newname);
        userdata.put("image",ImageURIaccessToken);
        userdata.put("uid",firebaseAuth.getUid());
        userdata.put("status","Online");



        documentReference.set(userdata).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show());




    }

    private void updateimageStorage() {

        StorageReference imageref=storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic");

        //image compression

        Bitmap bitmap=null;
        try {
            bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25,byteArrayOutputStream);
        byte[] data=byteArrayOutputStream.toByteArray();

        ///putting image to storage

        UploadTask uploadTask=imageref.putBytes(data);

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            imageref.getDownloadUrl().addOnSuccessListener(uri -> {
                ImageURIaccessToken=uri.toString();
                Toast.makeText(getApplicationContext(),"URI get success",Toast.LENGTH_SHORT).show();
                updatenameoncloudfirestore();
            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"URI get Failed",Toast.LENGTH_SHORT).show());
            Toast.makeText(getApplicationContext(),"Image is Updated",Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Image Not Updated", Toast.LENGTH_SHORT).show());




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            imagepath = data.getData();
            mgetnewuserimageinimageview.setImageURI(imagepath);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStop(){
        super.onStop();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status", "offline").addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"User is Offline",Toast.LENGTH_SHORT).show());



    }

    @Override
    protected void onStart(){
        super.onStart();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status", "Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"User is Online",Toast.LENGTH_SHORT).show();
            }
        });
    }

}