package cl.adachersoft.bitacora.models;

import com.orm.SugarRecord;

import static android.R.attr.description;

/**
 * Created by cristian on 15-11-2016.
 */

public class Record extends SugarRecord {

    private String name,description;
    private boolean done;

    public Record() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
