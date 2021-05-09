package com.example.doan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import com.example.doan1.Controller.Libs;
import com.example.doan1.Fragment.DetailsInformationFragment;
import com.example.doan1.Fragment.ListLocationFragment;
import com.example.doan1.Fragment.MapsFragment;
import com.example.doan1.Model.DataMqtt;
import com.example.doan1.Model.ListNode;
import com.example.doan1.Object.AirQualityInfo;
import com.example.doan1.Object.Node;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    public static TabLayout tabLayout;
    public static ArrayList<Node> allNode;
    public static ArrayList<AirQualityInfo> takeAirInfoMQTT;
    public static int currentNode;
    public static ArrayList<Node> listFavLocation;

    SharedPreferences sharedLocaFav;

    public static int currentPositionTab;

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    FrameLayout frameLayout;
    NavigationView navigationView;

    private long backPressTime;
    public static RadioButton radioVN, radioEng;

    @Override
    protected void onNewIntent(Intent intent) {     //Vì sử dụng launch mode single task cho MainActivity, nên sau khi
        //dữ liệu chuyển từ datamqtt sang mainactivity sẽ chạy vào hàm này chứ ko chạy vào onCreate
        super.onNewIntent(intent);
        Bundle b = intent.getExtras();
        //if(b.getInt("checkIntent") == 1) {
            //takeAirInfoMQTT = b.getParcelableArrayList("Data");
        //}
    }

    @Override
    protected void onResume() {
        super.onResume();
        //DataMqtt.takeData.set(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //DataMqtt.takeData.set(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //ánh xạ các view

        listFavLocation = new ArrayList<>();

        loadListLocaFav();

        allNode = ListNode.CreateNode();
        setLayout(false);

    }

    private void setLayout(boolean checkLogin) {
        setContentView(R.layout.activity_main);
        InitWiget();
        setTablayout();
        setToolbar();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.bt_search).setVisible(false);
        nav_menu.findItem(R.id.connect_wifi).setVisible(checkLogin);
    }

    private void loadListLocaFav() {        //Load danh sách địa điểm yêu thích
        sharedLocaFav = getSharedPreferences("DataLocaFav", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedLocaFav.getString("listlocafav", null);
        Type type = new TypeToken<ArrayList<Node>>() {
        }.getType();
        listFavLocation = gson.fromJson(json, type);

        if (listFavLocation == null) {
            listFavLocation = new ArrayList<>();
        }
    }

    //Kích đúp thoát để thoát ứng dụng
    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            Intent start = new Intent(Intent.ACTION_MAIN);
            start.addCategory(Intent.CATEGORY_HOME);
            start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start);
            stopService(new Intent(getBaseContext(), DataMqtt.class));
            finish();
            System.exit(0);
        }
        else {
            Toast.makeText(MainActivity.this, R.string.press_exit, Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }

    private void InitWiget() {
        frameLayout = findViewById(R.id.contents_fragment);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void setTablayout() {
        TabLayout.Tab fisrtTab = tabLayout.newTab();
        fisrtTab.setText(R.string.map);
        fisrtTab.setIcon(R.drawable.ic_public);
        tabLayout.addTab(fisrtTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText(R.string.list);
        secondTab.setIcon(R.drawable.ic_playlist_device);
        tabLayout.addTab(secondTab);

        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText(R.string.details);
        thirdTab.setIcon(R.drawable.ic_information);
        tabLayout.addTab(thirdTab);

        fragment = new MapsFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.contents_fragment, fragment);
        ft.commit();

        //thay đổi màu icon khi click vào tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPositionTab = tab.getPosition();
                tab.getIcon().setColorFilter(Color.parseColor("#0078FF"), PorterDuff.Mode.SRC_IN);
                fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MapsFragment();
                        break;
                    case 1:
                        fragment = new ListLocationFragment();
                        break;
                    case 2:
                        fragment = new DetailsInformationFragment();
                        break;
                }
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.contents_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setToolbar() {
        //Tạo một toolbar sau đó set toolbar về actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);     //icon của navigation trên actionbar
        actionbar.setTitle(null);


        mDrawerlayout = findViewById(R.id.navigationDrawer);
    }

    private void getdatasearchview(String queryString) {
        int tmp = 0;
        for(int i = 0; i < allNode.size(); i++) {
            if(allNode.get(i).getAddress().equals(queryString)) {
                tmp++;
                currentNode = i;
                break;
            }
        }
        if(tmp == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setMessage("Địa điểm này chưa được đặt máy đo");
            alertDialog.show();
        }
        else {
            if(currentPositionTab == 0 || currentPositionTab == 1) {
                TabLayout.Tab tab = tabLayout.getTabAt(2);
                tab.select();
            }
            if (currentPositionTab == 2) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailsInformationFragment detailsInformationFragment = new DetailsInformationFragment();
                fragmentTransaction.replace(R.id.contents_fragment, detailsInformationFragment);
                fragmentTransaction.commit();
            }
        }

    }

    @Override       //search view
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.bt_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm địa điểm");

        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        String dataArr[] = new String[allNode.size()];
        for (int i = 0; i < allNode.size(); i++) {
            dataArr[i] = allNode.get(i).getAddress();
        }

        ArrayAdapter<String> newsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataArr);
        searchAutoComplete.setAdapter(newsAdapter);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(queryString);
                getdatasearchview(queryString);
            }
        });

        // Below event is triggered when submit search query.
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




        return super.onCreateOptionsMenu(menu);
    }

    @Override       //override lại hàm này để click vào nút menu trên actionbar sẽ hiện ra navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerlayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_language:
                showDialogLanguage();
                break;

            case R.id.nav_notification:
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_aboutus:
                intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;

            case R.id.feedback_error:
                intent = new Intent(MainActivity.this, ErrorFeedbackActivity.class);
                startActivity(intent);
                break;

            case R.id.connect_wifi:
                stopService(new Intent(getBaseContext(), DataMqtt.class));
                intent = new Intent(MainActivity.this, SmartConfigActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.signIn_dev:
                showDialogLogin();
                break;

        }
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.nav_setting);
        item.setVisible(false);
        item = menu.findItem(R.id.nav_admin);
        item.setVisible(false);
        item = menu.findItem(R.id.nav_option);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    private void showDialogLanguage() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_language);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        
        radioVN = dialog.findViewById(R.id.lang_viet);
        radioEng = dialog.findViewById(R.id.lang_eng);
        Button chooseLan = dialog.findViewById(R.id.buttonChooseLanguage);

        chooseLan.setOnClickListener(new View. OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang = "";
                if (radioVN.isChecked()) {
                    lang = "vi";
                    dialog.dismiss();
                    SplashActivity.temp = lang;
                    Libs.changeLang(lang, MainActivity.this);
                    setLayout(false);
                    radioVN.setChecked(SplashActivity.prefs.getBoolean("checked", false));

                } else if (radioEng.isChecked()) {
                    lang = "en";
                    dialog.dismiss();
                    SplashActivity.temp = lang;
                    Libs.changeLang(lang, MainActivity.this);
                    setLayout(false);
                    radioEng.setChecked(SplashActivity.prefs.getBoolean("checked", false));
                }
                Libs.changeLang(lang, MainActivity.this);
            }
        });
        dialog.show();
    }

    private void showDialogLogin() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText edtusername = dialog.findViewById(R.id.edittextusername);
        final EditText edtpass = dialog.findViewById(R.id.edittextPass);
        Button btnLogin = dialog.findViewById(R.id.buttonLogIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtusername.getText().toString().trim();
                String pass = edtpass.getText().toString().trim();
                if(user.equals("admin") && pass.equals("admin")) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    setLayout(true);
                }
                else {
                    Toast.makeText(MainActivity.this, "Tài khoản, mật khẩu sai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
