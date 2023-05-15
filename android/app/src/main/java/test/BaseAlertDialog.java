import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class BaseAlertDialog extends AlertDialog {
    protected Context mContext;
    protected BaseAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }
    protected BaseAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }
    protected BaseAlertDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void show() {
        BadTokenHelper.safeRunInActivity(mContext, new Runnable() {
            @Override
            public void run() {
                BaseAlertDialog.super.show();
            }
        }, BadTokenHelper.DISPLAY_TYPE_SHOW);
    }

    @Override
    public void dismiss() {
        BadTokenHelper.safeRunInActivity(mContext, new Runnable() {
            @Override
            public void run() {
                BaseAlertDialog.super.dismiss();
            }
        }, BadTokenHelper.DISPLAY_TYPE_DISMISS);
    }

    protected void initTransparentWindow() {
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = window.getAttributes();
            if (null == lp) {
                lp = new WindowManager.LayoutParams();
            }
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
    }
}
