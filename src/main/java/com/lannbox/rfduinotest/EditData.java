package com.lannbox.rfduinotest;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditData extends LinearLayout{
    EditText dataEdit;
    ToggleButton hexToggle;

    public EditData(Context context) {
        super(context);
        initEditData();
    }

    public EditData(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditData();
    }

    public EditData(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initEditData();
    }

    private void initEditData() {
        dataEdit = new EditText(getContext());
        dataEdit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        addView(dataEdit, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));

        hexToggle = new HexToggle(getContext());
        addView(hexToggle);

        dataEdit.addTextChangedListener(new EditDataHexWatcher());
    }

    public void setImeOptions(int imeOptions) {
        dataEdit.setImeOptions(imeOptions | EditorInfo.IME_FLAG_FORCE_ASCII);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener l) {
        dataEdit.setOnEditorActionListener(l);
    }

    public boolean inHexMode() {
        return (hexToggle != null) && hexToggle.isChecked();
    }

    public byte[] getData() {
        String text = dataEdit.getText().toString();
        if (inHexMode()) {
            return HexAsciiHelper.hexToBytes(text);
        } else {
            return text.getBytes();
        }
    }

    public void setData(byte[] data) {
        String text;
        if (inHexMode()) {
            text = HexAsciiHelper.bytesToHex(data);
        } else {
            text = HexAsciiHelper.bytesToAsciiMaybe(data);
            if (text == null) {
                text = "";
            }
        }
        dataEdit.setText(text);
    }

    private static final Pattern nonHexPattern = Pattern.compile("\\P{XDigit}+");

    private class EditDataHexWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            if (inHexMode()) {
                Matcher m = nonHexPattern.matcher(s);
                while (m.find()) {
                    s.delete(m.start(), m.end());
                    m.reset(s);
                }
            }
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
    }

    private class HexToggle extends ToggleButton {
        public HexToggle(Context context) {
            super(context);
            setTextOff("Text");
            setTextOn("Hex");
            toggle();
        }

        @Override
        public void setChecked(boolean checked) {
            byte[] data = getData();
            super.setChecked(checked);
            setData(data);
        }
    }
}
