package com.example.a4gbapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4gbapplication.databaseHelper.adminClass;
import com.example.a4gbapplication.databaseHelper.blacklistedCreditCardClass;
import com.example.a4gbapplication.databaseHelper.creditCardClass;
import com.example.a4gbapplication.databaseHelper.orderStatusClass;
import com.example.a4gbapplication.databaseHelper.priceConditionClass;
import com.example.a4gbapplication.databaseHelper.qtyConditionClass;
import com.example.a4gbapplication.databaseHelper.sellerClass;
import com.example.a4gbapplication.databaseHelper.userTypeClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dbDataConstructor extends AppCompatActivity {

    EditText inputText1, inputText2, inputText3, inputText4, inputText5, inputText6, inputText7, inputText8;

    Spinner spinner1;

    Button buttonAdd;

    TextView textViewMain, textViewSecond;


    DatabaseReference databaseCreditCard, databaseOrderStatus, databaseProductType,
            databaseUser;

    List<userTypeClass> userType;

    DatePickerDialog.OnClickListener dateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_data_constructor);

        databaseCreditCard = FirebaseDatabase.getInstance().getReference("Credit Card Detail");
        databaseOrderStatus = FirebaseDatabase.getInstance().getReference("Order Status");
        databaseProductType = FirebaseDatabase.getInstance().getReference("Product Type");

        inputText1 = (EditText) findViewById(R.id.inputText1);
        inputText2 = (EditText) findViewById(R.id.inputText2);
        inputText3 = (EditText) findViewById(R.id.inputText3);
        inputText4 = (EditText) findViewById(R.id.inputText4);
        inputText5 = (EditText) findViewById(R.id.inputText5);
        inputText6 = (EditText) findViewById(R.id.inputText6);
        inputText7 = (EditText) findViewById(R.id.inputText7);
        inputText8 = (EditText) findViewById(R.id.inputText8);

        textViewMain = (TextView) findViewById(R.id.textViewMain);
        textViewSecond = (TextView) findViewById(R.id.textViewSecond);

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        userType = new ArrayList<>();


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void addToWatchList(String customerName, String productID){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Watch List");
        String id = db.push().getKey();
    }

    public void createProductGroup(String productID) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Product Group");
        String id = db.push().getKey();
    }
    public void alternativeQtyCondition(float discountRate) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Alternative Quantity Condition");
        String id = db.push().getKey();

        qtyConditionClass qtyConditionClass = new qtyConditionClass(id, discountRate);
        databaseUser.child(id).setValue(qtyConditionClass);
    }
    public void alternativePrcCondition(float discountRate){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Alternative Quantity Condition");
        String id = db.push().getKey();

        priceConditionClass priceConditionClass = new priceConditionClass(id, discountRate);
        databaseUser.child(id).setValue(priceConditionClass);
    }

    public void blacklistCreditCart(String ccID) {
        DatabaseReference blacklistDB = FirebaseDatabase.getInstance().getReference("Blacklisted Credit Card");
        String id = blacklistDB.push().getKey();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
        String date = dateFormat.format(new Date());

        blacklistedCreditCardClass blacklistCreditCardClass = new blacklistedCreditCardClass(id, date, ccID);
        databaseUser.child(id).setValue(blacklistCreditCardClass);
    }

    public void addCustomer(final EditText email, final EditText contactNum, final EditText firstName, final EditText LastName,
                            final EditText password, final EditText confirmPassword, final EditText address,
                            final Spinner gender) {
        DatabaseReference databaseUserType = FirebaseDatabase.getInstance().getReference("User Type");
        databaseUserType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("ut_name").getValue().toString().equals("customer")) {
                        String ut_ID = snapshot.child("ut_ID").getValue().toString();
                        databaseUser = FirebaseDatabase.getInstance().getReference("User").child(snapshot.child("ut_name").getValue().toString());

                        String cus_email = email.getText().toString().trim();
                        String cus_contactNum = contactNum.getText().toString().trim();

                        if (!isPhoneNumValid(cus_contactNum)) {
                            email.setError("Email cannot be use");
                        }
                        if (!isEmailValid(cus_email)) {
                            contactNum.setError("Invalid phone number");
                        }

                        if (checkNull(firstName) || checkNull(LastName) || checkLength(password, 8, 16)
                        || checkLength(confirmPassword, 8, 16) || checkNull(address)) {
                            if (checkNull(firstName) && checkNull(LastName) && checkLength(password, 8, 16) &&
                                    checkLength(confirmPassword, 8, 16) && checkNull(address)
                                    && isPhoneNumValid(cus_contactNum) && isEmailValid(cus_email)) {
                                if (confirmingPassword(password, confirmPassword)) {



                                    String cus_firstName = firstName.getText().toString().trim();
                                    String cus_LastName = LastName.getText().toString().trim();
                                    String cus_password = password.getText().toString().trim();
                                    String cus_address = address.getText().toString().trim();
                                    String cus_gender = gender.getSelectedItem().toString();
                                    int cus_loyaltyPoint = 0;
                                    String a = ut_ID;

                                }
                            }
                        }
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void addCreditCard(EditText cc_num, EditText cc_NVM, String cusName) {
        if (checkLength(cc_num, 16, 16) || checkLength(cc_NVM, 3, 3)) {
            if (checkLength(cc_num, 16, 16) && checkLength(cc_NVM, 3, 3)) {

                String id = databaseCreditCard.push().getKey();
                String ccnum = cc_num.getText().toString().trim();
                String ccCVNum = cc_NVM.getText().toString().trim();

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/YY");
                String date = dateFormat.format(new Date());

                creditCardClass creditCardClass = new creditCardClass(id, ccCVNum, date, ccnum, cusName);
                databaseCreditCard.child(cusName).setValue(creditCardClass);
            } else {}
        } else {}
    }


    public void addSeller() {
        DatabaseReference databaseUserType = FirebaseDatabase.getInstance().getReference("User Type");
        databaseUserType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("ut_name").getValue().toString().equals("seller")) {
                        String ut_ID = snapshot.child("ut_ID").getValue().toString();
                        databaseUser = FirebaseDatabase.getInstance().getReference("User").child(snapshot.child("ut_name").getValue().toString());
                        if (checkNull(inputText1) && checkLength(inputText2, 8, 8) && checkNull(inputText3)) {
                            String email = inputText1.getText().toString().trim();
                            String contactNum = inputText2.getText().toString().trim();
                            String firstName = inputText3.getText().toString().trim();
                            String sgPassword = autoGeneratePassword(8);

                            String id = databaseUser.push().getKey();
                            sellerClass sellerClass = new sellerClass(id, firstName, email, sgPassword, contactNum, ut_ID);
                            databaseUser.child(firstName).setValue(sellerClass);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void addproductType(EditText productTypeName) {
        String name = productTypeName.getText().toString().trim();
        productTypeName.setText("");

        if (!TextUtils.isEmpty(name)) {
            String id = databaseProductType.push().getKey();

            orderStatusClass orderStatusClass = new orderStatusClass(id, name);
            databaseProductType.child(name).setValue(orderStatusClass);

            Toast.makeText(this, "Input Added", Toast.LENGTH_LONG).show();
        } else {
            productTypeName.setError("Field Empty");
        }
    }
    public void addorderStatus(EditText orderStatusName) {
        String name = orderStatusName.getText().toString().trim();
        orderStatusName.setText("");

        if (!TextUtils.isEmpty(name)) {
            String id = databaseOrderStatus.push().getKey();

            orderStatusClass orderStatusClass = new orderStatusClass(id, name);
            databaseOrderStatus.child(name).setValue(orderStatusClass);

            Toast.makeText(this, "Input Added", Toast.LENGTH_LONG).show();
        } else {
            orderStatusName.setError("Field Empty");
        }
    }
    public void addUserType(EditText userTypeName) {
        DatabaseReference databaseUserType = FirebaseDatabase.getInstance().getReference("User Type");
        String name = userTypeName.getText().toString().trim();
        userTypeName.setText("");

        if (!TextUtils.isEmpty(name)) {
            String id = databaseUserType.push().getKey();

            userTypeClass userTypeClass = new userTypeClass(id, name);
            databaseUserType.child(name).setValue(userTypeClass);

            Toast.makeText(this, "Input Added", Toast.LENGTH_LONG).show();
        } else {
            userTypeName.setError("Text Field's Empty");
        }
    }
    public void addAdmin() {
        DatabaseReference databaseUserType  = FirebaseDatabase.getInstance().getReference("User Type");
        databaseUserType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("ut_name").getValue().toString().equals("admin")) {
                        String ut_ID = snapshot.child("ut_ID").getValue().toString();
                        databaseUser = FirebaseDatabase.getInstance().getReference("User").child(snapshot.child("ut_name").getValue().toString());
                        String email = inputText1.getText().toString().trim();
                        String contactNum = inputText2.getText().toString().trim();
                        String firstName = inputText3.getText().toString().trim();
                        String lastName = inputText4.getText().toString().trim();
                        String password = inputText5.getText().toString().trim();
                        String gender = spinner1.getSelectedItem().toString();

                        String id = databaseUser.push().getKey();
                        adminClass adminClass = new adminClass(id, email, contactNum, firstName, lastName, password, gender, ut_ID);
                        String newChildName = firstName + " " + lastName;
                        databaseUser.child(newChildName).setValue(adminClass);
                        break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public static String autoGeneratePassword(int passwordLength) {
        String PASSWORD_CHARACTER ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUV@!&";
        Random random = new Random();
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            password.append(PASSWORD_CHARACTER.charAt(random.nextInt(PASSWORD_CHARACTER.length())));
        }
        return password.toString();
    }

    public static boolean checkLength(EditText editText, int minLength, int maxLength) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError("Field Required");
            return false;
        } if (text.length() < minLength) {
            editText.setError("Invalid Inputs");
            return false;
        } if (text.length() > maxLength) {
            editText.setError("Invalid Inputs");
            return false; }

        return true;

    }

    public static boolean checkNull(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError("Field Required");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPhoneNumValid(String phoneNum){
        String expression = "^[8,9]{1,1}+[0-9]{7,7}+$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }

    public static boolean isEmailValid(String email) {
        String expression1 = "^[a-zA-Z0-9_]{3}+@[hotmail]+\\.+[com, sg]+$";
        String expression2 = "^[a-zA-Z0-9_]{3}+@[email, gmail, outlook]+\\.+[com]+$";
        Pattern pattern1 = Pattern.compile(expression1, Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile(expression2, Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(email);
        Matcher matcher2 = pattern2.matcher(email);

        if (matcher1.matches()) {
            return matcher1.matches();
        } else {
            return matcher2.matches();
        }
    }

    public static boolean confirmingPassword(EditText password, EditText confirmPassword) {
        String mainText = password.getText().toString().trim();
        String confirmationText = confirmPassword.getText().toString().trim();
        if (confirmationText.equals(mainText)) {
            return true;
        } else {
            password.setError("Password does not match");
            confirmPassword.setError("Password does not match");
            return false;
        }
    }

}

