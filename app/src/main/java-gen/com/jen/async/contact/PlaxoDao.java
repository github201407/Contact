package com.jen.async.contact;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.jen.async.contact.Plaxo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PLAXO".
*/
public class PlaxoDao extends AbstractDao<Plaxo, Long> {

    public static final String TABLENAME = "PLAXO";

    /**
     * Properties of entity Plaxo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Userid = new Property(1, String.class, "userid", false, "USERID");
        public final static Property Regtime = new Property(2, String.class, "regtime", false, "REGTIME");
        public final static Property Actiontype = new Property(3, String.class, "actiontype", false, "ACTIONTYPE");
        public final static Property Updatetime = new Property(4, String.class, "updatetime", false, "UPDATETIME");
        public final static Property States = new Property(5, String.class, "states", false, "STATES");
        public final static Property Name = new Property(6, String.class, "name", false, "NAME");
        public final static Property Phone = new Property(7, String.class, "phone", false, "PHONE");
        public final static Property Workphone = new Property(8, String.class, "workphone", false, "WORKPHONE");
        public final static Property Homephone = new Property(9, String.class, "homephone", false, "HOMEPHONE");
        public final static Property Photo = new Property(10, String.class, "photo", false, "PHOTO");
        public final static Property Email = new Property(11, String.class, "email", false, "EMAIL");
        public final static Property Workemail = new Property(12, String.class, "workemail", false, "WORKEMAIL");
        public final static Property Address = new Property(13, String.class, "address", false, "ADDRESS");
        public final static Property Groups = new Property(14, String.class, "groups", false, "GROUPS");
        public final static Property Custom = new Property(15, String.class, "custom", false, "CUSTOM");
        public final static Property Companynumber = new Property(16, String.class, "companynumber", false, "COMPANYNUMBER");
        public final static Property Departmentnumber = new Property(17, String.class, "departmentnumber", false, "DEPARTMENTNUMBER");
        public final static Property ShortName = new Property(18, String.class, "shortName", false, "SHORT_NAME");
        public final static Property Ou = new Property(19, String.class, "ou", false, "OU");
        public final static Property Titlenumber = new Property(20, String.class, "titlenumber", false, "TITLENUMBER");
        public final static Property Raw_contacts_version = new Property(21, Integer.class, "raw_contacts_version", false, "RAW_CONTACTS_VERSION");
        public final static Property Server_version = new Property(22, Integer.class, "server_version", false, "SERVER_VERSION");
        public final static Property Creation_time = new Property(23, Long.class, "creation_time", false, "CREATION_TIME");
        public final static Property Contact_id = new Property(24, String.class, "contact_id", false, "CONTACT_ID");
        public final static Property Raw_contact_id = new Property(25, String.class, "raw_contact_id", false, "RAW_CONTACT_ID");
        public final static Property Org = new Property(26, String.class, "org", false, "ORG");
    };


    public PlaxoDao(DaoConfig config) {
        super(config);
    }
    
    public PlaxoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PLAXO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USERID\" TEXT," + // 1: userid
                "\"REGTIME\" TEXT," + // 2: regtime
                "\"ACTIONTYPE\" TEXT," + // 3: actiontype
                "\"UPDATETIME\" TEXT," + // 4: updatetime
                "\"STATES\" TEXT," + // 5: states
                "\"NAME\" TEXT NOT NULL ," + // 6: name
                "\"PHONE\" TEXT," + // 7: phone
                "\"WORKPHONE\" TEXT," + // 8: workphone
                "\"HOMEPHONE\" TEXT," + // 9: homephone
                "\"PHOTO\" TEXT," + // 10: photo
                "\"EMAIL\" TEXT," + // 11: email
                "\"WORKEMAIL\" TEXT," + // 12: workemail
                "\"ADDRESS\" TEXT," + // 13: address
                "\"GROUPS\" TEXT," + // 14: groups
                "\"CUSTOM\" TEXT," + // 15: custom
                "\"COMPANYNUMBER\" TEXT," + // 16: companynumber
                "\"DEPARTMENTNUMBER\" TEXT," + // 17: departmentnumber
                "\"SHORT_NAME\" TEXT," + // 18: shortName
                "\"OU\" TEXT," + // 19: ou
                "\"TITLENUMBER\" TEXT," + // 20: titlenumber
                "\"RAW_CONTACTS_VERSION\" INTEGER," + // 21: raw_contacts_version
                "\"SERVER_VERSION\" INTEGER," + // 22: server_version
                "\"CREATION_TIME\" INTEGER," + // 23: creation_time
                "\"CONTACT_ID\" TEXT," + // 24: contact_id
                "\"RAW_CONTACT_ID\" TEXT," + // 25: raw_contact_id
                "\"ORG\" TEXT);"); // 26: org
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PLAXO\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Plaxo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(2, userid);
        }
 
        String regtime = entity.getRegtime();
        if (regtime != null) {
            stmt.bindString(3, regtime);
        }
 
        String actiontype = entity.getActiontype();
        if (actiontype != null) {
            stmt.bindString(4, actiontype);
        }
 
        String updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindString(5, updatetime);
        }
 
        String states = entity.getStates();
        if (states != null) {
            stmt.bindString(6, states);
        }
        stmt.bindString(7, entity.getName());
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(8, phone);
        }
 
        String workphone = entity.getWorkphone();
        if (workphone != null) {
            stmt.bindString(9, workphone);
        }
 
        String homephone = entity.getHomephone();
        if (homephone != null) {
            stmt.bindString(10, homephone);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(11, photo);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(12, email);
        }
 
        String workemail = entity.getWorkemail();
        if (workemail != null) {
            stmt.bindString(13, workemail);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(14, address);
        }
 
        String groups = entity.getGroups();
        if (groups != null) {
            stmt.bindString(15, groups);
        }
 
        String custom = entity.getCustom();
        if (custom != null) {
            stmt.bindString(16, custom);
        }
 
        String companynumber = entity.getCompanynumber();
        if (companynumber != null) {
            stmt.bindString(17, companynumber);
        }
 
        String departmentnumber = entity.getDepartmentnumber();
        if (departmentnumber != null) {
            stmt.bindString(18, departmentnumber);
        }
 
        String shortName = entity.getShortName();
        if (shortName != null) {
            stmt.bindString(19, shortName);
        }
 
        String ou = entity.getOu();
        if (ou != null) {
            stmt.bindString(20, ou);
        }
 
        String titlenumber = entity.getTitlenumber();
        if (titlenumber != null) {
            stmt.bindString(21, titlenumber);
        }
 
        Integer raw_contacts_version = entity.getRaw_contacts_version();
        if (raw_contacts_version != null) {
            stmt.bindLong(22, raw_contacts_version);
        }
 
        Integer server_version = entity.getServer_version();
        if (server_version != null) {
            stmt.bindLong(23, server_version);
        }
 
        Long creation_time = entity.getCreation_time();
        if (creation_time != null) {
            stmt.bindLong(24, creation_time);
        }
 
        String contact_id = entity.getContact_id();
        if (contact_id != null) {
            stmt.bindString(25, contact_id);
        }
 
        String raw_contact_id = entity.getRaw_contact_id();
        if (raw_contact_id != null) {
            stmt.bindString(26, raw_contact_id);
        }
 
        String org = entity.getOrg();
        if (org != null) {
            stmt.bindString(27, org);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Plaxo readEntity(Cursor cursor, int offset) {
        Plaxo entity = new Plaxo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // regtime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // actiontype
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // updatetime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // states
            cursor.getString(offset + 6), // name
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // phone
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // workphone
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // homephone
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // photo
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // email
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // workemail
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // address
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // groups
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // custom
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // companynumber
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // departmentnumber
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // shortName
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // ou
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // titlenumber
            cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21), // raw_contacts_version
            cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22), // server_version
            cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23), // creation_time
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // contact_id
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // raw_contact_id
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26) // org
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Plaxo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRegtime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setActiontype(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUpdatetime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStates(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setName(cursor.getString(offset + 6));
        entity.setPhone(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setWorkphone(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setHomephone(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPhoto(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setEmail(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setWorkemail(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setAddress(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setGroups(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCustom(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCompanynumber(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setDepartmentnumber(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setShortName(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setOu(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTitlenumber(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setRaw_contacts_version(cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21));
        entity.setServer_version(cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22));
        entity.setCreation_time(cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23));
        entity.setContact_id(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setRaw_contact_id(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setOrg(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Plaxo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Plaxo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
