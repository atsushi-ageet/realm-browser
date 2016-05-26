package com.dd.realmbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.realm.RealmConfiguration;
import io.realm.RealmModel;

import java.util.ArrayList;
import java.util.List;

public class RealmModelsActivity extends AppCompatActivity {

    private static final String EXTRAS_REALM_CONF_HASH = "EXTRAS_REALM_CONF_HASH";

    private RealmConfiguration mRealmConf;

    public static void start(@NonNull Activity activity, @NonNull RealmConfiguration realmConf) {
        Intent intent = new Intent(activity, RealmModelsActivity.class);
        intent.putExtra(EXTRAS_REALM_CONF_HASH, realmConf.hashCode());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_realm_list_view);

        int realmConfHash = getIntent().getIntExtra(EXTRAS_REALM_CONF_HASH, 0);
        mRealmConf = RealmBrowser.getInstance().findRealmConfByHashCode(realmConfHash);

        List<String> modelList = new ArrayList<>();
        for ( Class<? extends RealmModel> realmObject : mRealmConf.getRealmObjectClasses() ) {
            modelList.add(realmObject.getSimpleName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modelList);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked(position);
            }
        });
    }

    private void onItemClicked(int position) {
        RealmBrowserActivity.start(this, position, mRealmConf);
    }
}
