package com.chungmyung.ndivident;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;


public class Eventcost_Payment_Activity extends AppCompatActivity {


    private EditText mTitle;
    private EditText mTotalAmount;
    private EditText mNumAttendee;

    private CheckBox mAttendeeCheckbox;
    private LinearLayout mAddNameline;

    private RadioGroup mRadiogroup;
    private RadioButton mUnit1;
    private RadioButton mUnit2;
    private RadioButton mUnit3;

    private static final String TAG = "MainActivity";

    private AdView mAdView;
    private String mNumattendeeInput;

    private Integer mIntegerAttendee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventcost__payment_);

        mTitle = (EditText) findViewById(R.id.eventName_edit);
        mTotalAmount = (EditText) findViewById(R.id.totalAmount_edit);
        mNumAttendee = (EditText) findViewById(R.id.numAttendee_edit);

        mAttendeeCheckbox = (CheckBox) findViewById(R.id.attendeelist_checkbox);
        mAddNameline = (LinearLayout) findViewById(R.id.addNamelist);

        mRadiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        mUnit1 = (RadioButton) findViewById(R.id.unit1_radio);
        mUnit2 = (RadioButton) findViewById(R.id.unit2_radio);
        mUnit3 = (RadioButton) findViewById(R.id.unit3_radio);


        mTitle.setLines(1);


        mAdView = (AdView) findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, "ca-app-pub-7273514499724377~3565394785");


