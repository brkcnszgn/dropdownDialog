package com.brkcnszgn.dropdownhash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.brkcnszgn.bottomDropdown.R;
import com.brkcnszgn.bottomDropdown.databinding.ActivityDropdownBinding;
import com.brkcnszgn.dropdownhash.listeners.OrientationListener;
import com.brkcnszgn.dropdownhash.model.DropdownModel;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DropdownHashActivity extends AppCompatActivity implements OrientationListener {

    public static String TITLE = "title";
    public static String SEARCH = "search";
    public static String MIN_LENGTH_FOR_SEARCH = "mLengthForSearch";

    private HashMap<String, DropdownModel> mParameters;
    private HashMap<String, DropdownModel> mOriginalParameters;

    private ActivityDropdownBinding binding;


    private int mMinLengthForSearch;
    private Adapter dropdownViewAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDropdownBinding.inflate(getLayoutInflater());
        this.setContentView(binding.getRoot());
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mParameters = Constants.INSTANCE.getMParameters();
        this.mOriginalParameters = Constants.INSTANCE.getMParameters();
        //setBottomSheet();
        binding.txtTitle.setText(getIntent().getStringExtra(TITLE));
        boolean search = getIntent().getBooleanExtra(SEARCH, false);
        mMinLengthForSearch = getIntent().getIntExtra(MIN_LENGTH_FOR_SEARCH, 3);
        if (mParameters != null && mParameters.size() > 0) {
            if (getResources().getConfiguration().orientation == 1) {
                if (mParameters.size() > 8) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp_400));
                    binding.rView.setLayoutParams(params);
                }
            } else {
                if (mParameters.size() > 8) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    binding.rView.setLayoutParams(params);
                }
            }


//

            dropdownViewAdapter = new Adapter(this);
            binding.rView.setAdapter(dropdownViewAdapter);
            setItems2();
            setListener();

            if (search) {
                binding.searchLayout.setVisibility(View.VISIBLE);
            }


        }

        clickFunc();

    }

    void clickFunc(){
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClose();
            }
        });

        binding.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClose();
            }
        });
    }


    private void setItems2() {
        dropdownViewAdapter.setList(mParameters);
    }

    public void selectItem(String ddKey, String ddText) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.INSTANCE.getSELECTED_DROPDOWN_TEXT(), ddKey);
        resultIntent.putExtra(Constants.INSTANCE.getSELECTED_DROPDOWN_TEXT(), ddText);
        setResult(Activity.RESULT_OK, resultIntent);
        UIUtil.hideKeyboard(DropdownHashActivity.this);
        finish();
    }

    public void selectItem(DropdownModel dropdown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.INSTANCE.getSELECTED_DROPDOWN(), dropdown.getTitle());
        resultIntent.putExtra(Constants.INSTANCE.getSELECTED_DROPDOWN_SECOND_KEY(), dropdown.getSecondKey());
        resultIntent.putExtra(Constants.INSTANCE.getSELECTED_DROPDOWN_THRID_KEY(), dropdown.getThirdKey());
        resultIntent.putExtra(Constants.INSTANCE.getSELECTED_DROPDOWN_TEXT(), dropdown.getDescription());
        setResult(Activity.RESULT_OK, resultIntent);
        UIUtil.hideKeyboard(DropdownHashActivity.this);
        finish();
    }

    private void setListener() {
        binding.txtSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = String.valueOf(s);
                if (text.length() == 0) {
                    mParameters = mOriginalParameters;
                    setItems2();
                } else if (text.length() >= mMinLengthForSearch) {
                    mParameters = new HashMap<>();

                    Iterator it = mOriginalParameters.entrySet().iterator();

                    int c = 0;
                    while (it.hasNext()) {

                        final Map.Entry pair = (Map.Entry) it.next();

                        String key = pair.getKey().toString();
                        final DropdownModel dm = mOriginalParameters.get(key);


                        if (dm.getDescription().toLowerCase(Constants.INSTANCE.getTURKISH())
                                .replace("ğ", "g")
                                .replace("ü", "u")
                                .replace("ş", "s")
                                .replace("i", "ı")
                                .replace("ö", "o")
                                .replace("ç", "c")

                                .contains(text.toLowerCase(Constants.INSTANCE.getTURKISH())
                                        .replace("ğ", "g")
                                        .replace("ü", "u")
                                        .replace("ş", "s")
                                        .replace("i", "ı")
                                        .replace("ö", "o")
                                        .replace("ç", "c"))) {

                            mParameters.put(String.valueOf(c), dm);
                            c++;
                        }

                    }


                    setItems2();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClose() {
        UIUtil.hideKeyboard(this);
        finish();
    }

    @Override
    public void onOrientationChange(int orientation) {
        if (orientation == 2) {
            if (mParameters.size() > 8) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                binding.rView.setLayoutParams(params);
            }
        }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (mParameters.size() > 8) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                binding.rView.setLayoutParams(params);
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mParameters.size() > 8) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp_400));
                binding.rView.setLayoutParams(params);
            }
        }
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private Context mContext;
        private HashMap<String, DropdownModel> mParameters = new HashMap<>();

        public Adapter(Context context) {
            this.mContext = context;
        }

        public void setList(HashMap<String, DropdownModel> map) {
            this.mParameters = map;
            notifyDataSetChanged();
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Adapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_dropdown, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ViewHolder holder, int position) {

            for (Map.Entry<String, DropdownModel> stringDropdownModelEntry : mParameters.entrySet()) {

                final Map.Entry pair = (Map.Entry) stringDropdownModelEntry;

                String key = pair.getKey().toString();

                if (Integer.parseInt(key) == position) {
//                    if (position == mParameters.size() - 1) {
//                        holder.viewSeparator.setVisibility(View.GONE);
//                    }

                    final DropdownModel dm = mParameters.get(key);

                    holder.txtValue.setText(dm.getDescription());
                    holder.btnItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mContext instanceof DropdownHashActivity) {
                                ((DropdownHashActivity) mContext).selectItem(dm);
                            }
                        }
                    });

