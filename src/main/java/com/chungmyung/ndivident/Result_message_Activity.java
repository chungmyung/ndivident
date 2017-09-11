package com.chungmyung.ndivident;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Result_message_Activity extends AppCompatActivity {


    private String mTitleText;
    private TextView mPayPeopleText;
    private TextView mMaxMoneyText;
    private TextView mNameMoneyText;
    private LinearLayout mPayLayout;
    private LinearLayout mPayLayout2;
    private LinearLayout mPayBankLayout;

    private String mPayCheck;
    private int mMaximumNum;
    private String mSendDisplay;
    private EditText mBankText;
    private EditText mBankNumberText;
    private String mPayDivison;
    private String mMaxMoney;
    private String mDividedAmount;
    private TextView mMessageText;
    private int mNumPeople;
    private String mPayPeopleText1;
    private String mBankName;
    private String mBankNumber;
    private EditText mAccountHoler;
    private String mHoderName;

    private static final String TAG = "MainActivity";

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_message_);

        mPayPeopleText = (TextView) findViewById(R.id.holdername_edit);


        mBankText = (EditText) findViewById(R.id.bankname_edit);
        mBankNumberText = (EditText) findViewById(R.id.accountnum_edit);
        mAccountHoler = (EditText) findViewById(R.id.holdername_edit);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        mMessageText = (TextView) findViewById(R.id.message_text);


        if (getIntent() != null) {

            String name_money = "";

            mPayCheck = getIntent().getStringExtra("paycheck");  // 대금지불자 체크여부
            mPayDivison = getIntent().getStringExtra("paydivision");
            mPayPeopleText1 = getIntent().getStringExtra("paypeople") ;


            mMaxMoney = getIntent().getStringExtra("MaxMoney");
            mNumPeople = Integer.parseInt(getIntent().getStringExtra("num"));


            int checkPeople = getIntent().getIntExtra("checkPeople", 32);
            for (int i = 0; i < mNumPeople; i++) {
                if (i == checkPeople) {

                } else {
                    name_money = name_money + getIntent().getStringExtra("name" + i) + "\t" + "\t"
                            + getIntent().getStringExtra("Money" + i) + " Won"
                            + getIntent().getStringExtra("division" + i);

                    name_money = name_money + "\n";
                }
            }
            mDividedAmount = name_money;
            mTitleText = getIntent().getStringExtra("title");
        }
        display();
    }


    private void display() {

        mBankName = mBankText.getText().toString();
        mBankNumber = mBankNumberText.getText().toString();
        mHoderName = mAccountHoler.getText().toString();

        if (mPayCheck.equals("OK")) {
            mMessageText.setText("=================" + "\n"
                    + getString(R.string.title1) + mTitleText + "\n" + "\n"
                    + getString(R.string.payer2) + mPayPeopleText1 + "\n" + "\n"
                    +  getString(R.string.totalamount2) + mMaxMoney   + "\n" + "\n"
                    +  getString(R.string.numberattendee) + mNumPeople  + "\n" + "\n"
                    + "=================" + "\n"
                    + mBankName + "\n"
                    + getString(R.string.accountnumber2) + mBankNumber + "\n"
                    + getString(R.string.owner2)  +  mHoderName + "\n"
                    + "=================" + "\n"
                    + mDividedAmount);


        } else if (!mBankName.equals("") || !mBankNumber.equals("")) {

            mMessageText.setText("=================" + "\n"
                    + getString(R.string.title1) + mTitleText + "\n" + "\n"
                    + getString(R.string.totalamount2) + mMaxMoney   + "\n" + "\n"
                    + getString(R.string.numberattendee) + mNumPeople  + "\n" + "\n"
                    + "================="
                    + getString(R.string.accountnumber2) + mBankNumber + "\n"
                    + getString(R.string.owner2)  +  mHoderName + "\n"
                    + "=================" + "\n"
                    + mDividedAmount);
        }
        else {
            mMessageText.setText("=================" + "\n"
                    + getString(R.string.title1)  + mTitleText + "\n" + "\n"
                    + getString(R.string.totalamount2) + mMaxMoney   + "\n" + "\n"
                    + getString(R.string.numberattendee) + mNumPeople  + "\n" + "\n"
                    + "=================" + "\n"
                    +   mBankName + "\n"
                    + getString(R.string.accountnumber2) + mBankNumber + "\n"
                    + getString(R.string.owner2)  +  mHoderName + "\n"
                    + "=================" + "\n"
                    + mDividedAmount);
        }
    }

    public void MmsMessage(String message) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void onClickConfirmButton(View view) {
        display();
    }



    public void onClickSendButton(View view) {
        display();
        MmsMessage(mMessageText.getText().toString());
    }

}











