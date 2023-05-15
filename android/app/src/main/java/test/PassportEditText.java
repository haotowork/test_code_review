import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class PassportEditText extends AppCompatAutoCompleteTextView implements IEnableAction {
    private PassportEnableCompat compat;
    private EnableControler enableControler;
    private IParamAction<String> paramAction;

    public PassportEditText(Context context) {
        super(context);
        init();
    }

    public PassportEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PassportEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paramAction = new EditTextParamAction(this);
        compat = PassportEnableCompat.getInstance();
        enableControler = new DefaultEnableControler();
    }

    public void setEnableControler(EnableControler controler) {
        if (controler != null) {
            enableControler = controler;
            compat.setEnable(enableControler.enable(getEditableText()));
        }
    }

    public void setEnableLength(int length) {
        if (enableControler != null && enableControler instanceof DefaultEnableControler) {
            ((DefaultEnableControler) enableControler).setLength(length);
        }
    }


    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() == visibility) {
            return;
        }
        super.setVisibility(visibility);
        if (visibility != VISIBLE) {
            compat.setEnable(true);
        } else {
            compat.setEnable(enableControler.enable(getText()));
        }
    }

    @Override
    public void addEnableAction(IPassportEnableControler controler) {
        compat.addEnableAction(controler);
        compat.setEnable(enableControler.enable(getEditableText()));
        addTextChangedListener(new PassportTextWatcher(enable -> compat.setEnable(enableControler.enable(enable))));
    }

    public String getParam() {
        return getText().toString();
    }

    public IParamAction<String> getParamAction() {
        return paramAction;
    }

    public void addTextChangeListener(PassportEditText.TextChangeListener listener) {
        addTextChangedListener(new PassportTextWatcher(listener));
    }

    public interface EnableControler {
        boolean enable(Editable editable);
    }

    private static class DefaultEnableControler implements EnableControler {

        private int length = 0;

        public void setLength(int length) {
            this.length = length;
        }

        @Override
        public boolean enable(Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                return false;
            } else if (editable.length() < length) {
                return false;
            }
            return true;
        }
    }

    private static class PassportTextWatcher implements TextWatcher {

        private TextChangeListener listener;


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (listener != null) {
                listener.afterTextChanged(editable);
            }
        }

        public PassportTextWatcher(TextChangeListener listener) {
            this.listener = listener;
        }
    }

    public interface TextChangeListener {
        void afterTextChanged(Editable editable);
    }
}
