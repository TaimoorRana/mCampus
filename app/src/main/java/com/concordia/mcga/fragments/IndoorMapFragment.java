package com.concordia.mcga.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.concordia.mcga.activities.R;

/**
 * Created by Sylvain on 2/12/2017.
 */

public class IndoorMapFragment extends Fragment implements View.OnClickListener {

    //Components
    private WebView leafletView;
    private LinearLayout floorButtonContainer;
    private Spinner floorSelectSpinner;
    private Button testPathButton;

    //State
    private boolean pageLoaded = false;
    private boolean pathsDrawn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.indoor_map_fragment, container, false);

        //Init Webview
        leafletView = (WebView) view.findViewById(R.id.leafletview);
        leafletView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pageLoaded = true;
                //Initialize a building, normally this would be done dynamically
                initializeHBuilding();
            }
        });

        WebSettings webSettings = leafletView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        leafletView.loadUrl("file:///android_asset/leaflet.html");

        //Floor Select Spinner
        floorButtonContainer = (LinearLayout) view.findViewById(R.id.floorButtonContainer);

        //Test Path Button
        testPathButton = (Button) view.findViewById(R.id.testPathButton);
        testPathButton.setOnClickListener(this);

        return view;
    }

    public void generatePath() {
        //Pathfinder stuff goes here
    }

    public void initializeHBuilding() {
        //Show Default Floor
        leafletView.evaluateJavascript("loadMapImage('floormaps/H/4.png')", null);

        //Add Floor Buttons
        Button h1 = new Button(getContext());
        h1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.indoor_floor_button, null));
        h1.setText("1/2");
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageLoaded)
                    leafletView.evaluateJavascript("loadMapImage('floormaps/H/1-2.png')", null);
            }
        });

        Button h4 = new Button(getContext());
        h4.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.indoor_floor_button, null));
        h4.setText("4");
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageLoaded)
                    leafletView.evaluateJavascript("loadMapImage('floormaps/H/4.png')", null);
            }
        });

        floorButtonContainer.addView(h4);
        floorButtonContainer.addView(h1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testPathButton:
                if (pathsDrawn) {
                    pathsDrawn = false;
                    leafletView.evaluateJavascript("clearLayers()", null);
                } else {
                    pathsDrawn = true;
                    leafletView.evaluateJavascript("generatePath()", null);
                }
                break;
        }
    }
}