//에딧택스트 입력 문자 에 따른 설정
        mTotalAmount.addTextChangedListener(new TextWatcher() {

            DecimalFormat num = new DecimalFormat("###,###.####");

            String strAmount = "";


            protected String makeStringCommar(String str) {

                if (str.length() == 0)
                    return "";

                long value = Long.parseLong(str);
                DecimalFormat format = new DecimalFormat("###,###.##");
                return format.format(value);

            }


            @Override
            public void beforeTextChanged(
                    CharSequence charSequence, int i, int i1, int i2) {
            }

            // 입력 금액에 따른 범위 감지 , 초과에 대한 메시지.
            @Override
            public void onTextChanged(CharSequence cha, int i, int i1, int i2) {

                if (cha.toString().isEmpty()) {

                } else if (!cha.toString().equals(strAmount)) { // StackOverflow 방지
                    strAmount = makeStringCommar(cha.toString().replace(",", ""));
                    mTotalAmount.setText(strAmount);
                    Editable e = mTotalAmount.getText();
                    Selection.setSelection(e, strAmount.length());
                    if (Integer.valueOf(cha.toString().replace(",", "")) > 100000000) {
                        mTotalAmount.setText("");
                        Toast.makeText(Eventcost_Payment_Activity.this,
                                "Set the amount below 100,000,000. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // 참가 인원수 입력시 처리
        mNumAttendee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(
                    CharSequence attendeenumber, int i, int i1, int i2) {

                // 처음에 빈칸으로 두기
                mAddNameline.removeAllViews();
                mNumattendeeInput = mNumAttendee.getText().toString();

                if (mAttendeeCheckbox.isChecked()) {

                        if (!TextUtils.isEmpty(mNumattendeeInput)){
                    mIntegerAttendee = Integer.valueOf(mNumattendeeInput);}


                    if ((mNumattendeeInput.trim().isEmpty())) {
                        mAddNameline.removeAllViews();

                    } else if (mIntegerAttendee > 50) {
                        mNumAttendee.setText("");
                        Toast.makeText(Eventcost_Payment_Activity.this,
                                "Maximum attendee is 50."
                                , Toast.LENGTH_SHORT).show();

                    } else if (!(mNumattendeeInput.trim().isEmpty())) {

                        for (int n = 0; n < mIntegerAttendee; n++) {

                            EditText name_text = new EditText(
                                    Eventcost_Payment_Activity.this);

                            name_text.setLayoutParams(new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT));
                            name_text.setPadding(20, 10, 10, 10);
                            name_text.setTextSize(24);
                            name_text.setId(View.generateViewId());

//                            name_text.requestFocus();
                            name_text.setImeOptions(EditorInfo.IME_ACTION_NEXT);  // enter 또는 다음 눌렀을때 하게 되는 행동
                            name_text.setInputType(InputType.TYPE_CLASS_TEXT);  // 위의 코딩과 패어링을 작동 함.

                            name_text.setHint((n + 1) + ". " + " Input name of attendee.");
                            name_text.setTag("nameView" + n);

                            mAddNameline.addView(name_text);
                        }
                    }
                } else {

//                   mNumattendeeInput = mNumAttendee.getText().toString();
//                    if (!TextUtils.isEmpty(mNumattendeeInput))
//                    mIntegerAttendee = Integer.valueOf(mNumattendeeInput);

//                    입력한 숫자가 공백이라면 ... 지워라
                    if ((mNumattendeeInput.trim().isEmpty())) {
                        mAddNameline.removeAllViews();


                    } else if (Integer.valueOf(mNumattendeeInput) > 50) {
                        mNumAttendee.setText("");
                        Toast.makeText(Eventcost_Payment_Activity.this,
                                "the Maximum attendee is 50 ."
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    public void onCheckBoxClicked(View view) {

        if (mAttendeeCheckbox.isChecked()) {
            mAddNameline.removeAllViews();
            mAddNameline.setVisibility(View.VISIBLE);

            if (mNumattendeeInput.trim().equals("")) {
                Toast.makeText(this, "Input attendee name. ", Toast.LENGTH_SHORT).show();
                mAttendeeCheckbox.setChecked(false);  //  아무것도 입력 안했을 경우 해제.

            } else {
                if (!TextUtils.isEmpty(mNumattendeeInput)){
                    mIntegerAttendee = Integer.valueOf(mNumattendeeInput);}

                for (int m = 0; m < mIntegerAttendee; m++) {

                    EditText name_text = new EditText(Eventcost_Payment_Activity.this);

                    name_text.setLayoutParams(new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                    name_text.setPadding(20, 10, 10, 10);
                    name_text.setTextSize(24);
                    name_text.setId(View.generateViewId());

//                    name_text.requestFocus();
                    name_text.setImeOptions(EditorInfo.IME_ACTION_NEXT);  // enter눌렀을때 하게 되는 행동
                    name_text.setInputType(InputType.TYPE_CLASS_TEXT);  // 위의 코딩과 패어링을 작동 함.

                    name_text.setHint((m + 1) + ". " + "Input name of attendee.");
                    name_text.setTag("nameView" + m);

                    mAddNameline.addView(name_text);
                }
            }

        } else {
            mAddNameline.setVisibility(View.GONE);
            mAddNameline.removeAllViews();
        }
    }


    private boolean isEditTextEmpty() {

        return TextUtils.isEmpty(mTitle.getText().toString()) ||
                TextUtils.isEmpty(mTotalAmount.getText().toString()) ||
                TextUtils.isEmpty(mNumAttendee.getText().toString());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 화면 돌렸을때 죽이지 않는 코드..  아무것도 입력하지 않았기 때문.

        if (!TextUtils.isEmpty(mNumAttendee.getText().toString())) {
            mIntegerAttendee = Integer.valueOf(mNumAttendee.getText().toString());
        }

        outState.putBoolean("ischeck", mAttendeeCheckbox.isChecked());
        outState.putString("numatteedee", mNumAttendee.getText().toString());

        if (mAttendeeCheckbox.isChecked()) {

            for (int p = 0; p < mIntegerAttendee; p++) {

                EditText attendeename = (EditText) mAddNameline.findViewWithTag("nameView" + p);

                if (!attendeename.getText().toString().trim().equals("")) {

                    outState.putString("nameText" + p, attendeename.getText().toString());
                }
            }
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

//        if (!TextUtils.isEmpty(mNumAttendee.getText().toString())) {
//            mIntegerAttendee = Integer.valueOf(mNumAttendee.getText().toString());
//        }

        mAddNameline.removeAllViews();
        Boolean check = savedInstanceState.getBoolean("ischeck");
        String numatteedee = savedInstanceState.getString("numatteedee");

        String name;

        mAttendeeCheckbox.setChecked(check);
        mAddNameline.setVisibility(View.VISIBLE);

        if (numatteedee.isEmpty()) {

        } else {

            if (!TextUtils.isEmpty(mNumattendeeInput)){
                mIntegerAttendee = Integer.valueOf(mNumattendeeInput);}

            for (int m = 0; m < mIntegerAttendee; m++) {

                EditText name_text = new EditText(Eventcost_Payment_Activity.this);

                if (savedInstanceState.getString("nameText" + m) != null) {
                    name = savedInstanceState.getString("nameText" + m);
                    name_text.setText(name);
                }

                name_text.setLayoutParams(new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                name_text.setPadding(20, 10, 10, 10);
                name_text.setTextSize(24);
                name_text.setId(View.generateViewId());

//                name_text.requestFocus();
                name_text.setImeOptions(EditorInfo.IME_ACTION_NEXT);  // enter눌렀을때 하게 되는 행동
                name_text.setInputType(InputType.TYPE_CLASS_TEXT);  // 위의 코딩과 패어링을 작동 함.

                name_text.setHint((m + 1) + ". " + "Input name of attendee.");
                name_text.setTag("nameView" + m);

                mAddNameline.addView(name_text);
            }
        }
        mNumAttendee.setText(numatteedee);
    }


    // 버튼 클릭시
    public void onClickButton(View view) {

        String money_check = mTotalAmount.getText().toString().replace(",", "").trim();
        String attendee_check = mNumAttendee.getText().toString().trim();

//

        if (money_check == null || attendee_check == null) {
            Toast.makeText(this,
                    " Input the spent amount and number of attendee ",
                    Toast.LENGTH_SHORT).show();
        } else {

            if (!(money_check.equals("")) && !(attendee_check.equals(""))) {
                String name[] = new String[Integer.valueOf(mNumAttendee.getText().toString())];

                if (mAttendeeCheckbox.isChecked()) {

                    Intent intent = new Intent(Eventcost_Payment_Activity.this,
                            DetailCalculationActivity.class);

                    for (int l = 0; l < Integer.valueOf(mNumAttendee.getText().toString()); l++) {

                        EditText nametext = (EditText) mAddNameline.findViewWithTag("nameView" + l);

                        if (nametext.getText().toString().trim().equals("")) {
                            intent.putExtra("namei" + l, "" + (l + 1));
                        } else {
                            intent.putExtra("namei" + l, nametext.getText().toString());
                        }
                    }


                    if (mUnit1.isChecked()) {
                        intent.putExtra("unit", 10);
                    } else if (mUnit2.isChecked()) {
                        intent.putExtra("unit", 100);
                    } else if (mUnit3.isChecked()) {
                        intent.putExtra("unit", 1000);
                    }

                    intent.putExtra("title", mTitle.getText().toString());
                    intent.putExtra("totalamount", mTotalAmount.getText().toString().replace(",", ""));
                    intent.putExtra("numattendee", mNumAttendee.getText().toString());

                    startActivity(intent);
                } else {
                    simpleCalculation();
                }
            } else if (!(money_check.equals("")) && (attendee_check.equals(""))) {
                Toast.makeText(this,
                        "Input the number of attendee.", Toast.LENGTH_SHORT).show();
            } else if ((money_check.equals("")) && !(attendee_check.equals(""))) {
                Toast.makeText(this,
                        "Input the total spent amount.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Input the amount and the number of attendee.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void simpleCalculation() {

        Intent intent = new Intent(Eventcost_Payment_Activity.this,
                FirstCalculationActivity.class);

        if (mUnit1.isChecked()) {
            intent.putExtra("delete", 10);
        } else if (mUnit2.isChecked()) {
            intent.putExtra("delete", 100);
        } else if (mUnit3.isChecked()) {
            intent.putExtra("delete", 1000);
        }

        intent.putExtra("title", mTitle.getText().toString());
        intent.putExtra("totalamount", mTotalAmount.getText().toString().replace(",", ""));
//        intent.putExtra("totalamount2", mTotalAmount.getText().toString().replace(",", "")); // 분담금액
        intent.putExtra("numattendee", mNumAttendee.getText().toString());

        startActivity(intent);
    }
}

