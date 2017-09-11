package com.chungmyung.ndivident;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

public class DetailCalculationActivity extends AppCompatActivity {


    private TextView mPeopleNum;
    private TextView mMaxmoneyText;
    private TextView mMoneyText;

    private LinearLayout mCalculAddNameLay;
    private String[] m1CalculAddName;
    private String[] m2CalculAddMoney;
    private Boolean[] m4CalculAddCheck;
    private Boolean[] m5CalculAddPay;

    private int checkedMoney;  // 고정 금액이 총 금액을 넘는지 체크
    private int checkedCount;
    private RadioButton mRadioButton0;
    private RadioButton mRadioButton100;
    private RadioButton mRadioButton10;
    private RadioButton mRadioButton1000;
    private String[] m3CalculAddMoney;
    private int division;
    private TextView mTitleText;


    private AdView mAdView;
    private int mAttendeeNumber;
    private int[] mId;
    private int[] mMoneyId;

    // 새로 만들어 추가된 에디트박스들의 평균 돈


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_calculation);


        mTitleText = (TextView) findViewById(R.id.calcul_title_text);
        mPeopleNum = (TextView) findViewById(R.id.calcul_peoplenum_text);
        mMaxmoneyText = (TextView) findViewById(R.id.calcul_maxmoney_text);
        mMoneyText = (TextView) findViewById(R.id.calcul_money_text);


