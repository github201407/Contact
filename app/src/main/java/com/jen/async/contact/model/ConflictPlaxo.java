package com.jen.async.contact.model;

import com.jen.async.contact.Plaxo;

/**
 * Created by Administrator on 2016/1/7.
 */
public class ConflictPlaxo {
    String contact_id;
    String raw_contact_id;
    Plaxo local;
    Plaxo app;

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getRaw_contact_id() {
        return raw_contact_id;
    }

    public void setRaw_contact_id(String raw_contact_id) {
        this.raw_contact_id = raw_contact_id;
    }

    public Plaxo getLocal() {
        return local;
    }

    public void setLocal(Plaxo local) {
        this.local = local;
    }

    public Plaxo getApp() {
        return app;
    }

    public void setApp(Plaxo app) {
        this.app = app;
    }
}
