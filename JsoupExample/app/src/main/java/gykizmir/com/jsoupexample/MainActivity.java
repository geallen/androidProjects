package gykizmir.com.jsoupexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    Button learnTitleBtn;
    TextView titleOfBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleOfBlog = (TextView) findViewById(R.id.learnTitle);

        learnTitleBtn = (Button) findViewById(R.id.learnTitle);

        learnTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Title().execute();
            }
        });


    }

    private class Title extends AsyncTask<Void, Void, Void>{

        String url = "http://sengamze.blogspot.com/2019/04/android-resim-ekleme.html";
        String title;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Jsoup Example");
            progressDialog.setMessage("Title is retrieving");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();
                title = document.title();
            } catch (IOException ex){
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid){
            titleOfBlog.setText(title);
            progressDialog.dismiss();
        }
    }

}
