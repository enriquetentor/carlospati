package com.example.petdaycare_enriquet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.petdaycare_enriquet.Data.PetDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pets> Mascotas = new ArrayList<>();
    ListView List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List = findViewById(R.id.PetsList);
        List.setOnItemClickListener(PetsListener);

        //Crear la BD
        PetDBHelper pDbHelper = new PetDBHelper(this);
        SQLiteDatabase db = pDbHelper.getReadableDatabase();

        //Llamar a la consulta de datos
        ArrayList<Pets> myPetsArray;
        myPetsArray = pDbHelper.displayDatabaseBaseInfo();

        for (Pets pets : myPetsArray) {
            Mascotas.add(new Pets(pets.getName(), pets.getBreed()));
        }

        PetsAdapter wordsAdapter = new PetsAdapter(this, 0, Mascotas);
        List.setAdapter(wordsAdapter);
    }

    AdapterView.OnItemClickListener PetsListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent Details;
            Details = new Intent(getApplicationContext(), DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("PetId",(i+1));
            Details.putExtras(bundle);
            startActivity(Details);
        }
    };

    public void AddPet(View view) {
        Intent AddPet;
        AddPet = new Intent(this, AddPetActivity.class);
        startActivity(AddPet);
    }
}
