package com.yoshione.fingen.backup;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.JobRequest;
import com.yoshione.fingen.BuildConfig;
import com.yoshione.fingen.DBHelper;
import com.yoshione.fingen.FGApplication;
import com.yoshione.fingen.FgConst;
import com.yoshione.fingen.csv.CsvImporter;
import com.yoshione.fingen.dao.DepartmentsDAO;
import com.yoshione.fingen.dao.TransactionsDAO;
import com.yoshione.fingen.dropbox.DeleteTask;
import com.yoshione.fingen.dropbox.DownloadTask;
import com.yoshione.fingen.dropbox.DropboxClient;
import com.yoshione.fingen.dropbox.UploadTask;
import com.yoshione.fingen.interfaces.IOnComplete;
import com.yoshione.fingen.model.Department;
import com.yoshione.fingen.model.Transaction;
import com.yoshione.fingen.utils.FileUtils;
import com.yoshione.fingen.utils.PrefUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by slv on 27.10.2017.
 * /
 */

public class RestoreJob extends DailyJob {

    public static final String TAG = "job_restore_tag";

    public static int schedule(Long l1, Long l2) {
        // schedule between 2 and 3 AM
        return DailyJob.schedule(new JobRequest.Builder(TAG),
                TimeUnit.HOURS.toMillis(l1) + TimeUnit.MINUTES.toMillis(l2) + 60000,
                TimeUnit.HOURS.toMillis(l1) + TimeUnit.MINUTES.toMillis(l2) + 120000);
    }

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(Params params) {
//        if (!BuildConfig.FLAVOR.equals("nd")) {
            Context context = FGApplication.getContext();
            SharedPreferences dropboxPrefs = context.getSharedPreferences("com.yoshione.fingen.dropbox", Context.MODE_PRIVATE);
            String token = dropboxPrefs.getString("dropbox-token", null);
            /****************/

            List<Department> departments;
            try {
                departments = DepartmentsDAO.getInstance(context).getAllDepartments();
                String DefDep = PrefUtils.getDefaultDepartment(context).getName();
                //File zip = DBHelper.getInstance(context).backupDB(true);

                /***********/
                for (int i = 0; i < departments.size(); i++) {
                    Department CurDep = departments.get(i);
                    String CurDepName = CurDep.getName();
                    Boolean YN = CurDepName.equals(DefDep);
                    if (!YN) {
                        try {
                            /****************/

                            File CurrLocalCSV = new File(FileUtils.getExtFingenBackupFolder() + "CSV_Export_" + CurDep + ".csv");

//                            File CSVBackupFile = FileUtils.zip(new String[]{context.getDatabasePath("fingen.db").toString()}, path);
                            if (CurrLocalCSV.exists()) {
//                     try {
                                CurrLocalCSV.delete();
                            }
/*                     catch (IOException ee) {
                       ee.printStackTrace();
                     }
                    }*/
                            /************/

                            if (token != null) {
                                /************/
//                csvImporter.setmCsvImportProgressChangeListener(myCsvImportProgressChangeListener);

                                /************/

                                Thread tttt = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DownloadTask(DropboxClient.getClient(token), CurrLocalCSV, new IOnComplete() {
                                            @Override
                                            public void onComplete() {
                                            }
                                        }).execute();
                                    }
                                });
                                tttt.start();
                                try {
                                    tttt.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                final CsvImporter csvImporter = new CsvImporter(context, CurrLocalCSV.toString(), 0, true);

                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            csvImporter.loadFingenBackupCSV();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                t.start();
                                try {
                                    t.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                /*************/
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

//        }
        return DailyJobResult.SUCCESS;
    }
}
