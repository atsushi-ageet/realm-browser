package com.dd.realmsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.dd.realmbrowser.RealmBrowser;
import com.dd.realmsample.data.Address;
import com.dd.realmsample.data.Contact;
import com.dd.realmsample.data.RealmString;
import com.dd.realmsample.data.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String REALM_FILE_NAME = "db10";
    private RealmConfiguration mRealmConf;
    private TextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRealmConf = new RealmConfiguration.Builder(this)
                .name(REALM_FILE_NAME)
                .build();
        RealmBrowser.getInstance().addRealmConf(mRealmConf);

        mTxtTitle = (TextView) findViewById(R.id.txtTitle);
        findViewById(R.id.btnInsert).setOnClickListener(this);
        findViewById(R.id.btnRemove).setOnClickListener(this);
        findViewById(R.id.btnOpenFile).setOnClickListener(this);
        findViewById(R.id.btnOpenModel).setOnClickListener(this);

        updateTitle();

        RealmBrowser.showRealmFilesNotification(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsert:
                insertUsers(100);
                updateTitle();
                break;
            case R.id.btnRemove:
                removeAllUsers();
                updateTitle();
                break;
            case R.id.btnOpenFile:
                startRealmFilesActivity();
                break;
            case R.id.btnOpenModel:
                startRealmModelsActivity();
                break;
        }
    }

    private void updateTitle() {
        Realm realm = Realm.getInstance(mRealmConf);
        long size = realm.where(User.class).count();
        mTxtTitle.setText(String.format("Items in database: %d", size));
        realm.close();
    }

    private void removeAllUsers() {
        Realm realm = Realm.getInstance(mRealmConf);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(User.class);
            }
        });

        realm.close();
    }

    private void insertUsers(int count) {
        Realm realm = Realm.getInstance(mRealmConf);

        final List<User> userList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Address address = new Address();
            address.setLat(49.8397473);
            address.setLon(24.0233077);

            User user = new User();
            user.setName("Jon Doe " + i);
            user.setIsBlocked(Math.random() > 0.5);
            user.setAge(i);
            user.setAddress(address);

            RealmList<RealmString> emailList = new RealmList<>();
            for (int k = 0; k < 5; k++) {
                emailList.add(new RealmString("jondoe" + k + "@gmail.com"));
            }
            user.setEmailList(emailList);

            RealmList<Contact> contactList = new RealmList<>();
            for (int k = 0; k < 10; k++) {
                Contact contact = new Contact();
                contact.setId(k);
                contact.setName("Filip");
                contactList.add(contact);
            }
            user.setContactList(contactList);

            userList.add(user);
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(userList);
            }
        });

        realm.close();
    }

    private void startRealmFilesActivity() {
        RealmBrowser.startRealmFilesActivity(this);
    }

    private void startRealmModelsActivity() {
        RealmBrowser.startRealmModelsActivity(this, mRealmConf);
    }

}
