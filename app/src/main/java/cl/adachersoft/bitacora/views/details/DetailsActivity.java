package cl.adachersoft.bitacora.views.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import cl.adachersoft.bitacora.R;
import cl.adachersoft.bitacora.models.Record;
import cl.adachersoft.bitacora.views.main.recordList.RecordListFragment;

public class DetailsActivity extends AppCompatActivity {
    private Record record;
    private EditText detailsInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        long recordID = getIntent().getLongExtra(RecordListFragment.RECORDS_ID, 0);
        record = Record.findById(Record.class, recordID);

        getSupportActionBar().setTitle(record.getName());

        detailsInput = (EditText) findViewById(R.id.detailsET);


    }

    @Override
    protected void onResume() {
        super.onResume();
        detailsInput.setText(record.getDescription());

    }


    @Override
    protected void onPause() {

        String details = detailsInput.getText().toString();
        record.setDescription(details);
        record.save();
        super.onPause();
    }
}
