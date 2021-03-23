package com.yoshione.fingen.managers;

import android.content.Context;

import com.yoshione.fingen.FragmentPushSenderEdit;
import com.yoshione.fingen.R;
import com.yoshione.fingen.model.PushSender;

import androidx.fragment.app.FragmentManager;

/**
 * Created by slv on 08.04.2016.
 *
 */
public class PushSenderManager {

    public static void showEditDialog(final PushSender sender, final FragmentManager fragmentManager, final Context context) {

        String title;
        if (sender.getID() < 0) {
            title = context.getResources().getString(R.string.ent_new_push_sender);
        } else {
            title = context.getResources().getString(R.string.ent_edit_push_sender);
        }

        FragmentPushSenderEdit fragmentPushSenderEdit = FragmentPushSenderEdit.newInstance(title,sender);
        fragmentPushSenderEdit.show(fragmentManager, "fragmentPushSenderEdit");
    }
}
