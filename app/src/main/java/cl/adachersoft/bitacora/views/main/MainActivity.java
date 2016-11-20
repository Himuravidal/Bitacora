package cl.adachersoft.bitacora.views.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cl.adachersoft.bitacora.R;
import cl.adachersoft.bitacora.models.Record;
import cl.adachersoft.bitacora.views.main.recordList.RecordListFragment;
import cl.adachersoft.bitacora.views.main.searchBar.SearchCallback;
import cl.adachersoft.bitacora.views.main.searchBar.SearchFragment;

public class MainActivity extends AppCompatActivity implements FirstCallback, SearchCallback {


    private Dialog dialog;
    private RecordListFragment recordListFragment;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recordListFragment = (RecordListFragment) getSupportFragmentManager().findFragmentById(R.id.recordListFragment);
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.searchfragment);

        setDialog();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(getCurrentFocus(), InputMethodManager.SHOW_FORCED);

            }
        });
    }


    private void setDialog() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_pending);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final EditText recordInput = (EditText) dialog.findViewById(R.id.pendingET);
        ImageButton saveBtn = (ImageButton) dialog.findViewById(R.id.saveBtn);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recordName = recordInput.getText().toString();
                createdrecord(recordName);


            }
        });


        recordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    String recordName = recordInput.getText().toString();
                    createdrecord(recordName);
                    return true;

                }

                return false;

            }
        });

    }

    private void createdrecord(String name) {
        RecordValidation recordValidation = new RecordValidation(this);
        recordValidation.init(name);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        dialog.dismiss();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(Record record) {

        recordListFragment.addRecord(record);
        searchFragment.addSugestion(record.getName());


    }

    @Override
    public void fail() {

        Toast.makeText(this, "Ingrese un Registro en la Bitacora", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void search(String name) {
        recordListFragment.search(name);

    }
}
