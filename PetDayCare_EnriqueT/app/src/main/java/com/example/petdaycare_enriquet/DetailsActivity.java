package com.example.petdaycare_enriquet;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.petdaycare_enriquet.Data.PetDBHelper;

public class DetailsActivity extends AppCompatActivity {
    Character[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'á', 'é', 'í', 'ó', 'ú', ' '};
    Button UpdatePet;
    EditText Name;
    EditText Breed;
    Spinner Gender;
    EditText Weigth;
    LinearLayout NameContainer;
    LinearLayout BreedContainer;
    LinearLayout GenderContainer;
    LinearLayout WeigthContainer;
    RelativeLayout UpdatePetButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_details);

        UpdatePet = findViewById(R.id.UpdatePet);
        Name = findViewById(R.id.Name);
        Breed = findViewById(R.id.Breed);
        Gender = findViewById(R.id.Gender);
        Weigth = findViewById(R.id.Weigth);
        NameContainer = findViewById(R.id.NameContainer);
        BreedContainer = findViewById(R.id.BreedContainer);
        GenderContainer = findViewById(R.id.GenderContainer);
        WeigthContainer = findViewById(R.id.WeigthContainer);
        UpdatePetButtonContainer = findViewById(R.id.UpdatePetButtonContainer);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final int PetId = bundle.getInt("PetId");

        PetDBHelper pDbHelper = new PetDBHelper(this);
        Pets myPet = pDbHelper.displayPetInfo(PetId);

        Name.setHint(myPet.getName());
        Breed.setHint(myPet.getBreed());
        Weigth.setHint(String.valueOf(myPet.getWeight()));
        if (myPet.getGender().equalsIgnoreCase("female")) {
            Gender.setSelection(1);
        } else {
            Gender.setSelection(0);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxes()) {
                    callUpdatePet(PetId, takeBoxes());
                    finish();
                }
            }
        };
        UpdatePet.setOnClickListener(onClickListener);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        int orientation = getResources().getConfiguration().orientation;
        if ((orientation == Configuration.ORIENTATION_LANDSCAPE) && (diagonalInches<6.5)) {
            NameContainer.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 18));
            BreedContainer.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 18));
            GenderContainer.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 18));
            WeigthContainer.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 18));
            UpdatePetButtonContainer.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 28));
            UpdatePet.setPadding(15, 0, 15, 0);
            UpdatePet.setTextSize((float) 17.5);
        }
    }

    public boolean checkBoxes() {
        boolean Correct = true;
        String ToastText = "";
        if (checkIfNotOnlyAlphabet(Name.getText().toString())) {
            Correct = false;
            ToastText = "Nombre";
        }
        if (checkIfNotOnlyAlphabet(Breed.getText().toString())) {
            Correct = false;
            if (ToastText.equalsIgnoreCase("")) {
                ToastText = "Raza";
            } else {
                ToastText = ToastText + " y Raza";
            }
        }
        if(!Correct){
            Toast toast = Toast.makeText(getApplicationContext(), "¡ERROR! Carácteres no permitidos en: " + ToastText + ".", Toast.LENGTH_LONG);
            toast.show();
        }
        return Correct;
    }

    public boolean checkIfNotOnlyAlphabet(String stringGiven) {
        boolean NotOnlyAlphabet = false;
        int countCorrect=0;
        for (int i = 0; stringGiven.length() > i; i++) {
            for (int j = 0; alphabet.length > j; j++) {
                if (stringGiven.toLowerCase().charAt(i) == alphabet[j]) {
                    countCorrect++;
                }
            }
        }
        if (countCorrect!=stringGiven.length()){
            NotOnlyAlphabet=true;
        }
        return NotOnlyAlphabet;
    }

    public boolean[] takeBoxes() {
        boolean[] boxesChanges = {false, false, false, false};
        if (Name.getText().length() != 0) {
            boxesChanges[0] = true;
        }
        if (Breed.getText().length() != 0) {
            boxesChanges[1] = true;
        }
        if (Weigth.getText().length() != 0) {
            boxesChanges[3] = true;
        }
        return boxesChanges;
    }

    public void callUpdatePet(int PetIdGiven, boolean[] boxesChanges) {
        String name = null;
        String breed = null;
        String gender = null;
        double weight = 0;
        for (int i = 0; boxesChanges.length > i; i++) {
            if (i == 0) {
                if (!boxesChanges[i]) {
                    name = (String) Name.getHint();
                } else {
                    name = Name.getText().toString();
                }
            } else if (i == 1) {
                if (!boxesChanges[i]) {
                    breed = (String) Breed.getHint();
                } else {
                    breed = Breed.getText().toString();
                }
            } else if (i == 2) {
                gender = Gender.getSelectedItem().toString();
            } else {
                if (!boxesChanges[i]) {
                    weight = Double.parseDouble((String) Weigth.getHint());
                } else {
                    weight = Double.parseDouble(Weigth.getText().toString());
                }
            }
        }
        Pets pet = new Pets(name, breed, gender, weight);
        PetDBHelper petDBHelper = new PetDBHelper(this);
        petDBHelper.UpdatePet(PetIdGiven, pet);
    }

    public boolean onOptionsItemSelected(MenuItem Item) {
        switch (Item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(Item);
    }

}

