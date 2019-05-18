package com.example.cly.word;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsContentFragment extends Fragment {
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        view=inflater.inflate( R.layout.news_content_frag,container,false );
        return view;
    }
    public void refresh(String newstitle,String newscontent,String newstime){
        View visibilityLayout=view.findViewById( R.id.visibility_layout );
        visibilityLayout.setVisibility( View.VISIBLE );
        TextView newstitleText=view.findViewById( R.id.news_title);
        TextView newscontentText=view.findViewById( R.id.news_content );
        TextView newstimeText=view.findViewById( R.id.news_time );
        newstitleText.setText( newstitle );
        newscontentText.setText( newscontent );
        newstimeText.setText( newstime );
    }
}
