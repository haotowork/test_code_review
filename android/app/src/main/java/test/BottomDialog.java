import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class BottomDialog extends AppCompatDialog {

    private static final String TAG = "BottomDialog";
    //dismiss动画开始
    private ObjectAnimator dismissAnimator;
    private ObjectAnimator dismissDimAnimator;
    private AnimatorSet dismissAnimatorSet;
    //dismiss动画结束

    private FrameLayout bottomLayout;

    //show动画开始
    private ObjectAnimator showAnimator;
    private ObjectAnimator showDimAnimator;
    private AnimatorSet showAnimatorSet;
    //show动画结束

    WindowDimProperty windowDimProperty;

    AnimatorListener listener;


    public BottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init() {
        listener = new AnimatorListener(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        dismissAnimator = new ObjectAnimator();
        dismissAnimator.setPropertyName("Y");

        dismissDimAnimator = new ObjectAnimator();
        dismissDimAnimator.setFloatValues(0.6f, 0f);

        dismissAnimatorSet = new AnimatorSet();
        dismissAnimatorSet.addListener(listener);
        dismissAnimatorSet.setDuration(200);
        dismissAnimatorSet.play(dismissAnimator).with(dismissDimAnimator);
        dismissAnimatorSet.setInterpolator(new AccelerateInterpolator());

        showAnimator = new ObjectAnimator();
        showAnimator.setPropertyName("Y");
        showAnimator.setFloatValues(Resources.getSystem().getDisplayMetrics().heightPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 5f);

        showDimAnimator = new ObjectAnimator();
        showDimAnimator.setFloatValues(0f, 0.6f);

        showAnimatorSet = new AnimatorSet();
        showAnimatorSet.play(showAnimator).with(showDimAnimator);
        showAnimatorSet.setInterpolator(new DecelerateInterpolator());
        showAnimatorSet.setDuration(250);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            windowDimProperty = new WindowDimProperty(window);
            dismissDimAnimator.setProperty(windowDimProperty);
            showDimAnimator.setProperty(windowDimProperty);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        showAnimatorSet.end();
        dismissAnimatorSet.end();
        dismissAnimatorSet.removeAllListeners();
        super.onDetachedFromWindow();
    }

    private View wrapInBottomSheet(View view) {
        View parent = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_bottom_dialog, null);
        bottomLayout = parent.findViewById(R.id.design_bottom_sheet);
        bottomLayout.addView(view);
        parent.findViewById(R.id.design_bottom_sheet_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return parent;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(view));
    }

    @Override
    public void show() {
        if (showAnimatorSet.isRunning()) {
            return;
        }
        showAnimator.setTarget(bottomLayout);
        super.show();
        showAnimatorSet.start();
    }

    private void superDismiss() {
        dismissAnimatorSet.removeAllListeners();
        super.dismiss();
    }

    @Override
    public void dismiss() {
        if (dismissAnimatorSet.isRunning()) {
            return;
        }
        dismissAnimator.setTarget(bottomLayout);
        dismissAnimator.setFloatValues(bottomLayout.getY(), Resources.getSystem().getDisplayMetrics().heightPixels);

        dismissAnimatorSet.start();
    }

    private static class AnimatorListener extends AnimatorListenerAdapter {

        private BottomDialog dialog;

        public AnimatorListener(BottomDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            dialog.superDismiss();
        }
    }

    private static class WindowDimProperty extends Property<Window, Float> {

        private Window window;

        WindowDimProperty(Window w) {
            super(Float.class, "WindowDimProperty");
            this.window = w;
        }

        @Override
        public Float get(Window object) {
            return window.getAttributes().dimAmount;
        }

        @Override
        public void set(Window object, Float value) {
            try {
                window.setDimAmount(value);
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
