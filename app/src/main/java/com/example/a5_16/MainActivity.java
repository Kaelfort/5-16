package com.example.a5_16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    //atributos
    private EditText etTelefono;
    private ImageButton btnLlamar, btnCamara;
    //atributos de tipo primitivo
    private String numerodetelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarvistas();
        btnLlamar.setOnClickListener(view->{
            obtenerInformacion();
            validar();
        });

    }

    private void validar() {
        //primero validadmos que el campo no este vacio
        if(!numerodetelefono.isEmpty()){
            //primer problema
            ///las llamadas cambiaron desde la v6 o sdk23
            //a partir de esa version se hace el coigo con algunos cambios
            //antes de ella habia otra manera de hacer
            //validar si la version de tu proyectoe es mayor o igual ala version de android donde
            //cambio la forma de procesar
            //SDK_INT = 24
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                //para versiones nuevas
            }else
            {
                //para versiones antiguas
                configurarnodoversionesAntuguas();
            }
        }
    }

    private void configurarnodoversionesAntuguas() {
        //aqui configurareos en intend para veriones antegriores
        //1. que accion quieren realizar
        //2 . que datos enviar AL intent
        //uri URL donde configuras las cabeceras de rutas, para psar datos
        Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numerodetelefono));
        startActivity(intentLlamada);
    }

    private void obtenerInformacion() {
        numerodetelefono = etTelefono.getText().toString();
    }

    private void inicializarvistas() {
        etTelefono = findViewById(R.id.etTelefono);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnCamara = findViewById(R.id.btnCamara);
    }
    private boolean revizarpermisos(String permiso){
        //android maneja los permisosode esta manera
        //GRANTED: PERMISO OTORGADO
        //DENIED: PERMISO NO OTORGADO
        //validar si el permiso a evaluar en su aplicacion tiene el valor que android maneja para un permiso otorgado que seria (GRANTE)
        return false;//solo por ahora
    }
}