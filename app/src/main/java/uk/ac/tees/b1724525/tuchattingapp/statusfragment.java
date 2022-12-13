package uk.ac.tees.b1724525.tuchattingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class statusfragment extends Fragment {

    private ImageView imageview;
    private static int PICK_IMAGE=123;
    private Uri imagepath;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statusfragment, container, false);

        FloatingActionButton mFab = view.findViewById(R.id.floatingActionButton);
        String camera;
        mFab.setOnClickListener(view1 -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MediaStore.Images.Media.BUCKET_DISPLAY_NAME ))));
        return view;
    }
}
