package com.example.taxi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class start_screen_taxi extends AppCompatActivity implements NavigationView .OnNavigationItemSelectedListener {

    DrawerLayout dLay;
    NavigationView nView;
    Toolbar tBar;
    MapView mapview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("55f218b2-9780-4207-9190-5ff3f995c22a");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_start_screen_taxi);
        
        mapview = (MapView)findViewById(R.id.mapview);
        dLay = findViewById(R.id.drawerStartLayout);
        nView = findViewById(R.id.navViewMenu);
        tBar = (Toolbar) findViewById(R.id.toolbarStart);

        nView.bringToFront();
        ActionBarDrawerToggle t;
        t = new ActionBarDrawerToggle(start_screen_taxi.this,
                dLay,
                tBar,
                R.string.nav_d_open,
                R.string.nav_d_close);
        dLay.addDrawerListener(t);
        t.syncState();

        mapview.getMap().move(
                new CameraPosition(new Point(55.04409581161974, 82.91760313468653), 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        mapview.getMap().getMapObjects().addPlacemark(new Point(55.04409581161974, 82.91760313468653), ImageProvider.fromResource(this, R.drawable.avatar));
        mapview.getMap().getMapObjects().addPlacemark(new Point(55.04463315565387, 82.91667363266208), ImageProvider.fromResource(this, R.drawable.car));
        mapview.getMap().getMapObjects().addPlacemark(new Point(55.04236817123143, 82.91941369727685), ImageProvider.fromResource(this, R.drawable.car));
        mapview.getMap().getMapObjects().addPlacemark(new Point(55.04285058782985, 82.91674208651334), ImageProvider.fromResource(this, R.drawable.car));

        nView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onBackPressed(){
        if(dLay.isDrawerOpen(GravityCompat.START)){
            dLay.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.itemMenuSetting):
                Intent i1 = new Intent(this, settings_taxi.class);
                startActivity(i1);
                break;
            case (R.id.itemMenuHistory):
                Intent i2 = new Intent(this, history_taxi.class);
                startActivity(i2);
                break;
        }
        dLay.closeDrawer(GravityCompat.START); return true;
    }
}