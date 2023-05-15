import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BaseDialog extends Dialog {
    private Context mContext;

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    public void show() {
        BadTokenHelper.safeRunInActivity(mContext, new Runnable() {
            @Override
            public void run() {
                BaseDialog.super.show();
            }
        }, BadTokenHelper.DISPLAY_TYPE_SHOW);
    }

    @Override
    public void dismiss() {
        BadTokenHelper.safeRunInActivity(mContext, new Runnable() {
            @Override
            public void run() {
                BaseDialog.super.dismiss();
            }
        }, BadTokenHelper.DISPLAY_TYPE_DISMISS);
    }


}
