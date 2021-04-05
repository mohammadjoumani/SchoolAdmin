package com.example.schooladmin.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.schooladmin.R;
import com.example.schooladmin.model.ClassSchool;

import java.util.List;

public class ClassSpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<ClassSchool> classes;

    public ClassSpinnerAdapter(List<ClassSchool> classes, Context context) {
        this.context = context;
        this.classes = classes;
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Object getItem(int i) {
        return classes.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        view = layoutInflater.inflate( R.layout.item_spinner_classes, null );
        TextView txtSpinnerClassesName = view.findViewById( R.id.txtSpinnerClassesName );
        txtSpinnerClassesName.setText( classes.get( i ).getName() );
        return view;
    }

    public void setData(List<ClassSchool> classes) {
        this.classes = classes;
        notifyDataSetChanged();
    }
}
