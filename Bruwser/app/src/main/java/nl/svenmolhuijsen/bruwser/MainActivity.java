package nl.svenmolhuijsen.bruwser;

import android.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private TabLayout tabLayout;
    public ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        adapter= new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.addFragment(new TabFragment());

    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<TabFragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public TabFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(TabFragment fragment) {
            mFragmentList.add(fragment);
            adapter.notifyDataSetChanged();
            tabLayout.setupWithViewPager(viewPager);
        }

        public void removeFragment(int position) {
            getItem(position).resetFragment();
            mFragmentList.remove(position);
            adapter.notifyDataSetChanged();
            tabLayout.setupWithViewPager(viewPager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Integer.toString(position);
        }

        public int getItemPosition(Object object) {
            if (viewPager.getCurrentItem() == 0){
                return POSITION_NONE;
            }
            return POSITION_UNCHANGED;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0,0,0,"New Tab");
        menu.add(0,1,1,"Close Tab");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case 0://NEW TAB
                adapter.addFragment(new TabFragment());

                break;
            case 1://CLOSE TAB
                int position = viewPager.getCurrentItem();
                Log.e("currentItem", Integer.toString(viewPager.getCurrentItem()));
                adapter.removeFragment(position);

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
