package com.jen.async.contact;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.EditText;

import com.jen.async.contact.Utils.ContactUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private SQLiteDatabase db;
    private EditText editText;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    public static final String TAG = "DaoExample";

    public ApplicationTest() {
        super(Application.class);
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
        String textColumn = NoteDao.Properties.Display_name.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = db.query(getNoteDao().getTablename(), getNoteDao().getAllColumns(), null, null, null, null, orderBy);
        assertNotNull(cursor);
        List<Note> notes = getNoteDao().loadAll();
        for (Note note : notes) {
            Log.d(TAG, "testDBQueryAllColumn() returned: " + note);
        }
        assertTrue(notes.size() >= 2);
        assertEquals("name", notes.get(0).getDisplay_name());
        assertEquals("name2", notes.get(1).getDisplay_name());
    }

    public void testDBInsertNode(){
        Note note = new Note(null,"2","name2","32","2","1234562","1234562");
        long insertRowId = getNoteDao().insert(note);
        assertEquals(1,insertRowId);
    }

    public void testSearchByContactId() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = getNoteDao().queryBuilder()
                .where(NoteDao.Properties.Contact_id.eq("2"))
                .orderAsc(NoteDao.Properties.Contact_id)
                .build();

//      查询结果以 List 返回
        List notes = query.list();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        for (Object note : notes) {
            Note note1 = (Note) note;
            assertEquals("1234562", note1.getCreation_time());
        }
    }

    public void testDeleteByContactId(){
        getNoteDao().deleteByKey((long) 2);
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
        HashMap<String, Plaxo> plaxos = ContactUtils.getPlaxos(getApplication());
        for(Iterator<Map.Entry<String, Plaxo>> iterator = plaxos.entrySet().iterator();iterator.hasNext();){
            Map.Entry<String, Plaxo> next = iterator.next();
            Log.d(TAG, "testQueryAllContact: " + next.getKey() + ",Plaxo:" + next.getValue());

        }

    }
}