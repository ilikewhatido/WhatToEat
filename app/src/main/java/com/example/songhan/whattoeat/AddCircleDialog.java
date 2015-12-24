package com.example.songhan.whattoeat;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.songhan.whattoeat.database.DatabaseAdapter;

/**
 * Created by Song on 2015/12/2.
 */
public class AddCircleDialog extends DialogFragment implements View.OnClickListener {

    private Button ok, cancel;
    private EditText name;
    public static final String TAG = "add_group_dialog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_circle_dialog, null);
        ok = (Button) view.findViewById(R.id.add_circle_ok);
        ok.setOnClickListener(this);
        cancel = (Button) view.findViewById(R.id.add_circle_cancel);
        cancel.setOnClickListener(this);
        name = (EditText) view.findViewById(R.id.add_circle_name);
        setCancelable(true);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.add_circle_ok) {
            DatabaseAdapter db = new DatabaseAdapter(getActivity());
            db.addCircle(name.getText().toString());
            dismiss();
        } else if (id == R.id.add_circle_cancel) {
            dismiss();
        }
    }
}
