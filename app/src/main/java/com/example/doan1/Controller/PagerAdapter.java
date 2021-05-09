package com.example.doan1.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doan1.Fragment.AllLocationFragment;
import com.example.doan1.Fragment.FavoriteLocationFragment;
import com.example.doan1.SplashActivity;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new AllLocationFragment();
                break;
            case 1:
                frag = new FavoriteLocationFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                if(SplashActivity.temp.equals("vi")) title = "Tất cả";
                else title = "All";
                break;
            case 1:
                if(SplashActivity.temp.equals("vi")) title = "Yêu thích";
                else title = "Favorite";
                break;
        }
        return title;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}
