package cl.adachersoft.bitacora.views.main.searchBar;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.adachersoft.bitacora.R;
import cl.adachersoft.bitacora.data.RecordsData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final int COLLAPSED = 0;
    private static final int EXPANDED = 1;

    private AutoCompleteTextView autoCompleteTextView;
    private ImageView expander;

    private ArrayAdapter<String> adapter;
    private List<String> suggestions;
    private SearchCallback callback;




    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback =(SearchCallback) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autocompleteTV);
        expander = (ImageView) view.findViewById(R.id.searchIv);

        autoCompleteTextView.animate().translationX(10000).setDuration(150).start();
        expander.setTag(COLLAPSED);
        setExpander();
        setCompletion();

    }

    private void setExpander() {
        expander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int) expander.getTag() == EXPANDED) {
                    expander.setTag(COLLAPSED);
                    autoCompleteTextView.animate().translationX(1000).setDuration(400).start();
                    expander.setImageResource(R.mipmap.ic_search_white_24dp);

                } else {
                    expander.setTag(EXPANDED);
                    autoCompleteTextView.animate().translationX(0).setDuration(400).start();
                    expander.setImageResource(R.mipmap.ic_close_white_24dp);

                }
            }


        });

    }

    private void setCompletion() {

        suggestions = new RecordsData().names();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, suggestions);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = autoCompleteTextView.getText().toString();
                callback.search(name);


            }
        });

        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String name = autoCompleteTextView.getText().toString();
                    autoCompleteTextView.dismissDropDown();
                    callback.search(name);
                    return true;
                }
                return false;

            }
        });

    }

    public void addSugestion(String name){
        suggestions.add(name);
        adapter.notifyDataSetChanged();

    }



}
