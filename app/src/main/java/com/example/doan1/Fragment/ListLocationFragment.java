package com.example.doan1.Fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.doan1.Controller.PagerAdapter;
import com.example.doan1.R;
import com.google.android.material.tabs.TabLayout;

public class ListLocationFragment extends Fragment {
    View view;
    ViewPager pager;
    TabLayout tabLayout;
    public static boolean checkClickItemLocaFav = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listlocation, container, false);

        addTabwithViewPager();

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.text_white));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        return view;
    }

    private void addTabwithViewPager() {
        pager = view.findViewById(R.id.viewpagerLocation);
        pager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        tabLayout = view.findViewById(R.id.tablayoutLocation);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_all_location);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_favorite_choose);
    }

}
