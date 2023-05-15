import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.Calendar;

public class CalendarPopupWindow extends BasePopupWindow {
    private ExpandCalendarCard mCalendarCard;
    private View mBlankArea;
    public CalendarPopupWindow(Context context) {
        super(context);
        View view = View.inflate(context, R.layout.popup_calendar, null);
        mCalendarCard = (ExpandCalendarCard) view.findViewById(R.id.calendar);
        mBlankArea = view.findViewById(R.id.fl_blank_area);
        mBlankArea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.transparent_shadow));
        this.setBackgroundDrawable(dw);
    }

    public void updateCell() {
        mCalendarCard.notifyChanges();
    }

    public void setDateDisplay(Calendar dateDisplay) {
        mCalendarCard.setDateDisplay(dateDisplay);
    }

    public void setOnCellItemClick(OnCellItemClick onCellItemClick) {
        mCalendarCard.setOnCellItemClick(onCellItemClick);
    }

    public void setDuration(int duration){
        if(mCalendarCard != null){
            mCalendarCard.setDuration(duration);
        }
    }

    public void setHideOnClickMonth(boolean hideOnClickMonth) {
        if (mCalendarCard != null){
            mCalendarCard.setHideOnClickMonth(hideOnClickMonth);
        }
    }
}
