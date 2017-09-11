package com.chungmyung.ndivident;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

public class FirstCalculationActivity extends AppCompatActivity {


    private EditText mBankName;
    private EditText mBankNum;
    private EditText mHolder;

    private String mSendDisplay;
    private String mTitle;
    private String mTotalamount;
    private String mNumber;
    private String mAmount;
    private int mAmountPerAttendee;
    private int mDeleted_amount_attendee;

//    private static final String TAG = "MainActivity";

    private AdView mAdView;
    private TextView mMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_calculation);


        mBankName = (EditText) findViewById(R.id.bankname_edit);
        mBankNum = (EditText) findViewById(R.id.banknum_edit);
        mHolder = (EditText) findViewById(R.id.holder_edit);
        mMessage = (TextView) findViewById(R.id.simple_amount_text);


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        DecimalFormat df = new DecimalFormat("###,###.####");


        if (getIntent() != null) {

            mTitle = getIntent().getStringExtra("title");
            mTotalamount = getIntent().getStringExtra("totalamount");
 ;
            mNumber = getIntent().getStringExtra("numattendee");

            mAmountPerAttendee = Integer.parseInt(mTotalamount);

            int delete = getIntent().getIntExtra("delete", 1);
                mAmountPerAttendee =
                        (Integer.parseInt(mTotalamount) / Integer.parseInt(mNumber));
                mDeleted_amount_attendee =
                        (int) ((double) mAmountPerAttendee - (mAmountPerAttendee) % delete);


        }

        display();
    }

    private void display() {
        String bankname = mBankName.getText().toString();
        String accountnum = mBankNum.getText().toString();
        String holdername = mHolder.getText().toString();


        if (bankname.equals("") && accountnum.equals("")) {

            mMessage.setText("==================================" + "\n"
                    + "Title  ; " + mTitle + "\n"
                    + "Total Spendt Amount ;  " + mTotalamount + "\n"
                    + "==================================" + "\n"
                    + getString(R.string.portionattend) + mDeleted_amount_attendee + "\n"
                    + "==================================" + "\n"
            );
        } else {
            mMessage.setText("==================================" + "\n"
                    + "Title ; " + mTitle + "\n"
                    + "Total Spent Amount ;  " + mTotalamount + "\n"
                    + "==================================" + "\n"
                    + getString(R.string.portionofattendee) + mDeleted_amount_attendee + "\n"
                    + "==================================" + "\n"
                    + "Bank Name  ; " + bankname + "\n"
                    + "Account Number ; " + accountnum + "\n"
                    + " Owner; " + holdername + "\n"
                    + "=================================" + "\n"
            );
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
        MmsMessage(mSendDisplay);
    }
}