//        mRadioButton0 = (RadioButton) findViewById(R.id.radio_button_c0);
        mRadioButton10 = (RadioButton) findViewById(R.id.radio_button_c10);
        mRadioButton100 = (RadioButton) findViewById(R.id.radio_button_c100);
        mRadioButton1000 = (RadioButton) findViewById(R.id.radio_button_c1000);


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        checkedCount = 0;    // 체크 박스를 아무것도 체크가 안됬다는 초기값

        // 추가 영역 확보
        mCalculAddNameLay = (LinearLayout) findViewById(R.id.add_all_lay);


        if (getIntent() != null) {

            DecimalFormat df = new DecimalFormat("###,###.####");

            String result = null;

            String title = getIntent().getStringExtra("title");
            String people_num = getIntent().getStringExtra("numattendee");
            String money = getIntent().getStringExtra("totalamount");



            mPeopleNum.setText(people_num);
            mAttendeeNumber = Integer.valueOf(mPeopleNum.getText().toString().replace(",", ""));

//            mId = new int[mAttendeeNumber];
//            mMoneyId = new int[mAttendeeNumber];
//
//            for (int i = 0; i < mAttendeeNumber; i++) {
//                mId[i] = View.generateViewId();
//                mMoneyId[i] = View.generateViewId();
//            }


            division = getIntent().getIntExtra("unit", 1);

            if (division == 10) {
                mRadioButton10.setChecked(true);
            } else if (division == 100) {
                mRadioButton100.setChecked(true);
            } else if (division == 1000) {
                mRadioButton1000.setChecked(true);
            }

            int people_money0
                    = (Integer.parseInt(money) / Integer.parseInt(people_num));
            int people_money = (int) ((double) people_money0 - (people_money0 % division));

            int division_people_money = (int) ((double) (people_money0 % division));

            mTitleText.setText(title);
            mMaxmoneyText.setText(money);  //전체 소요 금액



            result = df.format(Long.parseLong(mMaxmoneyText
                    .getText().toString().replaceAll(",", "")));  // 에딧텍스트의 값을 변환하여, result에 저장.

            mMaxmoneyText.setText(result);    // 숫자에 , 붙이기 결과 텍스트 셋팅.

// 이름 받는 배열 생성



            m1CalculAddName = new String[mAttendeeNumber];  // 이름
            m2CalculAddMoney = new String[mAttendeeNumber]; // 평균금액
            m3CalculAddMoney = new String[mAttendeeNumber]; // 절사단위
            m4CalculAddCheck = new Boolean[mAttendeeNumber];  // 고정 체크박스
            m5CalculAddPay = new Boolean[mAttendeeNumber];  // 결제자 체크박스


            for (int i = 0; i < mAttendeeNumber; i++) {  // 위것을 배열에 담기
                String n = getIntent().getStringExtra("namei" + i);
                m1CalculAddName[i] = n;
            }

            // 이름 받는 배열 생성

            mMoneyText.setText(String.valueOf(people_money));
            result = df.format(Long.parseLong(mMoneyText.getText().toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
            mMoneyText.setText(result);    // 결과 텍스트 셋팅.


            // 레이아웃 생성
            // 코드로 layout만들기
            for (int i = 0; i < mAttendeeNumber; i++) {
                LinearLayout parentLL = new LinearLayout(DetailCalculationActivity.this);
                parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                parentLL.setTag("AllLay" + i);
//                parentLL.setId(i);
                parentLL.setOrientation(LinearLayout.HORIZONTAL);
                mCalculAddNameLay.addView(parentLL);
            }


//                 이름 받아서 에디트 박스 수 생성
            for (int i = 0; i < mAttendeeNumber; i++) {
                LinearLayout Alllay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);

                final EditText nameEdit = new EditText(DetailCalculationActivity.this);

//                nameEdit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                Lp.weight = 1;
                nameEdit.setPadding(20, 10, 10, 10);
                nameEdit.setTextSize(16);
                nameEdit.setHint((i + 1) + ". " + "Name");
                nameEdit.setId(i);
                nameEdit.setTag("NameView" + i);
                nameEdit.requestFocus();   // focus강제로 주기
//                다음화면 Edittext로 넘기기
                nameEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                nameEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                nameEdit.setText(m1CalculAddName[i]);

                Alllay.addView(nameEdit, Lp);
            }

            // 이름 받아서 에디트 박스 수 생성
            // 평균 금액
            for (int i = 0; i < mAttendeeNumber; i++) {

                LinearLayout Alllay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);

                final EditText peopleMoneyText = new EditText(DetailCalculationActivity.this);
//                final EditNumber peopleMoneyText = new EditNumber(Calculation.this);


                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                Lp.weight = 1;
                peopleMoneyText.setPadding(20, 10, 10, 10);
                peopleMoneyText.setTextSize(16);
                peopleMoneyText.setHint((i + 1) + ". " + getString(R.string.amout));
                peopleMoneyText.setId(i);
                peopleMoneyText.setTag("MoneyView" + i);
// EditText input type에 숫자 입력 시
                peopleMoneyText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_NORMAL);
                peopleMoneyText.setText(mMoneyText.getText().toString());
//                .replace(",", "")
                m2CalculAddMoney[i] = mMoneyText.getText().toString().replace(",", "");


                peopleMoneyText.addTextChangedListener(new TextWatcher() {
                    String strAmount = "";

                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
//                       s is peopleMoneyText값


                        if (s.toString().isEmpty()) {
                            peopleMoneyText.setText("0");

                        } else {
                            if (!s.toString().equals(strAmount)) {

                                // strAmount변수생성으로 StackOverflow 방지
                                strAmount = makeStringComma(s.toString().replace(",", ""));

                                peopleMoneyText.setText(strAmount);


                                //  커서 위치가 앞으로 가는것 을 방지 setSelection
//                            코드로 작성하면 커저가 앞으로 있고 지울수가 없음. 해서
                                Editable e = peopleMoneyText.getText();
                                Selection.setSelection(e, strAmount.length());


                                // 추가 입력금액이 초과하거나 음수가 나올때  경고 메시지
                                if (Integer.valueOf(s.toString().replace(",", "")) > Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""))) {
                                    peopleMoneyText.setText(mMoneyText.getText().toString().replace(",", ""));
                                    Toast.makeText(DetailCalculationActivity.this, R.string.amountovertotal, Toast.LENGTH_SHORT).show();
                                } else if (Integer.valueOf(s.toString().replace(",", "")) < 0) {
                                    peopleMoneyText.setText(mMoneyText.getText().toString().replace(",", ""));
                                    Toast.makeText(DetailCalculationActivity.this, R.string.amountsmall, Toast.LENGTH_SHORT).show();
                                } else {
                                    m2CalculAddMoney[peopleMoneyText.getId()] = s.toString().replace(",", "");
                                }
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                    }

                    protected String makeStringComma(String str) {    // 천단위 콤마 처리
                        if (str.length() == 0)
                            return "";
                        long value = Long.parseLong(str);
                        DecimalFormat format = new DecimalFormat("###,###");
                        return format.format(value);
                    }
                });
                Alllay.addView(peopleMoneyText, Lp);
            }

// 평균 금액