//                    if (dm.getColor() != null) {
//
//                        if (dm.getColor().equals("Active")) {
//                            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));
//                        } else if (dm.getColor().equals("Passive")) {
//                            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.green));
//                        } else {
//                            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
//                        }
//                    } else {
//                        holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.black));
//
//                    }


                }
            }

        }

        @Override
        public int getItemCount() {
            return mParameters == null ? 0 : mParameters.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            TextView txtValue;
            RelativeLayout btnItem;
            View viewSeparator;


            public ViewHolder(View itemView) {
                super(itemView);
                txtValue = itemView.findViewById(R.id.txtValue);
                btnItem = itemView.findViewById(R.id.btnItem);
                viewSeparator = itemView.findViewById(R.id.viewSeparator);
            }

        }

    }

    public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {

        private Context mContext;
        private HashMap<String, DropdownModel> mParameters = new HashMap<>();

        public Adapter2(Context context) {
            this.mContext = context;
        }

        public void setList(HashMap<String, DropdownModel> map) {
            this.mParameters = map;
            notifyDataSetChanged();
        }

        @Override
        public Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Adapter2.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_dropdown, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter2.ViewHolder holder, int position) {

            Iterator it = mParameters.entrySet().iterator();

            while (it.hasNext()) {

                final Map.Entry pair = (Map.Entry) it.next();

                String key = pair.getKey().toString();

                if (Integer.parseInt(key) == position) {
//                    if (position == mParameters.size() - 1) {
//                        holder.viewSeparator.setVisibility(View.GONE);
//                    }

                    final DropdownModel dm = mParameters.get(key);

                    holder.txtValue.setText(dm.getDescription());
                    holder.btnItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mContext instanceof DropdownHashActivity) {
                                ((DropdownHashActivity) mContext).selectItem(dm);
                            }
                        }
                    });

//                    if (dm.getColor() != null) {
//
//                        if (dm.getColor().equals("Active")) {
//                            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));
//                        } else if (dm.getColor().equals("Passive")) {
//                            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.green));
//                        } else {
//                            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
//                        }
//                    } else {
//                        holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.black));
//
//                    }


                }
            }

        }

        @Override
        public int getItemCount() {
            return mParameters == null ? 0 : mParameters.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txtValue;
            RelativeLayout btnItem;
            View viewSeparator;


            public ViewHolder(View itemView) {
                super(itemView);
                txtValue = itemView.findViewById(R.id.txtValue);
                btnItem = itemView.findViewById(R.id.btnItem);
                viewSeparator = itemView.findViewById(R.id.viewSeparator);
            }

        }

    }
}
