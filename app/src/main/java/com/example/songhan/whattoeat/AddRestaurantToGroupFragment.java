package com.example.songhan.whattoeat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;

import com.example.songhan.whattoeat.database.DatabaseAdapter;

/**
 * Created by Song on 2015/12/16.
 */
public class AddRestaurantToGroupFragment extends Fragment {

    private DatabaseAdapter db;
    public static final String TAG = "add_restaurant_to_group";
    private SimpleCursorAdapter adapter;

    public static Fragment newInstance(Context context) {
        return new AddRestaurantToGroupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        db = new DatabaseAdapter(getActivity());
        return inflater.inflate(R.layout.fragment_add_restaurant_to_circle, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_empty, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        long groupId = getArguments().getLong(GroupsFragment.SELECTED_GROUP_ID, 1);
        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.row_restaurant,
                db.getRestaurantsNotInCircle(groupId),
                new String[] { DatabaseAdapter.RESTAURANT_NAME },
                new int[] { R.id.row_restaurant_name });
        ListView list = (ListView) getActivity().findViewById(R.id.add_restaurant_to_group_listview);
        list.setAdapter(adapter);

        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Log.e("wawawa", "onCreateActionMode");
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_add, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_item:
                        long groupId = getArguments().getLong(GroupsFragment.SELECTED_GROUP_ID, 1);

                        Log.e("wawawa", "group id = " + groupId);

                        ListView list = (ListView) getActivity().findViewById(R.id.add_restaurant_to_group_listview);
                        for(long restaurantId : list.getCheckedItemIds()) {
                            db.linkRestaurantToGroup(restaurantId, groupId);
                        }
                        mode.finish();
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                        break;
                    default:
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }
        });
    }
}
