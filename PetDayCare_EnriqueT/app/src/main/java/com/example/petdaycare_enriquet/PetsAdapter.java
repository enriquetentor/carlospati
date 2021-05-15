package com.example.petdaycare_enriquet;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class PetsAdapter extends ArrayAdapter<Pets> {

    PetsAdapter(@NonNull Context context, int resource, ArrayList<Pets> words) {
        super(context, resource, words);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View list_item = convertView;

        if (list_item == null) {
            list_item = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, parent, false);
        }

        Pets currentPet = getItem(position);

        TextView Name = list_item.findViewById(R.id.Name);
        assert currentPet != null;
        Name.setText(currentPet.getName());
        TextView Breed = list_item.findViewById(R.id.Breed);
        Breed.setText(currentPet.getBreed());
        list_item.setPadding(15, 15, 15, 15);

        return list_item;
    }
}
