package com.desarrollo.lab3example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int CAMERA_PIC_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bitmap imagen = (Bitmap) data.getExtras().get("data");
                ImageView foto = (ImageView) findViewById(R.id.img_prev);
                foto.setImageBitmap(imagen);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BOTON LLAMAR

        Button btn_llamar = (Button)findViewById(R.id.btn_call);
        btn_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:952853779");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        //BOTON UBICACION

        Button btn_location = (Button)findViewById(R.id.btn_location);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri location = Uri.parse("geo:0.0?q=Universidad+Privada+de+Tacna,+Granada,+Tacna");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

                if (mapIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mapIntent);
                } else {
                    Snackbar.make(view, "No existe Actividad para esta accion", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON BUSQUEDA

        Button btn_web = (Button)findViewById(R.id.btn_web);
        btn_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://upt.edu.pe");
                Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);
                PackageManager packageManager = getPackageManager();
                //VERIFICA SI EL INTENT COMPLETO SU RECORRIDO
                List<ResolveInfo>activities = packageManager.queryIntentActivities(webIntent,PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                //VERIFICA NAVEGADOR POR DEFECTO
                String title = getResources().getString(R.string.choose_title);
                Intent wChooser = Intent.createChooser(webIntent,title);
                if (isIntentSafe) {
                    startActivity(wChooser);
                }
            }
        });

        //EXPLANAR BOTON IMAGEN Y PREVIEW

        Button btn_camera = (Button)findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }

        });
    }
}