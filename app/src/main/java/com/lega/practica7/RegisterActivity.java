package com.lega.practica7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lega.practica7.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private int contador =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.RAImage.setImageResource(R.drawable.imagen);
        binding.RAImageCamara.setImageResource(R.drawable.ic_camara_foreground);

        dropdownBinding();

        listener();

    }

    private void dropdownBinding() {
        String []  listFruits = getResources().getStringArray(R.array.ListOption);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listFruits);
        binding.RAAutocomplete.setAdapter(adapter);
    }

    private void listener() {
        binding.RANameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (binding.RANameText.getText().toString() != null && binding.RANameText.getText().toString().length() > 0){
                    if(binding.RANameText.getText().toString().contains("@") || binding.RANameText.getText().toString().contains("!")) {
                        binding.RANameText.setError("Ups, no creo que sea correcto, resviselo");
                        contador = 0;
                    }
                    contador +=1 ;
                    if(contador > 1){
                        binding.RABtnAction.setEnabled(true);
                    }
                }else{
                    binding.RABtnAction.setEnabled(false);
                }
            }
        });

        binding.RASurnameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(binding.RASurnameText.getText().toString() != null && binding.RASurnameText.getText().toString().length() > 0) {
                    if(binding.RASurnameText.getText().toString().contains("@") || binding.RASurnameText.getText().toString().contains("!")) {
                        binding.RASurnameText.setError("Ups, no creo que sea correcto, resviselo");
                        contador = 0;
                    }
                    contador +=1 ;
                    if(contador > 1){
                        binding.RABtnAction.setEnabled(true);
                    }else{
                        binding.RABtnAction.setEnabled(false);
                    }
                }
            }
        });

        binding.RAAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String optionSelected = "";
                if (String.valueOf(adapterView.getItemAtPosition(i)).equals("0-5")) {
                    Log.e("lega", "1");
                    binding.RADrop.setError("Esto no es para ti");
                }else  if (String.valueOf(adapterView.getItemAtPosition(i)).equals("6-11")) {
                    Log.e("lega", "2");
                    binding.RADrop.setError("Esto no es para ti");
                }else if (String.valueOf(adapterView.getItemAtPosition(i)).equals("12-17")) {
                    Log.e("lega", "3");
                    binding.RADrop.setError("Esto no es para ti");
                }else{
                    binding.RADrop.setError(null);
                }
            }
        });

        binding.RABtnAction.setOnClickListener(view -> {
            if(binding.RANameText.getText().length() > 0 && binding.RANameText.getText() != null && binding.RASurnameText.getText().length() > 0 && binding.RASurnameText.getText() != null){
                Intent data = new Intent();
                data.putExtra("name", binding.RANameText.getText().toString());
                data.putExtra("surname", binding.RASurnameText.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }else{
                Toast.makeText(RegisterActivity.this, "Debes Llenar todos los datos solicitados.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.RATxtCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeb("https://developers.google.com/ml-kit/terms");
            }
        });

        binding.RAImageCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });
    }

    public void openWeb(String url){
        Uri web= Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,web);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivityForResult(intent, 12);
    }

}