package com.example.songhan.whattoeat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.songhan.whattoeat.database.DatabaseAdapter;
import com.example.songhan.whattoeat.widget.WheelView;
import com.example.songhan.whattoeat.widget.adapters.AbstractWheelTextAdapter;

/**
 * Created by Song on 2015/12/16.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = "home";
    private static final int VISIBLE_ITEMS = 5;
    public static Fragment newInstance(Context context) {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final WheelView shaker = (WheelView) getActivity().findViewById(R.id.shaker);
        shaker.setVisibleItems(VISIBLE_ITEMS);
        shaker.setViewAdapter(new RestaurantShakerAdapter(getActivity()));
    }

    private class RestaurantShakerAdapter extends AbstractWheelTextAdapter {

        private String restaurants[] = new String[] {"USA", "Canada", "Ukraine", "France"};

        protected RestaurantShakerAdapter(Context context) {
            super(context, R.layout.shaker_row_restaurant, NO_RESOURCE);
            setItemTextResource(R.id.shaker_row_restaurant_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            return super.getItem(index, cachedView, parent);
        }

        @Override
        public int getItemsCount() {
            return restaurants.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return restaurants[index];
        }
    }
}
