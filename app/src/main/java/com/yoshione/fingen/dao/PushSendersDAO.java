package com.yoshione.fingen.dao;

import android.content.Context;
import android.database.Cursor;

import com.yoshione.fingen.db.DbUtil;
import com.yoshione.fingen.interfaces.IAbstractModel;
import com.yoshione.fingen.interfaces.IDaoInheritor;
import com.yoshione.fingen.model.PushSender;
import com.yoshione.fingen.model.Sender;

import java.util.List;

public class PushSendersDAO extends BaseDAO implements AbstractDAO, IDaoInheritor {

    //<editor-fold desc="ref_Push_Senders">
    public static final String TABLE = "ref_Push_Senders";

    public static final String COL_PACKAGE_NAME = "PackageName";
    public static final String COL_IS_ACTIVE = "IsActive";

    public static final String[] ALL_COLUMNS = joinArrays(COMMON_COLUMNS, new String[]{
            COL_NAME, COL_PACKAGE_NAME, COL_IS_ACTIVE
    });

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE + " ("
            + COMMON_FIELDS +                   ", "
            + COL_NAME +                        " TEXT NOT NULL, "
            + COL_PACKAGE_NAME +                " TEXT NOT NULL, "
            + COL_IS_ACTIVE +                   " INTEGER NOT NULL, "
            + "UNIQUE (" + COL_NAME + ", " + COL_SYNC_DELETED + ") ON CONFLICT ABORT, "
            + "UNIQUE (" + COL_PACKAGE_NAME + ", " + COL_SYNC_DELETED + ") ON CONFLICT ABORT);";
    //</editor-fold>

    private static PushSendersDAO sInstance;

    public synchronized static PushSendersDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PushSendersDAO(context);
        }
        return sInstance;
    }

    private PushSendersDAO(Context context) {
        super(context, TABLE, ALL_COLUMNS);
        super.setDaoInheritor(this);
    }

    @Override
    public IAbstractModel createEmptyModel() {
        return new PushSender();
    }

    @Override
    public IAbstractModel cursorToModel(Cursor cursor) {
        return cursorToPushSender(cursor);
    }

    private PushSender cursorToPushSender(Cursor cursor) {
        PushSender sender = new PushSender();
        sender.setID(DbUtil.getLong(cursor, COL_ID));
        sender.setName(DbUtil.getString(cursor, COL_NAME));
        sender.setPackageName(DbUtil.getString(cursor, COL_PACKAGE_NAME));
        sender.setActive(DbUtil.getBoolean(cursor, COL_IS_ACTIVE));

        return sender;
    }

    @SuppressWarnings("unchecked")
    public List<PushSender> getAllPushSenders() {
        return (List<PushSender>) getItems(getTableName(), null, null, null, COL_NAME, null);
    }

    public PushSender getPushSenderByID(long id) {
        return (PushSender) getModelById(id);
    }

    public PushSender getPushSenderByPackageName(String packageName) {
        List<PushSender> senders;
        try {
            senders = getAllPushSenders();
        } catch (Exception e) {
            return new PushSender();
        }

        for (PushSender sender : senders) {
            if (sender.getPackageName().toLowerCase().trim().equals(packageName.toLowerCase().trim())) {
                return sender;
            }
        }

        return new PushSender();
    }

    @Override
    public List<?> getAllModels() {
        return getAllPushSenders();
    }
}
