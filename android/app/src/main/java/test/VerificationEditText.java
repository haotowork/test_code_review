import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public final class VerificationEditText extends PassportEditText {

    private long lastTime = 0;

    public VerificationEditText(Context context) {
        super(context);
    }

    public VerificationEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerificationEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        this.setSelection(this.getText().length());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime < 500) {
                    lastTime = currentTime;
                    return true;
                } else {
                    lastTime = currentTime;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
