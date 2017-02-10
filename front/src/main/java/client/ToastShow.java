package client;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Janter on 2015/4/16.
 */
public class ToastShow {

    public static void showToast(Context context, String message){
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout ll = new LinearLayout(context);
        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        ll.addView(textView);
        toast.setView(ll);
        toast.show();
    }
}
