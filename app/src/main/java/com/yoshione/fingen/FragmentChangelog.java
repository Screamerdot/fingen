/*
 *  ******************************************************************************
 *     Copyright (c) 2013 Gabriele Mariotti.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *    *****************************************************************************
 */
package com.yoshione.fingen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import it.gmariotti.changelibs.library.view.ChangeLogRecyclerView;

/**
 * Example with Dialog
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class FragmentChangelog extends DialogFragment {

        public FragmentChangelog() {
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            ChangeLogRecyclerView chgList= (ChangeLogRecyclerView) layoutInflater.inflate(R.layout.fragment_changelog, null);

            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.ttl_changelog)
                    .setView(chgList)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();

        }

}
