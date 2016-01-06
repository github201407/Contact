package com.jen.async.contact.Utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.jen.async.contact.Plaxo;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/1/6.
 */
public class ContactUtils {

    private static final String TAG = "ContactUtils";
    private static HashMap<String,Plaxo> plaxos = new LinkedHashMap<>();/*contactID*/
    private static HashMap<String,Plaxo> rawplaxos = new LinkedHashMap<>();/*rawcontactID*/

    public static HashMap<String, Plaxo> getPlaxos(Context context) {
        doContacts(context);
        doRawContacts(context);
        doData(context);
        return rawplaxos;
    }

    /**
     * _ID,PHOTO_ID
     * @param context
     */
    public static void doContacts(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        Cursor query = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.PHOTO_ID
                }, null,
                null, null);
        if (query == null) return;
        Plaxo plaxo;
        while (query.moveToNext()){
            String id = query.getString(0);
            long photoId = query.getLong(1);
            Log.e(TAG, "raw_contact_id: " + id + ",photoId:" + photoId);
            plaxo = new Plaxo();
            plaxo.setContact_id(id);
            plaxo.setPhoto(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, photoId).toString());
            plaxos.put(id, plaxo);
        }
        query.close();
    }

    /**
     * _ID,version,contact_id,display_name,creation_time
     * @param context
     */
    public static void doRawContacts(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        Cursor query = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
                new String[]{
                        ContactsContract.RawContacts._ID,
                        ContactsContract.RawContacts.CONTACT_ID,
                        ContactsContract.RawContacts.VERSION,
                        ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
//                        "creation_time"
                },
                null, null, null);
        if (query == null) return;
        Plaxo plaxo;
        while (query.moveToNext()){
            String id = query.getString(0);
            String contact_id = query.getString(1);
            int version = query.getInt(2);
            String name = query.getString(3);
            long creation_time = 0;//query.getLong(4);
            Log.e(TAG, "raw_contact_id: " + id + ",contact_id:" + contact_id + ",version:" + version + ",name:" + name + ",creation_time:" + creation_time);
            plaxo = plaxos.get(contact_id);
            if(plaxo != null){
                plaxo.setRaw_contact_id(id);
                plaxo.setRaw_contacts_version(version);
                plaxo.setName(name);
//                plaxo.creation_time = creation_time;
                rawplaxos.put(id, plaxo);
            }
        }
        plaxos.clear();
        query.close();
    }


    public static void doData(Context context){
        Plaxo plaxo;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                new String[]{
                        ContactsContract.Data.RAW_CONTACT_ID,/*0*/
                        ContactsContract.Data.MIMETYPE,      /*1*/
                        ContactsContract.Data.DATA1,         /*2*/
                        ContactsContract.Data.DATA2,         /*3*/
                        ContactsContract.Data.DATA3,         /*4*/
                        ContactsContract.Data.DATA4          /*5*/
                },
                null, null, null);
        if(cursor == null) return;
        while (cursor.moveToNext()){
            String raw_contact_id = cursor.getString(0);
            String mimeType = cursor.getString(1);
            Log.e(TAG, "testQueryPlaxoByRawContactIdFromData:mimeType= " + mimeType );
            plaxo = rawplaxos.get(raw_contact_id);
            if(plaxo == null) continue;
            if(mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)){
                String phone = cursor.getString(2);
                int type = cursor.getInt(3);
                if(type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME){
                    plaxo.setHomephone(phone);
                } else if(type == ContactsContract.CommonDataKinds.Phone.TYPE_WORK){
                    plaxo.setWorkphone(phone);
                } else {
                    plaxo.setPhone(phone);
                }
            }else if(mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)){
                String email = cursor.getString(2);
                int type = cursor.getInt(3);
                if(type == ContactsContract.CommonDataKinds.Email.TYPE_WORK){
                    plaxo.setWorkemail(email);
                } else {
                    plaxo.setEmail(email);
                }
            } else if(mimeType.equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)){
                plaxo.setOrg(cursor.getString(2));
            }else if(mimeType.equals(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)){
                plaxo.setAddress(cursor.getString(2));
            }else if(mimeType.equals(ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)){
                String temp = plaxo.getGroups();
                if(TextUtils.isEmpty(temp)){
                    temp += cursor.getString(2);
                } else {
                    temp += "," + cursor.getString(2);
                }
                plaxo.setGroups(temp);
            }else if(mimeType.equals(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)){
                plaxo.setCustom(cursor.getString(2));
            }
        }
        cursor.close();
    }

}
