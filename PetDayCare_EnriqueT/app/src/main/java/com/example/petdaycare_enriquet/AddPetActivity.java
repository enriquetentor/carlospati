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

public class AddPetActivity extends AppCompatActivity {
    Character[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'á', 'é', 'í', 'ó', 'ú', ' '};
    EditText Name;
    EditText Breed;
    Spinner Gender;
    EditText Weigth;
    LinearLayout NameContainer;
    LinearLayout BreedContainer;
    LinearLayout GenderContainer;
    LinearLayout WeigthContainer;
    RelativeLayout CreateNewPetButtonContainer;
    Button CreateNewPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);

        CreateNewPet = findViewById(R.id.CreateNewPet);
        Name = findViewById(R.id.Name);
        Breed = findViewById(R.id.Breed);
        Gender = findViewById(R.id.Gender);
        Weigth = findViewById(R.id.Weigth);
        NameContainer = findViewById(R.id.NameContainer);
        BreedContainer = findViewById(R.id.BreedContainer);
        GenderContainer = findViewById(R.id.GenderContainer);
        WeigthContainer = findViewById(R.id.WeigthContainer);
        CreateNewPetButtonContainer = findViewById(R.id.CreateNewPetButtonContainer);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxes()) {
                    callInsertNewPet();
                    CleanBoxes();
                }
            }
        };

        CreateNewPet.setOnClickListener(onClickListener);

        Spinner spinner = Gender;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
            CreateNewPetButtonContainer.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 28));
            CreateNewPet.setPadding(15, 0, 15, 0);
            CreateNewPet.setTextSize((float) 17.5);
        }
    }

    public boolean checkBoxes() {
        boolean Correct = false;
        if (Name.getText().length() != 0) {
            if (Breed.getText().length() != 0) {
                if (Gender.getSelectedItem() != null) {
                    if (Weigth.getText().length() != 0) {
                        Correct = true;
                    }
                }
            }
        }
        if (!Correct) {
            String[] BoxesText = {"Nombre", "Raza", "Peso"};
            String ToastText = "";
            if (Name.getText().length() != 0) {
                BoxesText[0] = "";
            }
            if (Breed.getText().length() != 0) {
                BoxesText[1] = "";
            }
            if (Weigth.getText().length() != 0) {
                BoxesText[2] = "";
            }
            for (int i = 0; BoxesText.length > i; i++) {
                if (i == 0 && !BoxesText[(i + 2)].equalsIgnoreCase("")
                        && !BoxesText[i].equalsIgnoreCase("")) {
                    ToastText = ToastText + BoxesText[i] + ", ";
                } else if (i != (BoxesText.length - 1) && !BoxesText[(i + 1)].equalsIgnoreCase("")
                        && !BoxesText[i].equalsIgnoreCase("")) {
                    ToastText = ToastText + BoxesText[i] + ", ";
                } else {
                    ToastText = ToastText + BoxesText[i];
                }
            }
            Toast toast = Toast.makeText(getApplicationContext(), "No ha introducido: " + ToastText + ".", Toast.LENGTH_LONG);
            toast.show();
        } else {
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
            Toast toast = Toast.makeText(getApplicationContext(), "ERROR! Ha introducido un número en: " + ToastText + ".", Toast.LENGTH_LONG);
            toast.show();
        }
        return Correct;
    }

    public boolean checkIfNotOnlyAlphabet(String stringGiven) {
        boolean NotOnlyAlphabet = false;
        int countCorrect = 0;
        for (int i = 0; stringGiven.length() > i; i++) {
            for (int j = 0; alphabet.length > j; j++) {
                if (stringGiven.toLowerCase().charAt(i) == alphabet[j]) {
                    countCorrect++;
                }
            }
        }
        if (countCorrect != stringGiven.length()) {
            NotOnlyAlphabet = true;
        }
        return NotOnlyAlphabet;
    }

    public void callInsertNewPet() {
        Pets pet = new Pets(Name.getText().toString(), Breed.getText().toString(), Gender.getSelectedItem().toString(),
                Integer.parseInt(Weigth.getText().toString()));
        PetDBHelper petDBHelper = new PetDBHelper(this);
        double test = petDBHelper.insertPet(pet);
        Toast toast = Toast.makeText(getApplicationContext(), "Nueva inserción con el Id: " + test, Toast.LENGTH_LONG);
        toast.show();
    }

    public void CleanBoxes() {
        Name.getText().clear();
        Breed.getText().clear();
        Gender.setSelection(0);
        Weigth.getText().clear();
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
