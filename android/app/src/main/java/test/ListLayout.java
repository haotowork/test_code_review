import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class ListLayout extends LinearLayout {
    private View mEmptyView;
    private BaseAdapter mAdapter;
    public ListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private DataSetObserver mDataSetObserver;


    public void setAdapter(final BaseAdapter baseAdapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = baseAdapter;
        if (mAdapter != null) {
            mDataSetObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    if (mAdapter.getCount() == 0) {
                        if (getChildCount() > 0) ListLayout.this.removeAllViews();
                        mEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        initView();
                    }
                }
            };
            mAdapter.registerDataSetObserver(mDataSetObserver);
            initView();
        }
    }

    private void initView() {
        int dataCount = mAdapter.getCount();
        int viewCount = getChildCount();
        if (dataCount < viewCount) {
            for (int i = 0; i < dataCount; i++) {
                View view = this.getChildAt(i);
                mAdapter.getView(i, view, null);
            }
            removeViews(dataCount, viewCount - dataCount);
        } else {
            for (int i = 0; i < dataCount; i++) {
                if (i < viewCount) {
                    View view = this.getChildAt(i);
                    mAdapter.getView(i, view, null);
                } else {
                    addView(mAdapter.getView(i, null, null));
                }
            }
        }
        if (mEmptyView == null) return;
        if (this.getChildCount() > 0) {
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }
}
