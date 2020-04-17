package com.example.notesandreminders.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.notesandreminders.R;
import com.example.notesandreminders.model.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private FloatingActionButton mFab;
    private FloatingActionButton mClearAll;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String fragment;

    public static Intent getStartIntent(Context context,String fragment)
    {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("fragment",fragment);
        intent.putExtra("bundle",b);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initTabs();

        DbManager.getInstance().initializeDatabase(getApplicationContext());
//        startService(new Intent(this, AlarmService.class));
        Bundle b = this.getIntent().getBundleExtra("bundle");
        if(b!=null)
        {
            fragment = b.getString("fragment");
        }
        if("reminder".equalsIgnoreCase(fragment))
        {
            mViewPager.setCurrentItem(1);
        }
        // Initializing the database


    }

    private void initViews()
    {
        mFab = findViewById(R.id.fab);
        mClearAll = findViewById(R.id.clearMissed);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem()==0)
                {
                  Intent intent = AddActivity.getStartIntent(getApplicationContext(),1);
                  startActivity(intent);
                }
                else
                {
                    Intent intent = AddActivity.getStartIntent(getApplicationContext(),3);
                    startActivity(intent);
                }
            }
        });

        mClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void initTabs()
    {
        NotesAdapter mAdapter = new NotesAdapter(getSupportFragmentManager());
        mAdapter.addFragments(new NotesFragment(),"Notes");
        mAdapter.addFragments(new ReminderFragment(),"Reminders");
        mAdapter.addFragments(new MissedFragment(),"Missed");
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2)
                {
                   mFab.hide();
                   mClearAll.hide();
                }
                else
                {
                   mFab.show();
                   mClearAll.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


     class NotesAdapter extends FragmentPagerAdapter
    {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mTitleList = new ArrayList<>();
        public NotesAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        private void addFragments(Fragment fragment,String title)
        {
            mFragmentList.add(fragment);
            mTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(this, AlarmService.class));

    }
}
