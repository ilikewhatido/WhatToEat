package com.example.songhan.whattoeat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.songhan.whattoeat.database.DatabaseAdapter;

/**
 * Created by Song on 2015/12/16.
 */
public class RestaurantFragment extends Fragment {

    private DatabaseAdapter db;
    public static final String TAG = "restaurant";

    public static Fragment newInstance(Context context) {
        return new RestaurantFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DatabaseAdapter(getActivity());
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.row_restaurant,
                db.getRestaurants(),
                new String[] { DatabaseAdapter.RESTAURANT_NAME },
                new int[] { R.id.row_restaurant_name });
        ListView list = (ListView) getActivity().findViewById(R.id.restaurant_listview);
        list.setAdapter(adapter);
    }
}
