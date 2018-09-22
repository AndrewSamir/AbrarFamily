package com.service.andrewsamir.main.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.service.andrewsamir.main.Fragments.KashfFragment;
import com.service.andrewsamir.main.R;
import com.service.andrewsamir.main.adaptors.DBhelper;
import com.service.andrewsamir.main.adaptors.HomePagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.service.andrewsamir.main.data.Name;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


public class HOME extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    public static ViewPager mViewPager;
    static boolean calledAlready = false;

    DBhelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(getResources().getString(R.string.app_name));

        //  setSupportActionBar(toolbar);
        myDB = new DBhelper(this);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }


        mViewPager = (ViewPager) findViewById(R.id.containerTT);
        mViewPager.setOffscreenPageLimit(3);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsTT);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("الكشف");
        tabLayout.getTabAt(1).setText("الغياب");
        tabLayout.getTabAt(2).setText("الافتقاد");
        tabLayout.getTabAt(3).setText("اعياد الميلاد");

        /*tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_account_box);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_manage);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);


         tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        */


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_kashf)
        {

            mViewPager.setCurrentItem(0);

        } else if (id == R.id.nav_absent)
        {

            mViewPager.setCurrentItem(1);

        } else if (id == R.id.nav_eftkad_list)
        {

            mViewPager.setCurrentItem(2);

        } else if (id == R.id.nav_birthdates)
        {

            mViewPager.setCurrentItem(3);

        } else if (id == R.id.nav_sign_out)
        {

            FirebaseAuth.getInstance().signOut();
            myDB.dropAllTabels();
            startActivity(new Intent(this, LogIn.class));
            finish();
        } else if (id == R.id.nav_export_excel)
        {
            myDB.deleteAllFromExcel();
            for (Name kashfItem : KashfFragment.kashfLists)
            {
                boolean c = myDB.ADD_TO_EXCEL(kashfItem.getName(), kashfItem.getHomeNo(), kashfItem.getStreet(),
                        kashfItem.getFloor(), kashfItem.getFlat(), kashfItem.getDescription(),
                        kashfItem.getAnotherAdd(), kashfItem.getBirthdate(), kashfItem.getMama(),
                        kashfItem.getPapa(), kashfItem.getPhone());
                Log.d("dbKashf", c + kashfItem.getName());
            }
            SQLiteToExcel sqliteToExcel = new SQLiteToExcel(this, myDB.getDatabaseName());
            SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);


            sqliteToExcel.exportSingleTable(myDB.EXCEL_TABLE, "  فصل  " + prefs.getString("nameFasl", "الكشف") + ".xls", new SQLiteToExcel.ExportListener()
            {
                @Override
                public void onStart()
                {
                    Log.d("dbKashf", "start");

                }

                @Override
                public void onCompleted(String filePath)
                {
                    Log.d("dbKashf", filePath);
//                    myDB.deleteAllFromExcel();
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("dbKashf", e.toString());
                }
            });
        }/* else if (id == R.id.nav_import_excel)
        {
            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1)
                    .withFilter(Pattern.compile(".*\\.xls$")) // Filtering files and directories by file name using regexp
                    .withFilterDirectories(true) // Set directories filterable (false by default)
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static void readFile(Context context, String filename)
    {
        Log.w("FileUtils", filename);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.w("FileUtils", "Storage not available or read only");
            return;
        }

        FileInputStream fis = null;

        try
        {
            File file = new File(context.getExternalFilesDir(null), filename);
            fis = new FileInputStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                Log.w("FileUtils", "File data: " + strLine);
                Toast.makeText(context, "File Data: " + strLine, Toast.LENGTH_SHORT).show();
            }
            in.close();
        } catch (Exception ex)
        {
            Log.e("FileUtils", "failed to load file", ex);
        } finally
        {
            try
            {
                if (null != fis) fis.close();
            } catch (IOException ex)
            {
            }
        }

        return;
    }


    public static boolean isExternalStorageReadOnly()
    {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState))
        {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable()
    {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState))
        {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), "testDB");
            excelToSQLite.importFromFile(filePath, new ExcelToSQLite.ImportListener()
            {
                @Override
                public void onStart()
                {
                    Log.d("database", "start");

                }

                @Override
                public void onCompleted(String dbName)
                {
                    Log.d("database", dbName);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("database", e.toString());

                }
            });
        }
    }
}


