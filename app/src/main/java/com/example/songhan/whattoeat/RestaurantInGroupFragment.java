package com.example.songhan.whattoeat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.songhan.whattoeat.database.DatabaseAdapter;

/**
 * Created by Song on 2015/12/16.
 */
public class RestaurantInGroupFragment extends Fragment {

    private DatabaseAdapter db;
    public static final String TAG = "restaurant_group";
    private ListView list;
    private SimpleCursorAdapter adapter;

    public static Fragment newInstance(Context context) {
        return new RestaurantInGroupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        db = new DatabaseAdapter(getActivity());
        return inflater.inflate(R.layout.fragment_restaurants_in_group, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up group name
        String groupName = getArguments().getString(GroupsFragment.SELECTED_GROUP_NAME, DatabaseAdapter.DEFAULT_CIRCLE);
        TextView textView = (TextView) getActivity().findViewById(R.id.fragment_restaurants_in_group_textview);
        textView.setText("Groups > " + groupName);

        // Set up listview
        long groupId = getArguments().getLong(GroupsFragment.SELECTED_GROUP_ID, 1);
        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.row_restaurant,
                db.getRestaurantsByCircle(groupId),
                new String[] { DatabaseAdapter.RESTAURANT_NAME },
                new int[] { R.id.row_restaurant_name });
        list = (ListView) getActivity().findViewById(R.id.circle_restaurant_listview);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        // set up contexual toolbar
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_remove, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.remove_item:
                        long groupId = getArguments().getLong(GroupsFragment.SELECTED_GROUP_ID, 1);
                        long[] ids = list.getCheckedItemIds();
                        db.unlinkRestaurantFromGroup(ids, groupId);
                        adapter.changeCursor(db.getRestaurantsByCircle(groupId));
                        adapter.notifyDataSetChanged();
                        return true;
                    default:
                        return true;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Fragment frag = AddRestaurantToGroupFragment.newInstance(getActivity());
                frag.setArguments(getArguments());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame, frag, AddRestaurantToGroupFragment.TAG).addToBackStack(TAG).commit();
                return true;
            default:
                return true;
        }
    }
}
