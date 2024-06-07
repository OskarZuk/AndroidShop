package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import java.util.List;

public class EmailHelper {

    public static void sendEmail(Context context, String recipient, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.setType("message/rfc822");

        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            context.startActivity(Intent.createChooser(intent, "Wybierz aplikację do wysłania e-maila:"));
        } else {
            Toast.makeText(context, "Brak aplikacji do wysłania e-maila.", Toast.LENGTH_SHORT).show();
        }
    }
}
