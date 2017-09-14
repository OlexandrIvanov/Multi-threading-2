package com.example.user.task2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    MyTask mt;
    Button btnDWL,btnPREP,btnPAUSE,btnSTOP,btnST;
    ProgressBar progressBar;
    int pause = 0;
    int i;
    int i2;

    TextView tex;

    String status= "Не завантаженно";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = (ProgressBar) findViewById(R.id.pb);
        tex = (TextView) findViewById(R.id.textView);


        btnDWL = (Button) findViewById(R.id.btnDWL);
        btnDWL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=0;
                i2=0;
                mt = new MyTask();
                mt.execute();
                btnSTOP.setEnabled(true);
                btnPAUSE.setEnabled(true);
                btnDWL.setEnabled(false);
                btnPREP.setEnabled(false);
                status ="Завантаження";
            }
        });

        btnPREP = (Button) findViewById(R.id.btnPREP);
        btnPAUSE = (Button) findViewById(R.id.btnPause);
        btnSTOP = (Button) findViewById(R.id.btnSTOP);
        btnST = (Button) findViewById(R.id.btnST);

        btnSTOP.setEnabled(false);
        btnPAUSE.setEnabled(false);

        btnPREP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    status= "Не завантаженно";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSTOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mt.cancel(true);
                btnSTOP.setEnabled(false);
                btnPAUSE.setEnabled(false);
                btnDWL.setEnabled(true);
                btnPREP.setEnabled(true);
                status = "Завантаження відміненно";
                progressBar.setProgress(0);
                tex.setText("Завантаженно 0/20");
            }
        });
        btnST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, status, Toast.LENGTH_LONG).show();
            }
        });

        btnPAUSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (pause){
                    case 0:
                        pause=1;
                        mt.cancel(true);
                        progressBar.setProgress(i);
                        status = "Завантаження на паузі";
                        btnPAUSE.setText("Відновити");
                        tex.setText("Завантаженно "+i+"/20");

                        break;
                    case 1:
                        pause=0;
                        i2=i;
                        mt = new MyTask();
                        mt.execute();

                        status = "Завантаження";
                        btnPAUSE.setText("Пауза");
                        break;
                }
            }
        });

    }

     class  MyTask extends AsyncTask<Void,Integer,Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int counter = i;

                for (i=i2 ; i < 20; i++) {
                    getFloor(counter);
                    publishProgress(++counter);
                    if (isCancelled()) return null;
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           tex.setText("Завантаженно 0/20");
           progressBar.setProgress(0);
            btnSTOP.setEnabled(false);
            btnPAUSE.setEnabled(false);
            btnDWL.setEnabled(true);
            btnPREP.setEnabled(true);
            status = "Завантаження закінчилося";

        }

        @Override
        protected void onProgressUpdate(Integer... values) {


            super.onProgressUpdate(values);
            tex.setText("Завантаженно "+values[0]+"/20");
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();


        }

        private void getFloor(int counter) throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }
    }

}


