package cl.adachersoft.bitacora.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.adachersoft.bitacora.R;
import cl.adachersoft.bitacora.data.RecordsData;
import cl.adachersoft.bitacora.models.Record;
import cl.adachersoft.bitacora.views.main.recordList.ListClickListener;

/**
 * Created by cristian on 16-11-2016.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {


    private List<Record> records = new RecordsData().notDone();
    private ListClickListener listener;

    public RecordsAdapter(ListClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Record record = records.get(position);
        holder.name.setText(record.getName());


        CheckBox checkBox = holder.status;
        checkBox.setChecked(record.isDone());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            record.setDone(true);
                            record.save();
                            records.remove(position);
                            notifyDataSetChanged();
                        }
                    }, 100);
                }

            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.click(record.getId());


            }
        });


    }


    @Override
    public int getItemCount() {
        return records.size();
    }

    public void add(Record record) {
        records.add(0, record);
        notifyDataSetChanged();

    }

    public void search(String name) {

        List<Record> recordList = new RecordsData().byName(name);
        records.clear();
        records.addAll(recordList);
        notifyDataSetChanged();

    }


    public void reset() {

        records.clear();
        List<Record> recordList = new RecordsData().notDone();
        records.addAll(recordList);
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox status;
        //Same that above, create a subclass which you will use to access your views
        //But this time, the inflating is above and the finding is here

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameTv);
            status = (CheckBox) view.findViewById(R.id.recordCb);

        }

    }


}
