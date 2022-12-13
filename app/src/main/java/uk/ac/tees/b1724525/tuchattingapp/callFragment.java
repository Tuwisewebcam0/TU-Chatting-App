package uk.ac.tees.b1724525.tuchattingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uk.ac.tees.b1724525.tuchattingapp.R;

public class callFragment extends Fragment {

    String phone = "1111-111-111";

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.callfragment, container, false);

        FloatingActionButton mFab = view.findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(view1 -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel://" + phone))));
        return view;
    }
}