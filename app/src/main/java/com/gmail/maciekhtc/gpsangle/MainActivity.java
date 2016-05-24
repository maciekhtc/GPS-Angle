package com.gmail.maciekhtc.gpsangle;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {       //implements OnMapReadyCallback

    Button addMarkerButton;
    Button calculateButton;
    GoogleMap mMap;
    LinkedHashMap<Marker,Location> mPoints = new LinkedHashMap<Marker,Location>();
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapka);
        mapFragment.getMapAsync(this);

        textView1 = (TextView) findViewById(R.id.textView1);


        addMarkerButton = (Button) findViewById(R.id.addMarkerButton);
        addMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarkerOptions newMarker = new MarkerOptions();
                Location newLoc = mMap.getMyLocation();
                LatLng newLatLng = MyUtils.latlngFromLocation(newLoc);
                newMarker.position(newLatLng);
                newMarker.draggable(true);
                newMarker.title(newLatLng.toString());
                mPoints.put(mMap.addMarker(newMarker), newLoc);
                textView1.setText("przesunieto");
            }
        });


        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //action                                            !!
                Location[] locArr = mPoints.values().toArray(new Location[mPoints.values().size()]);
                textView1.setText("Pole= "+ MyUtils.calculateField(locArr));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 150, null);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(MyUtils.latlngFromLocation(location)));
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                marker.setTitle(marker.getPosition().toString());
                Location markerlocation = mPoints.get(marker);
                markerlocation.setLatitude(marker.getPosition().latitude);
                markerlocation.setLongitude(marker.getPosition().longitude);
            }
        });
    }

}
