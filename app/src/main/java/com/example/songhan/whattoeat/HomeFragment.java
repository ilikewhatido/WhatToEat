package com.example.songhan.whattoeat;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.songhan.whattoeat.database.DatabaseAdapter;
import com.example.songhan.whattoeat.widget.WheelView;
import com.example.songhan.whattoeat.widget.adapters.AbstractWheelTextAdapter;
import com.example.songhan.whattoeat.widget.adapters.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Song on 2015/12/16.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = "home";
    private static final int VISIBLE_ITEMS = 3;

    private DatabaseAdapter db;
    private SimpleCursorAdapter spinnerAdapter;
    private RestaurantShakerAdapter shakerAdapter;
    private WheelView shaker;

    public static Fragment newInstance(Context context) {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DatabaseAdapter(getActivity());
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up wheel picker
        shakerAdapter = new RestaurantShakerAdapter(getActivity(), db.getRestaurants());
        shaker = (WheelView) getActivity().findViewById(R.id.shaker);
        shaker.setVisibleItems(VISIBLE_ITEMS);
        shaker.setViewAdapter(shakerAdapter);
        shaker.setCyclic(true);

        // Set up spinner
        final Spinner spinner = (Spinner) getActivity().findViewById(R.id.shaker_selector);
        spinnerAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                db.getCircles(),
                new String[] { DatabaseAdapter.CIRCLE_NAME },
                new int[]{android.R.id.text1},
                0);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        Button mix = (Button) getActivity().findViewById(R.id.button_shake);
        mix.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        shakerAdapter.close();
        shakerAdapter = new RestaurantShakerAdapter(getActivity(), db.getRestaurantsByCircle(id));
        shaker.setViewAdapter(shakerAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //DO NOTHING
    }

    @Override
    public void onClick(View v) {
        final Random rand = new Random();
        int i = (rand.nextInt(shakerAdapter.getItemsCount() * 10) + 1);
        Log.e("wawawa", ""+i);
        shaker.scroll(i , 3000);
    }

    private class RestaurantShakerAdapter extends AbstractWheelTextAdapter {

        private Cursor cursor;

        public RestaurantShakerAdapter(Context context, Cursor cursor) {
            super(context, R.layout.row_shaker_restaurant, R.id.shaker_row_restaurant_name);
            this.cursor = cursor;
        }

        @Override
        public int getItemsCount() {
            return cursor.getCount();
        }

        @Override
        protected CharSequence getItemText(int index) {
            if(index >= 0 && index < cursor.getCount()) {
                cursor.moveToPosition(index);
                return cursor.getString(cursor.getColumnIndex(DatabaseAdapter.RESTAURANT_NAME));
            }
            return null;
        }

        public void close() {
            this.cursor.close();
        }
    }
}
