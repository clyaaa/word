package com.example.cly.word;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class first_main_fragment extends Fragment implements View.OnClickListener{
    ViewPager vp;
    TextView first_first;
    TextView first_second;
    TextView first_three;
    //TextView first_four;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view = inflater.inflate( R.layout.first_main, container, false );
        vp = (ViewPager) view.findViewById(R.id.vp);
        first_first=(TextView) view.findViewById( R.id.first_first );
        first_second=(TextView) view.findViewById( R.id.first_second );
        first_three=(TextView) view.findViewById( R.id.first_three );
        //first_four=(TextView) view.findViewById( R.id.first_four );
        initias();
        class MyAdater extends FragmentPagerAdapter {
            List<Fragment> fragments;

            public MyAdater(FragmentManager fm, List<Fragment> fragments) {
                super(fm);
                this.fragments = fragments;
                // TODO Auto-generated constructor stub
            }

            @Override
            public Fragment getItem(int position) {
                // TODO Auto-generated method stub
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragments.size();
            }

        }

        List<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new first_first_fragment());
        fragments.add(new first_second_fragment());
        fragments.add(new first_three_fragment());

        PagerAdapter adapter = new MyAdater(getChildFragmentManager(),
                fragments);
        vp.setAdapter(adapter);
        vp.setCurrentItem( 0 );
        vp.setOffscreenPageLimit( 10 );
        vp.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int currentItem=vp.getCurrentItem();
                switch(currentItem){
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        } );
        return view;
    }

    public void initias(){
        first_first.setOnClickListener( this );
        first_second.setOnClickListener( this );
        first_three.setOnClickListener( this );
        //first_four.setOnClickListener( this );
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.first_first:
                vp.setCurrentItem( 0 );
                break;
            case R.id.first_second:
                vp.setCurrentItem( 1 );
                break;
            case R.id.first_three:
                vp.setCurrentItem( 2 );
                break;
            //case R.id.first_four:
                //vp.setCurrentItem( 3 );
                //break;
            default:
                break;
        }
    }
}
