package com.example.beckytang.finalproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.beckytang.finalproject.model.Album;
import com.example.beckytang.finalproject.model.AlbumList;
import com.example.beckytang.finalproject.model.Photo;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public ArrayList<Photo> galleryData = new ArrayList<>();
    int pos;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        galleryData = getIntent().getParcelableArrayListExtra(AlbumActivity.TAG_DATA);
        pos = getIntent().getIntExtra(AlbumActivity.TAG_POS, 0);
        String albumName = getIntent().getStringExtra(AlbumActivity.TAG_ALBUM_NAME);

        setTitle(albumName);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), galleryData);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMG_TITLE = "image_title";
        private static final String ARG_IMG_URL = "image_url";

        String name, url;
        int pos;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber, String name, String url) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_IMG_TITLE, name);
            args.putString(ARG_IMG_URL, url);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            this.pos = args.getInt(ARG_SECTION_NUMBER);
            this.name = args.getString(ARG_IMG_TITLE);
            this.url = args.getString(ARG_IMG_URL);
        }

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            final ImageView imageView = (ImageView) rootView.findViewById(R.id.ivDetailImage);

            Glide.with(getActivity()).load(url).thumbnail(0.1f).into(imageView);

            return rootView;
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public ArrayList<Photo> galleryData = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Photo> galleryData) {
            super(fm);
            this.galleryData = galleryData;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position, galleryData.get(position).getPhotoName(), galleryData.get(position).getUrl());
        }

        @Override
        public int getCount() {
            return galleryData.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return galleryData.get(position).getPhotoName();
        }
    }

}
