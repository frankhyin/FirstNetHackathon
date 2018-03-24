package com.example.jennadeng.contextfirst;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BasicActivity extends MainActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_basic;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_basic;
    }

}
