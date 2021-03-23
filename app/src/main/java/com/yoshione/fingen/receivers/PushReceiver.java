package com.yoshione.fingen.receivers;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import com.yoshione.fingen.dao.SendersDAO;
import com.yoshione.fingen.model.Sender;
import com.yoshione.fingen.model.Sms;

import java.util.Date;
import java.util.Objects;

public class PushReceiver extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver mReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.yoshione.fingen.NOTIFICATION_LISTENER");
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        Toast.makeText(this, sbn.getPackageName(), Toast.LENGTH_SHORT).show();
       if (!sbn.getPackageName().equals("")) {
           Sender sender = SendersDAO.getInstance(this).getSenderByPhoneNo(sbn.getPackageName());

           if (sender.getID() >= 0) {
//               String msgText = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString() + sbn.getNotification().extras.getCharSequence(Notification.EXTRA_SUB_TEXT).toString();
               String msgText = "";
//               Toast.makeText(this, msgText, Toast.LENGTH_SHORT).show();

               Bundle extras = sbn.getNotification().extras;
//               if (extras.containsKey("android.bigText")) {
/*               if (extras.containsKey("android.textLines")) {
                   if (extras.getCharSequence("android.summaryText") != null) {
                       msgText = Objects.requireNonNull(extras.getCharSequence("android.summaryText")).toString();
//                       Toast.makeText(this, msgText, Toast.LENGTH_SHORT).show();
                   }
               }


               if (extras.getString(Notification.EXTRA_BIG_TEXT) != null)
                   msgText += "BigText: " + extras.getString(Notification.EXTRA_BIG_TEXT).toString() + "\n";
               else
                   msgText += "BigText = null \n";

               if (extras.getString(Notification.EXTRA_SUMMARY_TEXT) != null)
                   msgText += "SummaryText: " + extras.getString(Notification.EXTRA_SUMMARY_TEXT).toString() + "\n";
               else
                   msgText += "SummaryText = null \n";

               if (extras.getString(Notification.EXTRA_INFO_TEXT) != null)
                   msgText += "InfoText: " + extras.getString(Notification.EXTRA_INFO_TEXT).toString() + "\n";
               else
                   msgText += "InfoText = null \n";

               if (extras.getString(Notification.EXTRA_TEXT) != null)
                   msgText += "Text: " + extras.getString(Notification.EXTRA_TEXT).toString() + "\n";
               else
                   msgText += "Text = null \n";

               if (extras.getString(Notification.EXTRA_SUB_TEXT) != null)
                   msgText += "SubText: " + extras.getString(Notification.EXTRA_SUB_TEXT).toString() + "\n";
               else
                   msgText += "SubText = null \n";

               if (extras.getString(Notification.EXTRA_TITLE) != null)
                   msgText += "Title:" + extras.getString(Notification.EXTRA_TITLE).toString() + "\n";
               else
                   msgText += "Title = null \n";

               if (extras.getString(Notification.EXTRA_TITLE_BIG) != null)
                   msgText += "Big Title:" + extras.getString(Notification.EXTRA_TITLE_BIG).toString() + "\n";
               else
                   msgText += "Big Title = null \n";

               msgText += "Fields... \n";

               CharSequence[] lines = extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
               if (lines != null) {
                   for (CharSequence line : lines) {
                       msgText += "line: " + line.toString() + "  \n";
                   }
               } else {
                   msgText += " no lines... \n";
               }*/

               if (extras.getString(Notification.EXTRA_TITLE) != null)
                  msgText += /*"Title:" +*/ extras.getString(Notification.EXTRA_TITLE).toString() + "\n";

               if (extras.getString(Notification.EXTRA_TITLE_BIG) != null)
                   if (!msgText.contains(extras.getString(Notification.EXTRA_TITLE_BIG)))
                       msgText += /*"Big Title:" +*/ extras.getString(Notification.EXTRA_TITLE_BIG).toString() + "\n";

               if (extras.getString(Notification.EXTRA_BIG_TEXT) != null)
                   if (!msgText.contains(extras.getString(Notification.EXTRA_BIG_TEXT)))
                     msgText += /*"BigText: " + */extras.getString(Notification.EXTRA_BIG_TEXT).toString() + "\n";

               if (extras.getString(Notification.EXTRA_SUMMARY_TEXT) != null)
                   if (!msgText.contains(extras.getString(Notification.EXTRA_SUMMARY_TEXT)))
                     msgText += /*"SummaryText: " +*/ extras.getString(Notification.EXTRA_SUMMARY_TEXT).toString() + "\n";

               if (extras.getString(Notification.EXTRA_INFO_TEXT) != null)
                   if (!msgText.contains(extras.getString(Notification.EXTRA_INFO_TEXT)))
                     msgText += /*"InfoText: " +*/ extras.getString(Notification.EXTRA_INFO_TEXT).toString() + "\n";

               if (extras.getString(Notification.EXTRA_TEXT) != null)
                   if (!msgText.contains(extras.getString(Notification.EXTRA_TEXT)))
                     msgText += /*"Text: " + */ extras.getString(Notification.EXTRA_TEXT).toString() + "\n";

               if (extras.getString(Notification.EXTRA_SUB_TEXT) != null)
                   if (!msgText.contains(extras.getString(Notification.EXTRA_SUB_TEXT)))
                     msgText += /*"SubText: " +*/ extras.getString(Notification.EXTRA_SUB_TEXT).toString() + "\n";

               CharSequence[] lines = extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
               if (lines != null) {
                   for (CharSequence line : lines) {
                       if (!msgText.contains(line.toString()))
                           msgText += /*"line: " + */line.toString() + "  \n";
                   }
               }


               Sms sms = new Sms(-1, new Date(), sender.getID(), msgText);
               try {
                   SMSReceiver smsReceiver = new SMSReceiver();
                   smsReceiver.parseSMS(this, sms);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }


        /*        Log.i(TAG, "onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\\t" + sbn.getNotification().tickerText + "\\t" + sbn.getPackageName());*/
//        Intent intent = new Intent("com.yoshione.fingen.NOTIFICATION_LISTENER");
//        intent.putExtra("notification_event", "onNotificationPosted:\\n" + sbn.getPackageName() + "\\n");
//        sendBroadcast(intent);
    }

/*    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\\t" + sbn.getNotification().tickerText + "\\t" + sbn.getPackageName());
        Intent intent = new Intent("ru.alexanderklimov.NOTIFICATION_LISTENER_EXAMPLE");
        intent.putExtra("notification_event", "onNotificationRemoved:\\n" + sbn.getPackageName() + "\\n");
        sendBroadcast(intent);
    }*/


    class NLServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
/*            if (intent.getStringExtra("command").equals("clearall")) {
                PushReceiver.this.cancelAllNotifications();
            } else if (intent.getStringExtra("command").equals("list")) {
                Intent notificationIntent = new Intent("ru.alexanderklimov.NOTIFICATION_LISTENER_EXAMPLE");
                notificationIntent.putExtra("notification_event", "=======");
                sendBroadcast(notificationIntent);

                int i = 1;
                for (StatusBarNotification sbn : PushReceiver.this.getActiveNotifications()) {
                    Intent infoIntent = new Intent("ru.alexanderklimov.NOTIFICATION_LISTENER_EXAMPLE");
                    infoIntent.putExtra("notification_event", i + " " + sbn.getPackageName() + "\\n");
                    sendBroadcast(infoIntent);
                    i++;
                }*/

                Intent listIntent = new Intent("com.yoshione.fingen.NOTIFICATION_LISTENER");
//                listIntent.putExtra("notification_event", "Notification List");
                sendBroadcast(listIntent);
 //           }
        }
    }
}
