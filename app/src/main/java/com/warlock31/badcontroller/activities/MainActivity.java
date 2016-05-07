package com.warlock31.badcontroller.activities;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.warlock31.badcontroller.fragments.FragmentHome;
import com.warlock31.badcontroller.fragments.FragmentPs;
import com.warlock31.badcontroller.fragments.FragmentXbox;
import com.warlock31.badcontroller.fragments.MyFragment;
import com.warlock31.badcontroller.fragments.NavigationDrawerFragment;
import com.warlock31.badcontroller.R;
import com.warlock31.badcontroller.services.MyService;
import com.warlock31.badcontroller.views.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    public static final int BADCONTROLLER_HOME=0;
    public static final int BADCONTROLLER_XBOX=1;
    public static final int BADCONTROLLER_PS=2;

    private static int jobId = 100;


    private JobScheduler mJobScheduler;

    ComponentName mServiceComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceComponent = new ComponentName(this,MyService.class);
        mJobScheduler = (JobScheduler) getSystemService( Context.JOB_SCHEDULER_SERVICE );
        constructJob();

                toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);
        mTabs.setDistributeEvenly(true);


        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTabs.setViewPager(mPager);


    }


    private void constructJob(){
        JobInfo.Builder builder = new JobInfo.Builder(jobId,mServiceComponent);

//        PersistableBundle persistableBundle = new PersistableBundle();

        builder.setPeriodic(60000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);

        mJobScheduler.schedule(builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.categories) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    class MyPageAdapter extends FragmentPagerAdapter {

        int icons[] = {R.drawable.home,R.drawable.xbox,R.drawable.ps_logo};
        String[] tabs = getResources().getStringArray(R.array.tabs);

        public MyPageAdapter(FragmentManager fm) {

            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
           Fragment fragment=null;
            switch (position){
                case BADCONTROLLER_HOME:
                    fragment = new FragmentHome().newInstance("","");
                    break;
                case BADCONTROLLER_XBOX:
                    fragment = new FragmentXbox().newInstance("","");
                    break;
                case BADCONTROLLER_PS:
                    fragment = new FragmentPs().newInstance("","");
                    break;
                default:
                    break;

            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            if (drawable != null) {
                drawable.setBounds(0,0,72,72);
            }
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }





}
