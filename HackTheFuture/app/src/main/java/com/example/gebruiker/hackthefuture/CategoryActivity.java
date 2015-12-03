package com.example.gebruiker.hackthefuture;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.gebruiker.hackthefuture.REST.services.CategoryManager;
import com.example.gebruiker.hackthefuture.models.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    private CategoryManager categoryManager;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabs;

    private int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);

        categoryManager = CategoryManager.getInstance(getApplicationContext());
        
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new FetchCategoriesTask().execute();
    }

    private void setupViewPager(List<Category> categories) {

        Adapter adapter = new Adapter(getSupportFragmentManager());
        //adapter.addFragment(new RecipeListFragment(), getString(R.string.category_cooking));
        for(Category cat : categories){
            adapter.addFragment(new CategoryFragment(cat), cat.getName());
        }
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Update position when a new Tab / Page is selected
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabs.setupWithViewPager(viewPager);
    }

    /**
     * An adapter.
     */
    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    private class FetchCategoriesTask extends AsyncTask<Void, List<String>, Boolean>{

        private List<Category> categories = new ArrayList<>();

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                categories = categoryManager.getCategories();
                return true;
            }catch(final Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                setupViewPager(categories);
            }
        }
    }
}
