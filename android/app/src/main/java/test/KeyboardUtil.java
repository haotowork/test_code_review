import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static com.example.testuri.Utils.showKeybroad;

// 输入键盘工具类
public class KeyboardUtil {

    public static void showKeyboard(Context context, final EditText editText) {
        if (editText != null && context != null) {
            final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                boolean b = inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                if (!b) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                        }
                    });
                }
            } catch (Exception ignore) {
            }
        }
    }

    public static void showKeyboard(Context context, final View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            view.requestFocus();
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    public static void showKeybroadDelay(Context context, EditText editText) {
        editText.requestFocus();
        if (Build.VERSION.SDK_INT >= 18) {
            editText.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    if (hasFocus) {
                        showKeybroad(context, editText);
                    }
                    if (Build.VERSION.SDK_INT >= 18) {
                        editText.getViewTreeObserver().removeOnWindowFocusChangeListener(this);
                    }
                }
            });
        } else {
            showKeybroad(context, editText);
        }
    }

    public static void hideKeyboard(Context context, IBinder windowToken) {
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

}
