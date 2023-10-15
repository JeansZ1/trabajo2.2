package com.example.trabajo22;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private ImageView mImageView;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageView);
        downloadButton = findViewById(R.id.downloadButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la tarea asincrónica para descargar y mostrar la imagen
                new DownloadImageTask().execute("https://4.bp.blogspot.com/-jDcqEFr898k/XBAeeuO_B3I/AAAAAAAAAEM/ugCkZsDFoN8_HGj34Mcs6O61GvNDzcKaACLcBGAs/s1600/phpCode.png");
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                // Actualiza la ImageView con la imagen descargada
                mImageView.setImageBitmap(result);
            } else {
                // En caso de error, puedes manejarlo apropiadamente
                // mImageView.setImageResource(R.drawable.error_image); // Esto es solo un ejemplo
            }
        }
    }
}
