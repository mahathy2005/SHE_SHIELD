package com.sheshield.app;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_CODE = 1001;

    // =========================
    // PERSONAL DETAILS
    // =========================

    private TextInputEditText etFullName;
    private TextInputEditText etDob;
    private TextInputEditText etAge;
    private TextInputEditText etEmail;
    private TextInputEditText etUserMobile;
    private TextInputEditText etAddress;
    private TextInputEditText etAbout;

    private AutoCompleteTextView etBloodGroup;

    // =========================
    // GUARDIAN DETAILS
    // =========================

    private TextInputEditText etGuardianName;
    private TextInputEditText etGuardianMobile;
    private TextInputEditText etGuardianEmail;
    private TextInputEditText etGuardianAddress;

    private AutoCompleteTextView etGuardianRelation;
    private AutoCompleteTextView etGuardianBloodGroup;

    // =========================
    // BUTTONS
    // =========================

    private MaterialButton btnNext;
    private MaterialButton btnFetchLocation;

    // =========================
    // DROPDOWN ARRAYS
    // =========================

    private final String[] bloodGroups = {
            "A+", "A-", "B+", "B-",
            "AB+", "AB-", "O+", "O-"
    };

    private final String[] relations = {
            "Father",
            "Mother",
            "Spouse",
            "Sibling",
            "Legal Guardian",
            "Other"
    };

    // =========================
    // LOCATION
    // =========================

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);


        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);


        initViews();

        setupDropdownAdapters();


        // Date Picker

        etDob.setOnClickListener(
                v -> showDatePicker()
        );


        // Fetch Current Location

        btnFetchLocation.setOnClickListener(
                v -> checkLocationPermissionAndFetch()
        );


        // Next Button

        btnNext.setOnClickListener(v -> {

            if (validateInputs()) {

                navigateToPasswordSetup();

            }
        });
    }


    // =========================
    // INITIALIZE VIEWS
    // =========================

    private void initViews() {

        // Personal Details

        etFullName =
                findViewById(R.id.etFullName);

        etDob =
                findViewById(R.id.etDob);

        etAge =
                findViewById(R.id.etAge);

        etBloodGroup =
                findViewById(R.id.etBloodGroup);

        etEmail =
                findViewById(R.id.etEmail);

        etUserMobile =
                findViewById(R.id.etUserMobile);

        etAddress =
                findViewById(R.id.etAddress);

        etAbout =
                findViewById(R.id.etAbout);


        // Guardian Details

        etGuardianName =
                findViewById(R.id.etGuardianName);

        etGuardianRelation =
                findViewById(R.id.etGuardianRelation);

        etGuardianMobile =
                findViewById(R.id.etGuardianMobile);

        etGuardianBloodGroup =
                findViewById(R.id.etGuardianBloodGroup);

        etGuardianEmail =
                findViewById(R.id.etGuardianEmail);

        etGuardianAddress =
                findViewById(R.id.etGuardianAddress);


        // Buttons

        btnNext =
                findViewById(R.id.btnNext);

        btnFetchLocation =
                findViewById(R.id.btnFetchLocation);
    }


    // =========================
    // SETUP DROPDOWNS
    // =========================

    private void setupDropdownAdapters() {

        ArrayAdapter<String> bloodAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        bloodGroups
                );


        etBloodGroup.setAdapter(
                bloodAdapter
        );


        etGuardianBloodGroup.setAdapter(
                bloodAdapter
        );


        ArrayAdapter<String> relationAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        relations
                );


        etGuardianRelation.setAdapter(
                relationAdapter
        );
    }


    // =========================
    // LOCATION PERMISSION
    // =========================

    private void checkLocationPermissionAndFetch() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_CODE
            );

        } else {

            getCurrentLocation();
        }
    }


    // =========================
    // GET CURRENT LOCATION
    // =========================

    @SuppressWarnings("MissingPermission")
    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        Toast.makeText(
                this,
                "Fetching current location...",
                Toast.LENGTH_SHORT
        ).show();


        fusedLocationClient
                .getLastLocation()
                .addOnSuccessListener(
                        this,
                        location -> {

                            if (location != null) {

                                convertLocationToAddress(
                                        location
                                );

                            } else {

                                Toast.makeText(
                                        this,
                                        "Unable to get location. Ensure GPS is ON.",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =========================
    // CONVERT LOCATION TO ADDRESS
    // =========================

    private void convertLocationToAddress(
            Location location
    ) {

        Geocoder geocoder =
                new Geocoder(
                        this,
                        Locale.getDefault()
                );


        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.TIRAMISU) {


            geocoder.getFromLocation(

                    location.getLatitude(),

                    location.getLongitude(),

                    1,

                    addresses -> {

                        if (addresses != null
                                &&
                                !addresses.isEmpty()) {

                            Address returnedAddress =
                                    addresses.get(0);


                            StringBuilder addressBuilder =
                                    new StringBuilder();


                            for (
                                    int i = 0;
                                    i <= returnedAddress
                                            .getMaxAddressLineIndex();
                                    i++
                            ) {

                                addressBuilder
                                        .append(
                                                returnedAddress
                                                        .getAddressLine(i)
                                        )
                                        .append("\n");
                            }


                            runOnUiThread(
                                    () -> etAddress.setText(
                                            addressBuilder
                                                    .toString()
                                                    .trim()
                                    )
                            );

                        } else {

                            runOnUiThread(
                                    () ->
                                            Toast.makeText(
                                                    SignUpActivity.this,
                                                    "No address found",
                                                    Toast.LENGTH_SHORT
                                            ).show()
                            );
                        }
                    }
            );

        } else {

            try {

                @SuppressWarnings("deprecation")
                List<Address> addresses =
                        geocoder.getFromLocation(
                                location.getLatitude(),
                                location.getLongitude(),
                                1
                        );


                if (addresses != null
                        &&
                        !addresses.isEmpty()) {

                    Address returnedAddress =
                            addresses.get(0);


                    StringBuilder addressBuilder =
                            new StringBuilder();


                    for (
                            int i = 0;
                            i <= returnedAddress
                                    .getMaxAddressLineIndex();
                            i++
                    ) {

                        addressBuilder
                                .append(
                                        returnedAddress
                                                .getAddressLine(i)
                                )
                                .append("\n");
                    }


                    etAddress.setText(
                            addressBuilder
                                    .toString()
                                    .trim()
                    );

                } else {

                    Toast.makeText(
                            this,
                            "No address found",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            } catch (IOException e) {

                e.printStackTrace();


                Toast.makeText(
                        this,
                        "Location service error. Please enter manually.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }


    // =========================
    // LOCATION PERMISSION RESULT
    // =========================

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );


        if (requestCode ==
                LOCATION_PERMISSION_CODE) {

            if (grantResults.length > 0
                    &&
                    grantResults[0]
                            == PackageManager.PERMISSION_GRANTED) {

                getCurrentLocation();

            } else {

                Toast.makeText(
                        this,
                        "Location permission denied",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }


    // =========================
    // DATE PICKER
    // =========================

    private void showDatePicker() {

        Calendar calendar =
                Calendar.getInstance();


        int currentYear =
                calendar.get(Calendar.YEAR);

        int currentMonth =
                calendar.get(Calendar.MONTH);

        int currentDay =
                calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog =
                new DatePickerDialog(

                        this,

                        (
                                view,
                                selectedYear,
                                selectedMonth,
                                selectedDay
                        ) -> {

                            String selectedDob =
                                    selectedDay
                                            + "/"
                                            + (selectedMonth + 1)
                                            + "/"
                                            + selectedYear;


                            etDob.setText(
                                    selectedDob
                            );


                            int calculatedAge =
                                    currentYear
                                            - selectedYear;


                            if (
                                    calendar.get(Calendar.MONTH)
                                            < selectedMonth

                                            ||

                                            (
                                                    calendar.get(
                                                            Calendar.MONTH
                                                    )
                                                            == selectedMonth

                                                            &&

                                                            calendar.get(
                                                                    Calendar.DAY_OF_MONTH
                                                            )
                                                                    < selectedDay
                                            )
                            ) {

                                calculatedAge--;
                            }


                            etAge.setText(
                                    String.valueOf(
                                            Math.max(
                                                    0,
                                                    calculatedAge
                                            )
                                    )
                            );

                        },

                        currentYear,
                        currentMonth,
                        currentDay
                );


        datePickerDialog
                .getDatePicker()
                .setMaxDate(
                        System.currentTimeMillis()
                );


        datePickerDialog.show();
    }


    // =========================
    // VALIDATE INPUTS
    // =========================

    private boolean validateInputs() {

        if (isEmpty(etFullName)) {

            return showError(
                    etFullName,
                    "Full name required"
            );
        }


        if (isEmpty(etDob)) {

            return showError(
                    etDob,
                    "Select Date of Birth"
            );
        }


        if (isEmpty(etBloodGroup)) {

            return showError(
                    etBloodGroup,
                    "Select Blood Group"
            );
        }


        if (isEmpty(etEmail)
                ||
                !isValidEmail(etEmail)) {

            return showError(
                    etEmail,
                    "Valid email required"
            );
        }


        if (isEmpty(etUserMobile)
                ||
                !isValidMobile(etUserMobile)) {

            return showError(
                    etUserMobile,
                    "Valid 10-digit mobile required"
            );
        }


        if (isEmpty(etAddress)) {

            return showError(
                    etAddress,
                    "Address required"
            );
        }


        if (isEmpty(etGuardianName)) {

            return showError(
                    etGuardianName,
                    "Guardian name required"
            );
        }


        if (isEmpty(etGuardianRelation)) {

            return showError(
                    etGuardianRelation,
                    "Select Relation"
            );
        }


        if (isEmpty(etGuardianMobile)
                ||
                !isValidMobile(etGuardianMobile)) {

            return showError(
                    etGuardianMobile,
                    "Valid 10-digit mobile required"
            );
        }


        if (isEmpty(etGuardianBloodGroup)) {

            return showError(
                    etGuardianBloodGroup,
                    "Select Guardian Blood Group"
            );
        }


        if (isEmpty(etGuardianEmail)
                ||
                !isValidEmail(etGuardianEmail)) {

            return showError(
                    etGuardianEmail,
                    "Valid guardian email required"
            );
        }


        if (isEmpty(etGuardianAddress)) {

            return showError(
                    etGuardianAddress,
                    "Guardian address required"
            );
        }


        return true;
    }


    // =========================
    // EMPTY CHECK
    // =========================

    private boolean isEmpty(
            TextInputEditText editText
    ) {

        return editText.getText() == null
                ||
                TextUtils.isEmpty(
                        editText
                                .getText()
                                .toString()
                                .trim()
                );
    }


    private boolean isEmpty(
            AutoCompleteTextView dropdownView
    ) {

        return dropdownView.getText() == null
                ||
                TextUtils.isEmpty(
                        dropdownView
                                .getText()
                                .toString()
                                .trim()
                );
    }


    // =========================
    // EMAIL VALIDATION
    // =========================

    private boolean isValidEmail(
            TextInputEditText editText
    ) {

        return Patterns.EMAIL_ADDRESS
                .matcher(
                        editText
                                .getText()
                                .toString()
                                .trim()
                )
                .matches();
    }


    // =========================
    // MOBILE VALIDATION
    // =========================

    private boolean isValidMobile(
            TextInputEditText editText
    ) {

        return editText
                .getText()
                .toString()
                .trim()
                .matches(
                        "^[6-9]\\d{9}$"
                );
    }


    // =========================
    // SHOW ERROR
    // =========================

    private boolean showError(
            TextInputEditText editText,
            String message
    ) {

        editText.setError(message);

        editText.requestFocus();

        return false;
    }


    private boolean showError(
            AutoCompleteTextView dropdownView,
            String message
    ) {

        dropdownView.setError(message);

        dropdownView.requestFocus();

        return false;
    }


    // =========================
    // GO TO PASSWORD SETUP
    // =========================

    private void navigateToPasswordSetup() {

        Intent intent =
                new Intent(
                        SignUpActivity.this,
                        PasswordSetupActivity.class
                );


        // IMPORTANT:
        // Full name is passed to PasswordSetupActivity

        intent.putExtra(
                "fullName",
                etFullName
                        .getText()
                        .toString()
                        .trim()
        );


        // Personal Details

        intent.putExtra(
                "dob",
                etDob.getText().toString().trim()
        );

        intent.putExtra(
                "age",
                etAge.getText().toString().trim()
        );

        intent.putExtra(
                "bloodGroup",
                etBloodGroup.getText().toString().trim()
        );

        intent.putExtra(
                "email",
                etEmail.getText().toString().trim()
        );

        intent.putExtra(
                "userMobile",
                etUserMobile.getText().toString().trim()
        );

        intent.putExtra(
                "address",
                etAddress.getText().toString().trim()
        );

        intent.putExtra(
                "about",
                etAbout.getText().toString().trim()
        );


        // Guardian Details

        intent.putExtra(
                "guardianName",
                etGuardianName.getText().toString().trim()
        );

        intent.putExtra(
                "guardianRelation",
                etGuardianRelation.getText().toString().trim()
        );

        intent.putExtra(
                "guardianMobile",
                etGuardianMobile.getText().toString().trim()
        );

        intent.putExtra(
                "guardianBloodGroup",
                etGuardianBloodGroup.getText().toString().trim()
        );

        intent.putExtra(
                "guardianEmail",
                etGuardianEmail.getText().toString().trim()
        );

        intent.putExtra(
                "guardianAddress",
                etGuardianAddress.getText().toString().trim()
        );


        startActivity(intent);
    }
}