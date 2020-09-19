package com.brkcnszgn.dropdownhash;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.binaryfork.spanny.Spanny;
import com.brkcnszgn.bottomDropdown.R;
import com.brkcnszgn.dropdownhash.animations.Animations;
import com.brkcnszgn.dropdownhash.listeners.ClickListener;
import com.rengwuxian.materialedittext.MaterialEditText;


public class DropdownView extends LinearLayout {

    private Context context;
    private String title;
    private boolean isForced;
    private String retailerFormID;
    private Resources resources;
    private String key;
    private String key2;
    private String key3;
    private boolean isWhiteTheme;


    TextView txtTitle;
    MaterialEditText txtStatus;
    FrameLayout btnStatus;
    View viewLine;
    LinearLayout layoutDropdown;
    ImageView imgArrow;

    public DropdownView(Context context) {
        this(context, null);
        init(context, null);
    }

    public DropdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(getContext(), R.layout.view_dropdown, this);
        txtTitle = view.findViewById(R.id.txtTitle);
        btnStatus = view.findViewById(R.id.btnStatus);
        txtStatus = findViewById(R.id.txtStatus);
        viewLine = findViewById(R.id.viewLine);
        imgArrow = findViewById(R.id.imgArrow);
        layoutDropdown = findViewById(R.id.layoutDropdown);

        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DropdownView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        init(context, attrs);
    }

    public void setTitle(String title) {
        this.title = title;
        txtTitle.setText(title);
        txtStatus.setHint(title);
    }

    public void init(final Context context, AttributeSet attrs) {
        this.context = context;
        this.resources = context.getResources();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DropdownView);
        try {
            title = typedArray.getString(R.styleable.DropdownView_dropdown_title);
            isForced = typedArray.getBoolean(R.styleable.DropdownView_isForced_dropdown, false);
            isWhiteTheme = typedArray.getBoolean(R.styleable.DropdownView_dv_isWhiteTheme, false);
        } finally {
            typedArray.recycle();
        }

//        Spanny spanTitle = new Spanny();
//        if (isForced) {
//            spanTitle.append("* ", new ForegroundColorSpan(Color.RED));
//        }
//
//        spanTitle.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_form_view_title)));
//
//        Spanny spanHint = new Spanny();
//        if (isForced) {
//            spanHint.append("* ", new ForegroundColorSpan(Color.RED));
//        }
//        spanHint.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_form_view_hint)));
//
//        txtTitle.setText(spanTitle);
//        txtStatus.setHint(spanHint);

        setTitle(isForced, title);


        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, -25, 0, 0);
        btnStatus.setLayoutParams(lp);

        txtStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    if (txtTitle.getVisibility() == VISIBLE) {
                        Animations.fadeOut(txtTitle);
                    }
                    txtTitle.setVisibility(INVISIBLE);

                    if (isWhiteTheme) {
                        viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));
                    }

                } else {
                    if (txtTitle.getVisibility() == INVISIBLE) {
                        Animations.fadeIn(txtTitle);
                    }
                    txtTitle.setVisibility(VISIBLE);

                    if (isWhiteTheme) {
                        viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_yellow));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    public void setOnClickListener(final ClickListener callback) {
        btnStatus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick();
            }
        });
        txtStatus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick();
            }
        });
//        layoutDropdown.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.onClick();
//            }
//        });
    }

    public boolean check() {
        String s = txtStatus.getText().toString();

        setTextWatcher();

        if (s.equals("")) {
            //btnStatus.setBackgroundResource(R.drawable.bg_rectangle_light_red_solid_red_stroke);
            viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_error));
            btnStatus.requestFocus();
            return false;
        } else {
            //btnStatus.setBackgroundResource(R.drawable.bg_rectangle_white_solid_gray_stroke);
            viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));
            return true;
        }


    }

    public void cleanText() {
        txtStatus.setText("");
        //btnStatus.setBackgroundResource(R.drawable.bg_rectangle_white_solid_gray_stroke);
        viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));

    }

    public void setTextWatcher() {
        txtStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    //btnStatus.setBackgroundResource(R.drawable.bg_rectangle_light_red_solid_red_stroke);
                    viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_error));
                } else {
                    //btnStatus.setBackgroundResource(R.drawable.bg_rectangle_white_solid_gray_stroke);
                    viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void setEnable(boolean status) {
        btnStatus.setEnabled(status);
        btnStatus.setClickable(status);

        txtStatus.setEnabled(status);
    }

    public void setDefault() {
        //btnStatus.setBackgroundResource(R.drawable.bg_rectangle_white_solid);
        viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));
    }

    public void setText(String text) {
        if (text.equals("Seçiniz")) {
            txtStatus.setText("");
        } else {
            txtStatus.setText(text);
        }
    }


    public String getText() {
        return txtStatus.getText().toString();
    }

    public String getHint() {
        return title;
    }


    public void setTitle(boolean isForced, String title) {
        if (title == null) {
            title = "";
        }

        this.title = title;

        Spanny spanTitle = new Spanny();
        if (isForced) {
            spanTitle.append("* ", new ForegroundColorSpan(Color.RED));
        }

        if (isWhiteTheme) {
            spanTitle.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_white)));
        } else {
            spanTitle.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_form_view_title)));
        }


        Spanny spanHint = new Spanny();
        if (isForced) {
            spanHint.append("* ", new ForegroundColorSpan(Color.RED));
        }

        if (isWhiteTheme) {
            spanHint.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_white)));
        } else {
            spanHint.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_form_view_hint)));
        }

        txtTitle.setText(spanTitle);
        txtStatus.setHint(spanHint);

        if (isWhiteTheme) {
            //imgArrow.setColorFilter(ContextCompat.getColor(context, R.color.color_white), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgArrow.setColorFilter(ContextCompat.getColor(context, R.color.color_white), android.graphics.PorterDuff.Mode.SRC_IN);
            txtStatus.setTextColor(ContextCompat.getColor(context, R.color.color_white));

        }

    }


    public String getRetailerFormID() {
        return retailerFormID;
    }

    public void setRetailerFormID(String retailerFormID) {
        this.retailerFormID = retailerFormID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

}