//ddd  절사 테스트

            for (int i = 0; i < mAttendeeNumber; i++) {
                LinearLayout Alllay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);


                final TextView mDismoneyText = new TextView(DetailCalculationActivity.this);
//                final EditNumber mPeopleMoneyText = new EditNumber(Calculation.this);

                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                mDismoneyText.setTextSize(15);

                mDismoneyText.setId(i);
                mDismoneyText.setTag("Division" + i);

                mDismoneyText.setText("(" + division_people_money + ")");

                Alllay.addView(mDismoneyText, Lp);
            }


            ///  절사 테스트를========================================

            // 고정금액 체크 박스

            for (int i = 0; i < mAttendeeNumber; i++) {

                LinearLayout Alllay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);

                final CheckBox checkedMoney = new CheckBox(DetailCalculationActivity.this);

                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                checkedMoney.setTextSize(10);

                checkedMoney.setId(i);
                checkedMoney.setTag("CheckView" + i);

                // 고정 체크 했을때  확인하기. true로  정한다. intent로 보낸다.
                checkedMoney.setText(R.string.fix);
                m4CalculAddCheck[i] = false;

                checkedMoney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            m4CalculAddCheck[checkedMoney.getId()] = true;
                        } else {
                            m4CalculAddCheck[checkedMoney.getId()] = false;
                        }
                    }
                });

                Alllay.addView(checkedMoney, Lp);
            }

            // 고정금액 체크 박스
            for (int i = 0; i < mAttendeeNumber; i++) {

                LinearLayout Alllay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);
                final CheckBox checkedPay = new CheckBox(DetailCalculationActivity.this);

                LinearLayout.LayoutParams Lp =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                checkedPay.setTextSize(12);
                checkedPay.setId(i);
                checkedPay.setTag("PayView" + i);
                checkedPay.setText(R.string.payer);

                m5CalculAddPay[i] = false;
                checkedPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            if (checkedCount == 0) {
                                m5CalculAddPay[checkedPay.getId()] = true;
                                checkedCount = 1;
                            } else {
                                checkedPay.setChecked(false);
                                Toast.makeText(DetailCalculationActivity.this,
                                        R.string.onlyonepayer, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (m5CalculAddPay[checkedPay.getId()] == true) {
                                checkedCount = 0;
                                m5CalculAddPay[checkedPay.getId()] = false;
                            }
                        }
                    }
                });

                Alllay.addView(checkedPay, Lp);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        for (int i = 0; i < mAttendeeNumber; i++) {

            LinearLayout add_lay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);
            EditText peopName = (EditText) add_lay.findViewWithTag("NameView" + i);
            EditText peopMoney = (EditText) add_lay.findViewWithTag("MoneyView"+i);
            TextView cutUnit = (TextView) add_lay.findViewWithTag("Division" + i);
            CheckBox money_checked = (CheckBox) add_lay.findViewWithTag("CheckView" + i);
            CheckBox pay_checked = (CheckBox) add_lay.findViewWithTag("PayView" + i);


            outState.putString("nameView" + i, peopName.getText().toString());
            outState.putString("money" + i, peopMoney.getText().toString());
            outState.putString("cutUnit" + i, cutUnit.getText().toString());

            outState.putBoolean("checkView" + i, money_checked.isChecked());
            outState.putBoolean("payView" + i, pay_checked.isChecked());


        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if ( savedInstanceState != null) {
        for ( int i =0 ; i < mAttendeeNumber ; i++) {

            LinearLayout add_lay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);
            EditText peopName = (EditText) add_lay.findViewWithTag("NameView" + i);
            EditText peopMoney = (EditText) add_lay.findViewWithTag("MoneyView"+i);
            TextView cutUnit = (TextView) add_lay.findViewWithTag("Division" + i);
            CheckBox money_checked = (CheckBox) add_lay.findViewWithTag("CheckView" + i);
            CheckBox pay_checked = (CheckBox) add_lay.findViewWithTag("PayView" + i);


            peopName.setText(savedInstanceState.getString("nameView" + i) );
            peopMoney.setText(savedInstanceState.getString("money"  +i ));
            cutUnit.setText(savedInstanceState.getString("cutUnit"  +i ));

            money_checked.setChecked(savedInstanceState.getBoolean("checkView" + i));
            pay_checked.setChecked(savedInstanceState.getBoolean("payView" + i));






}
        }





        }

    // 상세 계산,  결과 확인 버튼 작동시
    public void onClickButton(View view) {

        int people_num = mAttendeeNumber; // 사람 수 만큼 for문을 위해

        if (mRadioButton10.isChecked()) {
            division = 10;
        } else if (mRadioButton100.isChecked()) {
            division = 100;
        } else if (mRadioButton1000.isChecked()) {
            division = 1000;
        }

        switch (view.getId()) {
            case R.id.button_calc:

                //고정금액 - 총금액을 위해 -- 실 분담해야 할 금액
                int minusMoney = Integer.parseInt(mMaxmoneyText.getText().toString().replace(",", ""));
                // 한 사람당 돌아갈 동
                int newPeopleMoney = 0;

                //체크한 돈 총액 비교를 위해
                checkedMoney = 0;

                // 체크한 사람 숫자
                int checkedMoneyPeopleCount = 0;

                for (int i = 0; i < people_num; i++) {
                    if (m4CalculAddCheck[i]) {
                        checkedMoney = checkedMoney + Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        checkedMoneyPeopleCount++;
                    }
                }
                if (checkedMoneyPeopleCount == mAttendeeNumber) {
                    Toast.makeText(this, R.string.selectagainallchex, Toast.LENGTH_SHORT).show();

                } else if (checkedMoney > Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""))) {
                    Toast.makeText(this, R.string.selectagainfixamountover, Toast.LENGTH_SHORT).show();

                } else {
                    //  고정 체크된 박스  true 이면 minusMoney = 실 총금액 다시 계산
                    for (int i = 0; i < mAttendeeNumber; i++) {
                        if (m4CalculAddCheck[i]) {
                            minusMoney = minusMoney - Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        }
                    }


                    if (minusMoney > mAttendeeNumber) {
                        int newPeopleMoney0
                                = minusMoney / mAttendeeNumber - checkedMoneyPeopleCount;

                        int new_division_people_money = (int) ((double) (newPeopleMoney0 % division));

                        newPeopleMoney = (int) (newPeopleMoney0 - ((double) (newPeopleMoney0 % division)));


                        for (int i = 0; i < mAttendeeNumber; i++) {

                            // 고정 금액 지원자가 없을 때
                            if (!m4CalculAddCheck[i]) {

                                LinearLayout add_lay =
                                        (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);

                                EditText PeopleMoney = (EditText) add_lay.findViewWithTag("MoneyView" + i);
                                TextView divisionMoney = (TextView) add_lay.findViewWithTag("Division" + i);
                                PeopleMoney.setText("" + newPeopleMoney);

                                m2CalculAddMoney[i] = PeopleMoney.getText().toString(); // 나눈 금액

                                divisionMoney.setText("(" + new_division_people_money + ")");
                                m3CalculAddMoney[i] = divisionMoney.getText().toString(); //절사 단위
                            } else {

                                LinearLayout add_lay =
                                        (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);
                                TextView divisionMoney = (TextView) add_lay.findViewWithTag("Division" + i);
                                divisionMoney.setText("(" + 0 + ")");
                            }
                        }
                        // 고정 금액이 총 금액일 때 연산이 죽음. 방지

                    } else if (minusMoney == 0) {
                        int newPeopleMoney0 = minusMoney / mAttendeeNumber - checkedMoneyPeopleCount;

                        newPeopleMoney = (int) (newPeopleMoney0 - ((double) (newPeopleMoney0 % division)));

                        int new_division_people_money = (int) ((double) (newPeopleMoney0 % division));
                        for (int i = 0; i < mAttendeeNumber; i++) {
                            if (!m4CalculAddCheck[i]) {
                                LinearLayout add_lay = (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);
                                EditText PeopleMoney = (EditText) add_lay.findViewWithTag("MoneyView" + i);
                                TextView divisionMoney = (TextView) add_lay.findViewWithTag("Division" + i);
                                PeopleMoney.setText("" + 0);
                                m2CalculAddMoney[i] = PeopleMoney.getText().toString();
                                divisionMoney.setText("(" + new_division_people_money + ")");
                                m3CalculAddMoney[i] = divisionMoney.getText().toString();

                            }
                        }
                    }
                }
                break;

            case R.id.button_ok:
                //고정금액 - 총금액을 위해
                int minusMoney_2 = Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""));
                // 한 사람당 돌아갈 동
                int newPeopleMoney_2 = 0;
                //체크한 돈 총액 비교를 위해
                checkedMoney = 0;

                // 체크한 사람 숫자
                int checkedMoneyPeopleCount_2 = 0;

                for (int i = 0; i < people_num; i++) {
                    if (m4CalculAddCheck[i]) {
                        checkedMoney = checkedMoney + Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        checkedMoneyPeopleCount_2++;
                    }
                }
                if (checkedMoneyPeopleCount_2 == mAttendeeNumber) {

                    Toast.makeText(this, getString(R.string.test), Toast.LENGTH_SHORT).show();

                } else if (checkedMoney > Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""))) {
                    Toast.makeText(this, R.string.inputfixedamountagain,
                            Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < mAttendeeNumber; i++) {
                        if (m4CalculAddCheck[i]) {
                            minusMoney_2 = minusMoney_2 - Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        }
                    }


                    if (minusMoney_2 > mAttendeeNumber) {
                        int newPeopleMoney0 = minusMoney_2 / mAttendeeNumber
                                - checkedMoneyPeopleCount_2;

                        newPeopleMoney = (int) (newPeopleMoney0 - ((double) (newPeopleMoney0 % division)));

                        int new_division_people_money = (int) ((double) (newPeopleMoney0 % division));

                        for (int i = 0; i < mAttendeeNumber; i++) {
                            if (!m4CalculAddCheck[i]) {
                                LinearLayout add_lay =
                                        (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);
                                EditText PeopleMoney = (EditText) add_lay.findViewWithTag("MoneyView" + i);
                                TextView divisionMoney = (TextView) add_lay.findViewWithTag("Division" + i);
                                PeopleMoney.setText("" + newPeopleMoney);
                                m2CalculAddMoney[i] = PeopleMoney.getText().toString();
                                divisionMoney.setText("(" + new_division_people_money + ")");
                                m3CalculAddMoney[i] = divisionMoney.getText().toString();


                            } else {

                                LinearLayout add_lay =
                                        (LinearLayout) mCalculAddNameLay.findViewWithTag("AllLay" + i);
                                TextView divisionMoney = (TextView) add_lay.findViewWithTag("Division" + i);
                                divisionMoney.setText("(" + 0 + ")");
                            }
                        }
                    }

                    int calcPeopleCheck = 32;
                    String calcPeopleName;
                    Intent intent = new Intent(DetailCalculationActivity.this, Result_message_Activity.class);
                    intent.putExtra("paycheck", "NO");
                    for (int i = 0; i < people_num; i++) {

                        if (m5CalculAddPay[i] == true) {

                            calcPeopleCheck = i;

                            LinearLayout add_lay = (LinearLayout) mCalculAddNameLay.
                                    findViewWithTag("AllLay" + i);
                            EditText PeopleName = (EditText) add_lay.findViewWithTag("NameView" + i);
                            TextView divisionMoney = (TextView) add_lay.findViewWithTag("Division" + i);
                            calcPeopleName = PeopleName.getText().toString();

                            intent.putExtra("paypeople", calcPeopleName);
                            intent.putExtra("paydivision", divisionMoney.getText().toString());
                            intent.putExtra("paycheck", "OK");

                        } else {

                        }
                    }


                    for (int i = 0; i < people_num; i++) {
                        if (i != calcPeopleCheck) {
                            LinearLayout add_lay = (LinearLayout) mCalculAddNameLay.
                                    findViewWithTag("AllLay" + i);
                            EditText PeopleName = (EditText) add_lay.findViewWithTag("NameView" + i);
                            String N = PeopleName.getText().toString();

                            EditText PeopleMoney = (EditText) add_lay.findViewWithTag("MoneyView" + i);
                            String M = PeopleMoney.getText().toString();

                            TextView divisionMoney = (TextView) add_lay.findViewWithTag("Division" + i);
                            String D = divisionMoney.getText().toString();

                            intent.putExtra("name" + i, N);
                            intent.putExtra("Money" + i, M);
                            intent.putExtra("division" + i, D);

                        } else {

                        }
                    }
                    intent.putExtra("MaxMoney", mMaxmoneyText.getText().toString());
                    intent.putExtra("num", mPeopleNum.getText().toString());
                    intent.putExtra("title", mTitleText.getText().toString());
                    intent.putExtra("checkPeople", calcPeopleCheck);
                    startActivity(intent);
                }



        }
    }

}





