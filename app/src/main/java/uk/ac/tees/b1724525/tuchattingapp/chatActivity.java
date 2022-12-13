package uk.ac.tees.b1724525.tuchattingapp;

import static uk.ac.tees.b1724525.tuchattingapp.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import uk.ac.tees.b1724525.tuchattingapp.R;

public class  chatActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem mchat,mstatus,mcommunity,mcall;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    androidx.appcompat.widget.Toolbar mtoolbar;

    FirebaseAuth firebaseAuth;


    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_chat);

        tabLayout=findViewById(id.include);
        mchat=findViewById(id.chat);
        mstatus=findViewById(id.status);
        mcommunity=findViewById(id.room);
        mcall=findViewById(id.calls);
        viewPager=findViewById(id.fragment_container);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        mtoolbar=findViewById(id.toolbar);
        setSupportActionBar(mtoolbar);


        Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_more_vert_24);
        mtoolbar.setOverflowIcon(drawable);


        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2 || tab.getPosition()==3)
                {
                    pagerAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       switch (item.getItemId())
       {
           case id.profile:
               Intent intent=new Intent( chatActivity.this,ProfileActivity.class);
               startActivity(intent);
               break;
           case id.Starred_messages:
               Toast.makeText(getApplicationContext(),"starred messages",Toast.LENGTH_SHORT).show();
               break;
           case id.Privacy:
               Toast.makeText(getApplicationContext(),"privacy",Toast.LENGTH_SHORT).show();
               break;
           case id.settings:
               Toast.makeText(getApplicationContext(),"settings",Toast.LENGTH_SHORT).show();
               break;
       }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    protected void onStop(){
        super.onStop();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseAuth.getUid()));
        documentReference.update("status", "offline").addOnSuccessListener(unused -> {
            Toast.makeText(getApplicationContext(), "Now User is Offline", Toast.LENGTH_SHORT).show();
        });



    }

    @Override
    protected void onStart(){
        super.onStart();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseAuth.getUid()));
        documentReference.update("status", "Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(chatActivity.this.getApplicationContext(), "Now User is Online", Toast.LENGTH_SHORT).show();
            }
        });
    }

}