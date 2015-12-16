package com.example.songhan.whattoeat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Song on 2015/12/16.
 */
public class RestaurantFragment extends Fragment {

    public static Fragment newInstance(Context context) {
        return new RestaurantFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        ListView list = (ListView) getActivity().findViewById(R.id.restaurant_listview);

        return view;
    }
}
