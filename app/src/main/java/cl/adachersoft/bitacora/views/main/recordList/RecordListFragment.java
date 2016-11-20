package cl.adachersoft.bitacora.views.main.recordList;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.adachersoft.bitacora.R;
import cl.adachersoft.bitacora.adapters.RecordsAdapter;
import cl.adachersoft.bitacora.models.Record;
import cl.adachersoft.bitacora.views.details.DetailsActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecordListFragment extends Fragment implements ListClickListener {


    private RecordsAdapter recordsAdapter;
    public static final String RECORDS_ID = "RECORDS_ID";

    public RecordListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recordsRV);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recordsAdapter = new RecordsAdapter(this);

        recyclerView.setAdapter(recordsAdapter);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recordsAdapter.reset();
                        refreshLayout.setRefreshing(false);
                    }
                }, 800);
            }
        });

    }

    public void addRecord(Record record) {
        recordsAdapter.add(record);

    }


    @Override
    public void click(long id) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(RECORDS_ID, id);
        startActivity(intent);

    }

    public void search(String name) {
        recordsAdapter.search(name);

    }

}
