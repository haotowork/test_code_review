import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.PopupWindow;


public class BasePopupWindow extends PopupWindow {
    public BasePopupWindow() {
        super();
    }
    public BasePopupWindow(Context context) {
        super(context);
    }
    public BasePopupWindow(View view) {
        super(view);
    }
    public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    private Context mContext;
    @Override
    public void showAsDropDown(final View anchor) {
        this.mContext = getActivityContext(anchor);
        startShow(anchor, new Runnable() {
            @Override
            public void run() {
                BasePopupWindow.super.showAsDropDown(anchor);
            }
        });
    }

    @Override
    public void showAsDropDown(final View anchor, final int xoff, final int yoff) {
        this.mContext = getActivityContext(anchor);

        startShow(anchor, new Runnable() {
            @Override
            public void run() {
                BasePopupWindow.super.showAsDropDown(anchor, xoff, yoff);
            }
        });
    }

    @Override
    public void showAsDropDown(final View anchor, final int xoff, final int yoff, final int gravity) {
        this.mContext = getActivityContext(anchor);

        startShow(anchor, new Runnable() {
            @Override
            public void run() {
                BasePopupWindow.super.showAsDropDown(anchor, xoff, yoff, gravity);
            }
        });
    }

    @Override
    public void showAtLocation(final View anchor, final int gravity, final int x, final int y) {
        this.mContext = getActivityContext(anchor);

        startShow(anchor, new Runnable() {
            @Override
            public void run() {
                BasePopupWindow.super.showAtLocation(anchor, gravity, x, y);
            }
        });
    }

    @Override
    public void dismiss() {
        if (!(mContext instanceof Activity)) {
            super.dismiss();
        } else {
            BadTokenHelper.safeRunInActivity(mContext, new Runnable() {
                @Override
                public void run() {
                    BasePopupWindow.super.dismiss();
                }
            }, BadTokenHelper.DISPLAY_TYPE_DISMISS);
        }
    }

    private void startShow(View view, Runnable runnable) {
        if (!(mContext instanceof Activity)) {
            runnable.run();
        } else {
            BadTokenHelper.safeRunInActivity(mContext, view, runnable, BadTokenHelper.DISPLAY_TYPE_SHOW);
        }
    }

    private Context getActivityContext(View view) {
        if (view == null) {
            return view.getContext();
        }
        if (view.getContext() instanceof Activity) {
            return view.getContext();
        }
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof View) {
            if (((View) viewParent).getId() == android.R.id.content) {
                return view.getContext();
            } else {
                return getActivityContext(((View) viewParent));
            }
        } else {
            return view.getContext();
        }
    }
}
