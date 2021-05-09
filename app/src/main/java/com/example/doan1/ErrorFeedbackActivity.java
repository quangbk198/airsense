package com.example.doan1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.doan1.Fragment.AboutUsFragment;
import com.example.doan1.Fragment.ErrorFeedbackFragment;

public class ErrorFeedbackActivity extends AppCompatActivity {
    public static String title, addr, phone, content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_feedback);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setToolbar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ErrorFeedbackFragment errorFeedbackFragment = new ErrorFeedbackFragment();
        fragmentTransaction.add(R.id.fragment_content_feedback, errorFeedbackFragment);
        fragmentTransaction.commit();
    }

    private void setToolbar() {
        //Tạo một toolbar sau đó set toolbar về actionbar
        Toolbar toolbar = findViewById(R.id.toolbarAboutUs);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_gray);
        actionbar.setTitle(R.string.write_feedback);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_error_feedback, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ErrorFeedbackActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_send_feedback:
                sendFeedBack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendFeedBack() {
        String[] nguoinhan = {"ngohuyquang02@gmail.com"};
        title = ErrorFeedbackFragment.edtTitle.getText().toString().trim();
        phone = ErrorFeedbackFragment.edtPhone.getText().toString().trim();
        content = ErrorFeedbackFragment.edtContent.getText().toString().trim();
        addr = ErrorFeedbackFragment.searchView.getQuery().toString().trim();
        String message = "+ Địa chỉ: " + addr + " + Số điện thoại: " + phone + " + Nội dung: " + content;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, nguoinhan);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "choose an email client"));
    }



























}
