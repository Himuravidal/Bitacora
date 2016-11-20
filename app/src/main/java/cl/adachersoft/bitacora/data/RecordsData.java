package cl.adachersoft.bitacora.data;

import java.util.ArrayList;
import java.util.List;

import cl.adachersoft.bitacora.models.Record;

/**
 * Created by cristian on 16-11-2016.
 */

public class RecordsData {

    public List<Record> notDone() {
        List<Record> records = new ArrayList<>();
        List<Record> recordList = Record.find(Record.class, " done = 0");
        if (recordList != null && recordList.size() > 0) {
            records.addAll(recordList);
        }

        return records;
    }

    public List<String> names() {
        List<String> names = new ArrayList<>();
        List<Record> records = notDone();
        if (records.size() > 0) {
            for (Record record : records) {
                names.add(record.getName());
            }
        }

        return names;


    }


    public List<Record> byName(String name) {
        List<Record> records = new ArrayList<>();
        String query = "done = 0 AND name LIKE" + "'&" + name + "%'";
        List<Record> recordList = Record.find(Record.class, query);
        if (recordList != null && recordList.size() > 0) {
            records.addAll(recordList);

        }
        return records;

    }

}
