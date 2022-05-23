package com.example.a5_16;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //atributos
    private EditText etTelefono;
    private ImageButton btnLlamar, btnCamara;
    //atributos de tipo primitivo
    private String numerodetelefono;
    private String numeroTelefono;
    private ImageView ivImage;

    private final int PHONE_CODE = 100;
    private final int CAMERA_CODE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarvistas();
        btnLlamar.setOnClickListener(view->{
            obtenerInformacion();
            validar();
        });
        btnCamara.setOnClickListener(view ->
        {
            activarCamara();
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
    private void activarCamara() {
        //el tachado no es algo malo
        //se significa que el metodo en particular esta deprecado
        //que se significa que ya no se le va ha dar mas soporte no lo agregan ni le quitan cosas y que a mediano plazo pueden quitar el metodo de la libreria
        //puede que ahora hay algo mas avanzado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_CODE);
        }
    }
    private void configurarIntentImplicito() {
        if(!numeroTelefono.isEmpty())
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CODE); //necesita 2 parametros, codigo que representa la llamada 100
            }
            else
            {
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
    private void activarcamara() {
        Intent IntentCamara = new Intent("android.media.activity.IMAGE_CAPTURE");
        startActivityForResult(IntentCamara,CAMERA_CODE);//agregar una varibale de pictureimage de camara
    }
    private void obtenerInformacion() {
        numerodetelefono = etTelefono.getText().toString();
    }

    private void inicializarvistas() {
        etTelefono = findViewById(R.id.etTelefono);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnCamara = findViewById(R.id.btnCamara);
        ivImage = findViewById(R.id.ivcamera);
    }
    private boolean revisarPermisos(String permiso)
    {
        int resultadoPermiso = checkCallingOrSelfPermission(permiso);
        return resultadoPermiso == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PHONE_CODE:
                String permiso = permissions[0];
                int valorPermiso = grantResults[0];
                //ambos valores coinciden en el array
                //para evitar que hayan errores humanos mas que nada
                if (permiso.equals(Manifest.permission.CALL_PHONE))
                {
                    //VALIDAR SI SE TIENE EL PERMISO O NO
                    if (valorPermiso == PackageManager.PERMISSION_GRANTED)
                    {
                        Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ numeroTelefono));
                        startActivity(intentLlamada);
                    }
                }
                break;
            case CAMERA_CODE:
                //el metodo request permisos es el autor intelectiual de la ventana de permisos as ino perjudicamos la funcionalidad de la applicacion
                int valor = grantResults[0];
                if(valor == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intentCamara, CAMERA_CODE);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //tres parametros codigo de peticion 50 , representa un aconstante que nosotros contrastamos con los que android ya conoce
        //para contrastar que tod0 esta bien , es un intent que dice data es un aprarmetroa qeu contiene los datos de terceros la
        // informacion que queremos procesar
        switch(requestCode)
        {
            case CAMERA_CODE:
                //RESULT_OK es un avariable que constrastea que tod0 esta bien
                if(resultCode == RESULT_OK)
                {
                    //para recibir imagenes generalmente se trabaja en formato mapa de bits
                    Bitmap foto = (Bitmap)data.getExtras().get("data");
                    ivImage.setImageBitmap(foto);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
    /*private boolean revizarpermisos(String permiso){
        //android maneja los permisosode esta manera
        //GRANTED: PERMISO OTORGADO
        //DENIED: PERMISO NO OTORGADO
        //validar si el permiso a evaluar en su aplicacion tiene el valor que android maneja para un permiso otorgado que seria (GRANTE)
        return false;//solo por ahora
    }*/
}