package cl.adachersoft.bitacora.views.main;

import cl.adachersoft.bitacora.models.Record;

/**
 * Created by cristian on 16-11-2016.
 */

public class RecordValidation {

    private FirstCallback callback;

    public RecordValidation(FirstCallback callback) {
        this.callback = callback;
    }

    public void init(String name) {
        if (name.trim().length() > 0) {
            Record record = new Record();
            record.setName(name);
            record.setDone(false);
            record.save();
            callback.success(record);

        } else {
            callback.fail();

        }


    }


}
