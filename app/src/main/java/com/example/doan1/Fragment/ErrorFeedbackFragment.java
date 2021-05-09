package com.example.doan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.doan1.MainActivity;
import com.example.doan1.R;

public class ErrorFeedbackFragment extends Fragment {
    View view;
    public static EditText edtTitle, edtPhone, edtContent;
    public static SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_error_feedback, container, false);
        initWidget();

        setUpSearchView();

        return  view;
    }

    private void setUpSearchView() {
        final androidx.appcompat.widget.SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        String dataArr[] = new String[MainActivity.allNode.size()];
        for (int i = 0; i < MainActivity.allNode.size(); i++) {
            dataArr[i] = MainActivity.allNode.get(i).getAddress();
        }

        ArrayAdapter<String> newsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataArr);
        searchAutoComplete.setAdapter(newsAdapter);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(queryString);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getdatasearchview(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getdatasearchview(String queryString) {
        int tmp = 0;
        for(int i = 0; i < MainActivity.allNode.size(); i++) {
            if(MainActivity.allNode.get(i).getAddress().equals(queryString)) {
                tmp++;
                break;
            }
        }
        if(tmp == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setMessage("Địa điểm này chưa được đặt máy đo");
            alertDialog.show();
        }
    }

    private void initWidget() {
        edtTitle = view.findViewById(R.id.edittextTitleFeedback);
        searchView = view.findViewById(R.id.searchViewAddress);
        edtPhone = view.findViewById(R.id.edittextPhoneFeedback);
        edtContent = view.findViewById(R.id.edittextContentFeedback);
    }
}
