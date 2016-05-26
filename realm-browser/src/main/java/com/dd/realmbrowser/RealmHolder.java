package com.dd.realmbrowser;

import io.realm.RealmModel;

import java.lang.reflect.Field;

class RealmHolder {

    private static final RealmHolder sInstance = new RealmHolder();
    private RealmModel mObject;
    private Field mField;

    public static RealmHolder getInstance() {
        return sInstance;
    }

    public void setObject(RealmModel object) {
        mObject = object;
    }

    public RealmModel getObject() {
        return mObject;
    }

    public Field getField() {
        return mField;
    }

    public void setField(Field field) {
        mField = field;
    }
}
