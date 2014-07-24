package ua.pl.mukha.app01.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    public Elements title;
    public ArrayList<String> titleList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView1);
        new NewThread().execute();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, titleList);
    }

    public class NewThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Document doc;

            try {
                doc = Jsoup.connect("http://rada-poltava.gov.ua/").get();
                title = doc.select("p");
                titleList.clear();

                for (Element titles : title) {
                    titleList.add(titles.text());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            lv.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
