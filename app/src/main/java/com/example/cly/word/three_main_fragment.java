package com.example.cly.word;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class three_main_fragment extends Fragment implements View.OnClickListener{

    TextView collect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.three_main, container, false );
        collect=(TextView) view.findViewById( R.id.collect );
        collect.setOnClickListener( this );
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.collect:
                CollectActivity.actionStart(getContext());
                break;
            default:
                break;
        }
    }
}
