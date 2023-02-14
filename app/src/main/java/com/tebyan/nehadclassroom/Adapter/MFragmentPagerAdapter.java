package com.tebyan.nehadclassroom.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MFragmentPagerAdapter extends FragmentStateAdapter {

    public List<Fragment> fragments;
    public List<String> names;

    public MFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void SetFragments(Fragment[] fragments, String[] names) {

        this.fragments = Arrays.asList(fragments);
        this.names = Arrays.asList(names);
    }

    public void AddFragment(Fragment fragment, String name) {
        fragments.add(fragment);
        names.add(name);
    }

    public Fragment GetFragment(int index) {
        return fragments.get(index);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);

    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
