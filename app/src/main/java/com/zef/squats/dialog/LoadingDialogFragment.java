package com.zef.squats.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

import com.zef.squats.R;

/**
 * Created by zef on 16/10/18.
 */

public class LoadingDialogFragment extends Dialog {
    ProgressBar progressBar;

    public LoadingDialogFragment(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(0);
    }

    @Override
    public void dismiss() {

    }

    public void dismissDialog() {
        super.dismiss();
    }
}
