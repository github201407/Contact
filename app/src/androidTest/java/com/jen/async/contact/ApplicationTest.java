package com.jen.async.contact;

import android.app.Application;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.test.ApplicationTestCase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.jen.async.contact.Utils.ContactUtils;
import com.jen.async.contact.model.ConflictPlaxo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<App> {

    private SQLiteDatabase db;
    private EditText editText;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    public static final String TAG = "DaoExample";

    public ApplicationTest() {
        super(App.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        // 官方推荐将获取 DaoMaster 对象的方法放到 Application 层，这样将避免多次创建生成 Session 对象
        setupDatabase();
        // 获取 NoteDao 对象
        getNoteDao();
    }

    public void testApplicationNotNull(){
        Application application = getApplication();
        assertNotNull(application);
    }

    public void testDBQueryAllColumn(){
//        String textColumn = NoteDao.Properties.Display_name.columnName;
//        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
//        cursor = db.query(getNoteDao().getTablename(), getNoteDao().getAllColumns(), null, null, null, null, orderBy);
//        assertNotNull(cursor);
//        List<Note> notes = getNoteDao().loadAll();
//        for (Note note : notes) {
//            Log.d(TAG, "testDBQueryAllColumn() returned: " + note);
//        }
//        assertTrue(notes.size() >= 2);
//        assertEquals("name", notes.get(0).getDisplay_name());
//        assertEquals("name2", notes.get(1).getDisplay_name());
    }

    public void testDBInsertNode(){
//        Note note = new Note(null,"2","name2","32","2","1234562","1234562");
//        long insertRowId = getNoteDao().insert(note);
//        assertEquals(1,insertRowId);
    }

    public void testSearchByContactId() {
//        // Query 类代表了一个可以被重复执行的查询
//        Query query = getNoteDao().queryBuilder()
//                .where(NoteDao.Properties.Contact_id.eq("2"))
//                .orderAsc(NoteDao.Properties.Contact_id)
//                .build();
//
////      查询结果以 List 返回
//        List notes = query.list();
//        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
//        QueryBuilder.LOG_SQL = true;
//        QueryBuilder.LOG_VALUES = true;
//        for (Object note : notes) {
//            Note note1 = (Note) note;
//            assertEquals("1234562", note1.getCreation_time());
//        }
    }

    public void testDeleteByContactId(){
//        getNoteDao().deleteByKey((long) 2);
    }


    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplication(), "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private NoteDao getNoteDao() {
        return null;
    }

    public void testQueryAllContact(){
        App app = getApplication();
        HashMap<String, Plaxo> plaxos = ContactUtils.getPlaxos(getApplication());
        for(Iterator<Map.Entry<String, Plaxo>> iterator = plaxos.entrySet().iterator();iterator.hasNext();){
            Map.Entry<String, Plaxo> next = iterator.next();
            Plaxo value = next.getValue();
            Log.d(TAG, "testQueryAllContact: " + next.getKey() + ",Plaxo:" + value);
            app.getPlaxoDao().insert(value);
        }
        assertEquals(3, plaxos.size());

    }

    public void testQueryTheSameContactVersionIsChange(){
        HashMap<String, Plaxo> localMap = ContactUtils.getPlaxos(getApplication());
        ArrayList<Plaxo> sameList = new ArrayList<>();
        ArrayList<Plaxo> dellList = new ArrayList<>();
        ArrayList<Plaxo> updateList = new ArrayList<>();
        ArrayList<Plaxo> newList = new ArrayList<>();
//        HashMap<String, Plaxo> appMap = new LinkedHashMap<>();
        App app = getApplication();
        List<Plaxo> appPlaxos = app.getPlaxoDao().loadAll();
        Plaxo localPlaxo;
        for (Plaxo plaxo : appPlaxos) {
            String raw_contact_id = plaxo.getRaw_contact_id();
            if(TextUtils.isEmpty(raw_contact_id)) continue;
            localPlaxo = localMap.get(raw_contact_id);
            if(localPlaxo == null){
                dellList.add(plaxo);/*本地已删除*/
            } else {
                int version = localPlaxo.getRaw_contacts_version() - plaxo.getRaw_contacts_version();
                if(version > 0){/* 本地有更新 */
                    updateList.add(localPlaxo);
                } else if(version == 0){/* 不变 */
                    sameList.add(plaxo);
                }
            }
        }
        for (Plaxo plaxo : sameList) {
            localMap.remove(plaxo.getRaw_contact_id());
            appPlaxos.remove(plaxo);
        }
        Collection<Plaxo> values = localMap.values();
        newList.addAll(values);/* 本地新建 */
//        newList.addAll(appPlaxos);/* 服务器新建 */

        assertFalse(dellList.isEmpty());
        assertFalse(sameList.isEmpty());
        assertFalse(newList.isEmpty());
        assertFalse(appPlaxos.isEmpty());
        assertFalse(updateList.isEmpty());


    }

    public void testAppAndLocalChangeWithEqual(){
        ArrayList<Plaxo> allPlaxos = new ArrayList<>();
        ArrayList<ConflictPlaxo> samePlaxos = new ArrayList<>();

        HashMap<String, Plaxo> localMap = ContactUtils.getPlaxos(getApplication());
        Collection<Plaxo> localPlaxos = localMap.values();
        App app = getApplication();
        List<Plaxo> appPlaxos = app.getPlaxoDao().loadAll();

        ConflictPlaxo conflictPlaxo;
        for (Plaxo appPlaxo : appPlaxos) {
            String appName = appPlaxo.getName();
            String appPhone = appPlaxo.getPhone();
            String appEmail = appPlaxo.getEmail();
            for(Plaxo localPlaxo: localPlaxos){
                if (appName.equals(localPlaxo.getName())
                        || appPhone.equals(localPlaxo.getPhone())) {
                    conflictPlaxo = new ConflictPlaxo();
                    conflictPlaxo.setApp(appPlaxo);
                    conflictPlaxo.setLocal(localPlaxo);
                    samePlaxos.add(conflictPlaxo);/*相同*/
                }
            }
        }

        Plaxo appPlaxo;
        Plaxo localPlaxo;
        for(ConflictPlaxo conflict: samePlaxos){/* TODO: 设置处理机制，处理冲突*/
            appPlaxo = conflict.getApp();
            localPlaxo = conflict.getLocal();
            appPlaxos.remove(appPlaxo);
            localPlaxos.remove(localPlaxo);
            dealConflictPlaxo(conflict, allPlaxos);
        }

        assertFalse(samePlaxos.isEmpty());
        assertFalse(localPlaxos.isEmpty());
        assertFalse(appPlaxos.isEmpty());

        /* TODO：本地新增，增加到服务器*/
//        localPlaxos;
//        for(Plaxo plaxo: localPlaxos)
//            app.getPlaxoDao().insert(plaxo);

        /* TODO：服务器新增，增加到本地*/
//        appPlaxos;
//        for(Plaxo plaxo: appPlaxos){
//            Log.d(TAG, "testEqualLocalAndApp: " + plaxo.toString());
//            addPlaxo2LocalContact(plaxo);
//        }
    }

    public void testEqualLocalAndApp(){
        ArrayList<Plaxo> allPlaxos = new ArrayList<>();
        ArrayList<ConflictPlaxo> samePlaxos = new ArrayList<>();

        HashMap<String, Plaxo> localMap = ContactUtils.getPlaxos(getApplication());
        Collection<Plaxo> localPlaxos = localMap.values();
        App app = getApplication();
        List<Plaxo> appPlaxos = app.getPlaxoDao().loadAll();

        ConflictPlaxo conflictPlaxo;
        for (Plaxo appPlaxo : appPlaxos) {
            String appName = appPlaxo.getName();
            String appPhone = appPlaxo.getPhone();
            String appEmail = appPlaxo.getEmail();
            for(Plaxo localPlaxo: localPlaxos){
                if (appName.equals(localPlaxo.getName())
                         || appPhone.equals(localPlaxo.getPhone())
                         || appEmail.equals(localPlaxo.getEmail())) {
                    conflictPlaxo = new ConflictPlaxo();
                    conflictPlaxo.setApp(appPlaxo);
                    conflictPlaxo.setLocal(localPlaxo);
                     samePlaxos.add(conflictPlaxo);/*相同*/
                 }
             }
        }

        Plaxo appPlaxo;
        Plaxo localPlaxo;
        for(ConflictPlaxo conflict: samePlaxos){/* TODO: 设置处理机制，处理冲突*/
            appPlaxo = conflict.getApp();
            localPlaxo = conflict.getLocal();
            appPlaxos.remove(appPlaxo);
            localPlaxos.remove(localPlaxo);
            dealConflictPlaxo(conflict, allPlaxos);
        }

        /* TODO：本地新增，增加到服务器*/
//        localPlaxos;
        for(Plaxo plaxo: localPlaxos)
            app.getPlaxoDao().insert(plaxo);

        /* TODO：服务器新增，增加到本地*/
//        appPlaxos;
        for(Plaxo plaxo: appPlaxos){
            Log.d(TAG, "testEqualLocalAndApp: " + plaxo.toString());
            addPlaxo2LocalContact(plaxo);
        }


        assertTrue(samePlaxos.isEmpty());
    }


    public void testDealConflict(){
        ArrayList<Plaxo> allPlaxos = new ArrayList<>();
        Plaxo plaxo1 = new Plaxo();
        plaxo1.setName("Jen Chen");
        plaxo1.setPhone("15260663918");
        plaxo1.setEmail("15260663919@139.com");
        plaxo1.setAddress("Fuzhou China");
        plaxo1.setOrg("rj");
        plaxo1.setHomephone("15260663921");
        plaxo1.setWorkphone("15260002314");
        plaxo1.setWorkemail("15260663916@qq.com");
        plaxo1.setCustom("this is test contact.");
        plaxo1.setGroups("2");

        Plaxo plaxo = new Plaxo();
        plaxo.setName("Jen Chen");
        plaxo.setPhone("15260663918");
        plaxo.setEmail("15260663919@139.com");
        plaxo.setAddress("Fuzhou China");
        plaxo.setOrg("rj");
        plaxo.setHomephone("15260663921");
        plaxo.setWorkphone("15260002314");
        plaxo.setWorkemail("15260663916@qq.com");
        plaxo.setCustom("this is test contact.");
        plaxo.setGroups("3");

        //assertEquals(plaxo1.toString().hashCode(),plaxo.toString().hashCode());
        ConflictPlaxo conflict = new ConflictPlaxo();
        conflict.setApp(plaxo1);
        conflict.setLocal(plaxo);
        dealConflictPlaxo(conflict, allPlaxos);
        assertEquals(1, allPlaxos.size());
        Plaxo result = allPlaxos.get(0);
        assertEquals("rj", result.getOrg());
        assertEquals("2,3", result.getGroups());
    }

    public void dealConflictPlaxo(ConflictPlaxo conflict, ArrayList<Plaxo> allPlaxos){
        /* 以本地数据为标准,进行自动合并 */
        Plaxo appPlaxo = conflict.getApp();
        Plaxo localPlaxo = conflict.getLocal();
        Plaxo tempPlaxo = appPlaxo;
        if(appPlaxo.getName().equals(localPlaxo.getName())
                && appPlaxo.getEmail().equals(localPlaxo.getEmail())
                && appPlaxo.getWorkemail().equals(localPlaxo.getWorkemail())
                && appPlaxo.getPhone().equals(localPlaxo.getPhone())
                && appPlaxo.getWorkphone().equals(localPlaxo.getWorkphone())
                && appPlaxo.getHomephone().equals(localPlaxo.getHomephone())
                && appPlaxo.getAddress().equals(localPlaxo.getAddress())
                && appPlaxo.getOrg().equals(localPlaxo.getOrg())
                && appPlaxo.getCustom().equals(localPlaxo.getCustom())
                && appPlaxo.getGroups().equals(localPlaxo.getGroups())
                ){
            allPlaxos.add(appPlaxo);
        }else {
            if (!appPlaxo.getName().equals(localPlaxo.getName())) {
                tempPlaxo.setName(appPlaxo.getName());
            }
            if (!appPlaxo.getPhone().equals(localPlaxo.getPhone())) {
                tempPlaxo.setPhone(appPlaxo.getPhone() + "," + localPlaxo.getPhone());
            }
            if (!appPlaxo.getWorkphone().equals(localPlaxo.getWorkphone())) {
                tempPlaxo.setWorkphone(appPlaxo.getWorkphone() + "," + localPlaxo.getWorkphone());
            }
            if (!appPlaxo.getHomephone().equals(localPlaxo.getHomephone())) {
                tempPlaxo.setHomephone(appPlaxo.getHomephone() + "," + localPlaxo.getHomephone());
            }
            if (!appPlaxo.getEmail().equals(localPlaxo.getEmail())) {
                tempPlaxo.setEmail(appPlaxo.getEmail() + "," + localPlaxo.getEmail());
            }
            if (!appPlaxo.getWorkemail().equals(localPlaxo.getWorkemail())) {
                tempPlaxo.setWorkemail(appPlaxo.getWorkemail() + "," + localPlaxo.getWorkemail());
            }
            if (!appPlaxo.getAddress().equals(localPlaxo.getAddress())) {
                tempPlaxo.setAddress(appPlaxo.getAddress() + "," + localPlaxo.getAddress());
            }
            if (!appPlaxo.getOrg().equals(localPlaxo.getOrg())) {
                tempPlaxo.setOrg(appPlaxo.getOrg() + "," + localPlaxo.getOrg());
            }
            if (!appPlaxo.getCustom().equals(localPlaxo.getCustom())) {
                tempPlaxo.setCustom(appPlaxo.getCustom() + "," + localPlaxo.getCustom());
            }
            if (!appPlaxo.getGroups().equals(localPlaxo.getGroups())) {
                tempPlaxo.setGroups(appPlaxo.getGroups() + "," + localPlaxo.getGroups());
            }
            allPlaxos.add(tempPlaxo);
        }
    }
    
    public void testTwoPlaxoStringIsEqual(){
        Plaxo plaxo1 = new Plaxo();
        plaxo1.setName("hello1");
        plaxo1.setPhone("123456");
        plaxo1.setEmail("123@qq.com"); 
        Plaxo plaxo2 = new Plaxo();
        plaxo2.setName("hello1");
        plaxo2.setPhone("123456");
        plaxo2.setEmail("123@qq.com");
        assertEquals(plaxo1.toString(),plaxo2.toString());
        assertTrue(plaxo1.toString().hashCode() == plaxo2.toString().hashCode());
        assertFalse(plaxo1.hashCode() == plaxo2.hashCode());
        String str1 = "hello" + "12345" + "123@qq.com";
        String str2 = "hello" + "12345" + "123@qq.com";
        assertTrue(str1.hashCode() == str2.hashCode());
    }


    public void testInsertPlaxo2Local(){
//        Plaxo plaxo = new Plaxo();
//        plaxo.setName("Jen Chen");
//        plaxo.setPhone("15260663918");
//        plaxo.setEmail("15360663919@139.com");
//        plaxo.setAddress("Fuzhou China");
//        plaxo.setOrg("rj");
//        plaxo.setHomephone("15360663921");
//        plaxo.setWorkphone("15360002314");
//        plaxo.setWorkemail("15260663916@qq.com");
//        plaxo.setCustom("this is test contact.2");
//        plaxo.setGroups("1,2,3");

        Plaxo plaxo = new Plaxo();
        plaxo.setName("Jen1");
        plaxo.setPhone("152606");
        plaxo.setEmail("1536066@139.com");
        plaxo.setAddress("Fuzhou");
        plaxo.setOrg("r");
        plaxo.setHomephone("15360663");
        plaxo.setWorkphone("1536314");
        plaxo.setWorkemail("152663916@qq.com");
        plaxo.setCustom("this contact.2");
        plaxo.setGroups("1,2,3");

        addPlaxo2LocalContact(plaxo);

    }

    private void addPlaxo2LocalContact(Plaxo plaxo) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        //Phone Number
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                /*mobile*/
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, plaxo.getPhone())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        //Phone Number
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                /*home*/
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, plaxo.getHomephone())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());
        //Phone Number
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                /*work*/
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, plaxo.getWorkphone())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build());

        //Display name/Contact name
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, plaxo.getName())
                .build());

        //Email details
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                /*home*/
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, plaxo.getEmail())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                .build());

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                /*work*/
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, plaxo.getWorkemail())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());

        //Postal Address

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE )
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, plaxo.getAddress())

                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE )
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER)
                .build());


        //Organization details
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE )
                .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, plaxo.getOrg())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE )
                .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, plaxo.getTitlenumber())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE )
                .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, "0")
                .build());

        //Note
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE )
                .withValue(ContactsContract.CommonDataKinds.Note.NOTE, plaxo.getCustom())
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE )
                .build());

        for(String group : plaxo.getGroups().split(","))
        //Groups
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, group)
                .build());

        //IM details
        /*ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Im.DATA, "ImName")
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE )
                .withValue(ContactsContract.CommonDataKinds.Im.DATA5, "2")
                .build());*/
        try {
            ContentProviderResult[] res = getApplication().getContentResolver().applyBatch(
                    ContactsContract.AUTHORITY, ops);
            assertTrue(res.length>0);
        } catch (RemoteException | OperationApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}