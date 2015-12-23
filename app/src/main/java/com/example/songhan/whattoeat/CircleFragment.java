package com.example.songhan.whattoeat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.songhan.whattoeat.database.DatabaseAdapter;

/**
 * Created by Song on 2015/12/16.
 */
public class CircleFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "circle_frag";
    private DatabaseAdapter db;
    private static final String ADD_CIRCLE_DIALOG_TAG = "add_circle";

    public static Fragment newInstance(Context context) {
        return new CircleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        db = new DatabaseAdapter(getActivity());
        return inflater.inflate(R.layout.fragment_circle, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.row_circle,
                db.getCircles(),
                new String[] { DatabaseAdapter.CIRCLE_NAME },
                new int[] { R.id.row_circle_name });
        ListView list = (ListView) getActivity().findViewById(R.id.circle_listview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_circle, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_circle:
                AddCircleDialog dialog = new AddCircleDialog();
                dialog.show(getActivity().getFragmentManager(), ADD_CIRCLE_DIALOG_TAG);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("circle_id", id);
        Fragment frag = CircleRestaurantFragment.newInstance(getActivity());
        frag.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(TAG).replace(R.id.content_frame, frag, TAG).commit();
    }
}
