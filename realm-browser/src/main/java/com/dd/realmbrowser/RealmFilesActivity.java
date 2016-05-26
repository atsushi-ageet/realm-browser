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
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class RealmFilesActivity extends AppCompatActivity {

    private ArrayAdapter<RealmConfiguration> mAdapter;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RealmFilesActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_realm_list_view);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, RealmBrowser.getInstance().getRealmConfList());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked(position);
            }
        });
    }

    private void onItemClicked(int position) {
        try {
            RealmConfiguration realmConf = mAdapter.getItem(position);
            Realm realm = Realm.getInstance(realmConf);
            realm.close();
            RealmModelsActivity.start(this, realmConf);
        } catch (RealmMigrationNeededException e) {
            Toast.makeText(getApplicationContext(), "RealmMigrationNeededException", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Can't open realm instance", Toast.LENGTH_SHORT).show();
        }
    }
}
