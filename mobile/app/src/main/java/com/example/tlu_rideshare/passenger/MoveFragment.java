package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.util.ArrayList;
import java.util.List;

public class MoveFragment extends Fragment {
    public MoveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout chứa nội dung trang chủ

        return inflater.inflate(R.layout.move_fragment_passenger_layout, container, false);
    }
}
