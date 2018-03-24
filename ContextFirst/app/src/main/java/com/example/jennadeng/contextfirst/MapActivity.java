package com.example.jennadeng.contextfirst;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;


public class MapActivity extends MainActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate MapView from layout
        mMapView = (MapView) findViewById(R.id.mapView);
        // create a map with the BasemapType topographic
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16);
        // set the map to be displayed in this view
        mMapView.setMap(map);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }

    @Override
    public int getContentViewId() {
        return R.layout.map_view;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_map;
    }
}
