package com.dd.realmsample;

import io.realm.Realm;

/**
 * Created by atsushi on 2017/04/11.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
