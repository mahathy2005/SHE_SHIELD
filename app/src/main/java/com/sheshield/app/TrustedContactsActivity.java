package com.sheshield.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class TrustedContactsActivity extends BaseActivity {

    // ============================================================
    // PREFERENCES
    // ============================================================

    private static final String PREF_NAME = "TrustedContacts";
    private static final String CONTACTS_KEY = "contacts";

    private SharedPreferences preferences;
    private LinearLayout contactsContainer;

    // ============================================================
    // COLORS
    // ============================================================

    private static final int PINK =
            Color.parseColor("#E91E63");

    private static final int PURPLE =
            Color.parseColor("#8E24AA");

    private static final int DARK_TEXT =
            Color.parseColor("#2D2B52");

    private static final int GREY_TEXT =
            Color.parseColor("#777487");

    private static final int SOFT_PINK =
            Color.parseColor("#FCE4EC");

    private static final int WHITE =
            Color.WHITE;

    // ============================================================
    // ON CREATE
    // ============================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_trusted_contacts
        );

        preferences = getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );

        contactsContainer =
                findViewById(
                        R.id.contactsContainer
                );

        // ========================================================
        // BACK BUTTON
        // ========================================================

        View btnBack =
                findViewById(
                        R.id.btnBack
                );

        if (btnBack != null) {

            btnBack.setOnClickListener(
                    v -> finish()
            );
        }

        // ========================================================
        // ADD CONTACT BUTTON
        // ========================================================

        MaterialButton btnAddContact =
                findViewById(
                        R.id.btnAddContact
                );

        if (btnAddContact != null) {

            btnAddContact.setOnClickListener(
                    v -> showAddContactDialog(-1)
            );
        }

        // ========================================================
        // LOAD CONTACTS
        // ========================================================

        loadContacts();
    }

    // ============================================================
    // SHOW ADD / EDIT CONTACT DIALOG
    //
    // position = -1 → ADD
    // position >= 0 → EDIT
    // ============================================================

    private void showAddContactDialog(
            int position
    ) {

        View dialogView =
                getLayoutInflater().inflate(
                        R.layout.dialog_add_trusted_contact,
                        null
                );

        // ========================================================
        // FORM FIELDS
        // SAME IDs AS SIGNUP
        // ========================================================

        EditText etGuardianName =
                dialogView.findViewById(
                        R.id.etGuardianName
                );

        AutoCompleteTextView etGuardianRelation =
                dialogView.findViewById(
                        R.id.etGuardianRelation
                );

        EditText etGuardianMobile =
                dialogView.findViewById(
                        R.id.etGuardianMobile
                );

        AutoCompleteTextView etGuardianBloodGroup =
                dialogView.findViewById(
                        R.id.etGuardianBloodGroup
                );

        EditText etGuardianEmail =
                dialogView.findViewById(
                        R.id.etGuardianEmail
                );

        EditText etGuardianAddress =
                dialogView.findViewById(
                        R.id.etGuardianAddress
                );

        // ========================================================
        // CUSTOM XML BUTTONS
        // ========================================================

        MaterialButton btnCancel =
                dialogView.findViewById(
                        R.id.btnCancel
                );

        MaterialButton btnSave =
                dialogView.findViewById(
                        R.id.btnSave
                );

        // ========================================================
        // SAFETY CHECK
        // ========================================================

        if (etGuardianName == null ||
                etGuardianRelation == null ||
                etGuardianMobile == null ||
                etGuardianBloodGroup == null ||
                etGuardianEmail == null ||
                etGuardianAddress == null ||
                btnCancel == null ||
                btnSave == null) {

            Toast.makeText(
                    this,
                    "Trusted contact form IDs are incomplete",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // ========================================================
        // RELATION OPTIONS
        // ========================================================

        String[] relations = {

                "Mother",
                "Father",
                "Sister",
                "Brother",
                "Daughter",
                "Son",
                "Spouse",
                "Partner",
                "Friend",
                "Relative",
                "Guardian",
                "Colleague",
                "Other"
        };

        ArrayAdapter<String> relationAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        relations
                );

        etGuardianRelation.setAdapter(
                relationAdapter
        );

        etGuardianRelation.setInputType(
                InputType.TYPE_NULL
        );

        etGuardianRelation.setOnClickListener(
                v -> etGuardianRelation.showDropDown()
        );

        // ========================================================
        // BLOOD GROUP OPTIONS
        // ========================================================

        String[] bloodGroups = {

                "A+",
                "A-",
                "B+",
                "B-",
                "AB+",
                "AB-",
                "O+",
                "O-"
        };

        ArrayAdapter<String> bloodAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        bloodGroups
                );

        etGuardianBloodGroup.setAdapter(
                bloodAdapter
        );

        etGuardianBloodGroup.setInputType(
                InputType.TYPE_NULL
        );

        etGuardianBloodGroup.setOnClickListener(
                v -> etGuardianBloodGroup.showDropDown()
        );

        // ========================================================
        // EDIT MODE
        // LOAD EXISTING DATA
        // ========================================================

        if (position >= 0) {

            try {

                String existing =
                        preferences.getString(
                                CONTACTS_KEY,
                                "[]"
                        );

                JSONArray contacts =
                        new JSONArray(existing);

                if (position < contacts.length()) {

                    JSONObject contact =
                            contacts.getJSONObject(
                                    position
                            );

                    etGuardianName.setText(
                            contact.optString(
                                    "name",
                                    ""
                            )
                    );

                    etGuardianRelation.setText(
                            contact.optString(
                                    "relation",
                                    ""
                            ),
                            false
                    );

                    etGuardianMobile.setText(
                            contact.optString(
                                    "mobile",
                                    ""
                            )
                    );

                    etGuardianBloodGroup.setText(
                            contact.optString(
                                    "bloodGroup",
                                    ""
                            ),
                            false
                    );

                    etGuardianEmail.setText(
                            contact.optString(
                                    "email",
                                    ""
                            )
                    );

                    etGuardianAddress.setText(
                            contact.optString(
                                    "address",
                                    ""
                            )
                    );

                    btnSave.setText(
                            "Update Contact"
                    );
                }

            } catch (Exception e) {

                e.printStackTrace();

                Toast.makeText(
                        this,
                        "Could not load contact details",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }

        // ========================================================
        // CREATE DIALOG
        // ONLY XML BUTTONS
        // ========================================================

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setView(dialogView)
                        .create();

        dialog.setCanceledOnTouchOutside(false);

        // ========================================================
        // CANCEL
        // ========================================================

        btnCancel.setOnClickListener(
                v -> dialog.dismiss()
        );

        // ========================================================
        // SAVE / UPDATE
        // ========================================================

        btnSave.setOnClickListener(v -> {

            String name =
                    etGuardianName
                            .getText()
                            .toString()
                            .trim();

            String relation =
                    etGuardianRelation
                            .getText()
                            .toString()
                            .trim();

            String mobile =
                    etGuardianMobile
                            .getText()
                            .toString()
                            .trim();

            String bloodGroup =
                    etGuardianBloodGroup
                            .getText()
                            .toString()
                            .trim();

            String email =
                    etGuardianEmail
                            .getText()
                            .toString()
                            .trim();

            String address =
                    etGuardianAddress
                            .getText()
                            .toString()
                            .trim();

            // ====================================================
            // NAME VALIDATION
            // ====================================================

            if (name.isEmpty()) {

                etGuardianName.setError(
                        "Please enter full name"
                );

                etGuardianName.requestFocus();

                return;
            }

            if (!name.matches(
                    "^[a-zA-Z][a-zA-Z .'-]{1,49}$"
            )) {

                etGuardianName.setError(
                        "Please enter a valid name"
                );

                etGuardianName.requestFocus();

                return;
            }

            // ====================================================
            // RELATION VALIDATION
            // ====================================================

            if (relation.isEmpty()) {

                etGuardianRelation.setError(
                        "Please select relationship"
                );

                etGuardianRelation.requestFocus();

                return;
            }

            // ====================================================
            // MOBILE VALIDATION
            // ====================================================

            if (mobile.isEmpty()) {

                etGuardianMobile.setError(
                        "Please enter mobile number"
                );

                etGuardianMobile.requestFocus();

                return;
            }

            if (!mobile.matches(
                    "^[6-9][0-9]{9}$"
            )) {

                etGuardianMobile.setError(
                        "Enter a valid 10-digit mobile number"
                );

                etGuardianMobile.requestFocus();

                return;
            }

            // ====================================================
            // BLOOD GROUP VALIDATION
            // ====================================================

            if (bloodGroup.isEmpty()) {

                etGuardianBloodGroup.setError(
                        "Please select blood group"
                );

                etGuardianBloodGroup.requestFocus();

                return;
            }

            // ====================================================
            // EMAIL VALIDATION
            // ====================================================

            if (!email.isEmpty()
                    && !android.util.Patterns
                    .EMAIL_ADDRESS
                    .matcher(email)
                    .matches()) {

                etGuardianEmail.setError(
                        "Enter a valid email address"
                );

                etGuardianEmail.requestFocus();

                return;
            }

            // ====================================================
            // ADD OR UPDATE
            // ====================================================

            if (position >= 0) {

                updateContact(
                        position,
                        name,
                        relation,
                        mobile,
                        bloodGroup,
                        email,
                        address
                );

            } else {

                saveContact(
                        name,
                        relation,
                        mobile,
                        bloodGroup,
                        email,
                        address
                );
            }

            dialog.dismiss();
        });

        // ========================================================
        // SHOW DIALOG
        // ========================================================

        dialog.show();

        // ========================================================
        // DIALOG SIZE
        // ========================================================

        if (dialog.getWindow() != null) {

            dialog.getWindow()
                    .setBackgroundDrawableResource(
                            android.R.color.transparent
                    );

            dialog.getWindow().setLayout(
                    (int) (
                            getResources()
                                    .getDisplayMetrics()
                                    .widthPixels * 0.94
                    ),
                    (int) (
                            getResources()
                                    .getDisplayMetrics()
                                    .heightPixels * 0.88
                    )
            );
        }
    }

    // ============================================================
    // SAVE CONTACT
    // ============================================================

    private void saveContact(
            String name,
            String relation,
            String mobile,
            String bloodGroup,
            String email,
            String address
    ) {

        try {

            String existing =
                    preferences.getString(
                            CONTACTS_KEY,
                            "[]"
                    );

            JSONArray contacts =
                    new JSONArray(existing);

            JSONObject contact =
                    new JSONObject();

            contact.put("name", name);
            contact.put("relation", relation);
            contact.put("mobile", mobile);
            contact.put("bloodGroup", bloodGroup);
            contact.put("email", email);
            contact.put("address", address);

            contacts.put(contact);

            preferences.edit()
                    .putString(
                            CONTACTS_KEY,
                            contacts.toString()
                    )
                    .apply();

            loadContacts();

            Toast.makeText(
                    this,
                    "Trusted contact added successfully ❤️",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Could not save contact",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // ============================================================
    // UPDATE CONTACT
    // ============================================================

    private void updateContact(
            int position,
            String name,
            String relation,
            String mobile,
            String bloodGroup,
            String email,
            String address
    ) {

        try {

            String existing =
                    preferences.getString(
                            CONTACTS_KEY,
                            "[]"
                    );

            JSONArray contacts =
                    new JSONArray(existing);

            if (position < 0 ||
                    position >= contacts.length()) {

                Toast.makeText(
                        this,
                        "Contact not found",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            JSONObject updatedContact =
                    new JSONObject();

            updatedContact.put(
                    "name",
                    name
            );

            updatedContact.put(
                    "relation",
                    relation
            );

            updatedContact.put(
                    "mobile",
                    mobile
            );

            updatedContact.put(
                    "bloodGroup",
                    bloodGroup
            );

            updatedContact.put(
                    "email",
                    email
            );

            updatedContact.put(
                    "address",
                    address
            );

            contacts.put(
                    position,
                    updatedContact
            );

            preferences.edit()
                    .putString(
                            CONTACTS_KEY,
                            contacts.toString()
                    )
                    .apply();

            loadContacts();

            Toast.makeText(
                    this,
                    "Trusted contact updated ❤️",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Could not update contact",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // ============================================================
    // LOAD CONTACTS
    // ============================================================

    private void loadContacts() {

        if (contactsContainer == null) {
            return;
        }

        contactsContainer.removeAllViews();

        try {

            String existing =
                    preferences.getString(
                            CONTACTS_KEY,
                            "[]"
                    );

            JSONArray contacts =
                    new JSONArray(existing);

            if (contacts.length() == 0) {

                showEmptyMessage();

                return;
            }

            for (int i = 0;
                 i < contacts.length();
                 i++) {

                JSONObject contact =
                        contacts.getJSONObject(i);

                addContactCard(
                        contact,
                        i
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showEmptyMessage();
        }
    }

    // ============================================================
    // EMPTY STATE
    // ============================================================

    private void showEmptyMessage() {

        TextView empty =
                new TextView(this);

        empty.setText(
                "♡\n\n" +
                        "No trusted contacts yet\n\n" +
                        "Add people you trust so they can\n" +
                        "receive help alerts during an emergency."
        );

        empty.setTextColor(
                GREY_TEXT
        );

        empty.setTextSize(14);

        empty.setGravity(
                Gravity.CENTER
        );

        empty.setPadding(
                dpToPx(30),
                dpToPx(70),
                dpToPx(30),
                dpToPx(70)
        );

        contactsContainer.addView(
                empty
        );
    }

    // ============================================================
    // CONTACT CARD
    // ============================================================

    private void addContactCard(
            JSONObject contact,
            int position
    ) {

        CardView card =
                new CardView(this);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(
                0,
                0,
                0,
                dpToPx(16)
        );

        card.setLayoutParams(cardParams);

        card.setRadius(
                dpToPx(22)
        );

        card.setCardElevation(
                dpToPx(3)
        );

        card.setCardBackgroundColor(
                WHITE
        );

        card.setUseCompatPadding(
                true
        );

        // ========================================================
        // MAIN LAYOUT
        // ========================================================

        LinearLayout mainLayout =
                new LinearLayout(this);

        mainLayout.setOrientation(
                LinearLayout.VERTICAL
        );

        mainLayout.setPadding(
                dpToPx(18),
                dpToPx(16),
                dpToPx(18),
                dpToPx(16)
        );

        // ========================================================
        // HEADER
        // ========================================================

        LinearLayout headerRow =
                new LinearLayout(this);

        headerRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        headerRow.setGravity(
                Gravity.CENTER_VERTICAL
        );

        // ========================================================
        // AVATAR
        // ========================================================

        TextView avatar =
                new TextView(this);

        String contactName =
                contact.optString(
                        "name",
                        "?"
                );

        String initial = "?";

        if (!contactName.isEmpty()) {

            initial =
                    contactName
                            .substring(0, 1)
                            .toUpperCase();
        }

        avatar.setText(initial);

        avatar.setTextColor(
                WHITE
        );

        avatar.setTextSize(
                22
        );

        avatar.setTypeface(
                null,
                Typeface.BOLD
        );

        avatar.setGravity(
                Gravity.CENTER
        );

        avatar.setBackgroundColor(
                PINK
        );

        LinearLayout.LayoutParams avatarParams =
                new LinearLayout.LayoutParams(
                        dpToPx(54),
                        dpToPx(54)
                );

        avatarParams.rightMargin =
                dpToPx(14);

        avatar.setLayoutParams(
                avatarParams
        );

        headerRow.addView(
                avatar
        );

        // ========================================================
        // NAME + RELATION
        // ========================================================

        LinearLayout nameLayout =
                new LinearLayout(this);

        nameLayout.setOrientation(
                LinearLayout.VERTICAL
        );

        LinearLayout.LayoutParams nameParams =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                );

        nameLayout.setLayoutParams(
                nameParams
        );

        TextView name =
                new TextView(this);

        name.setText(
                contactName
        );

        name.setTextSize(
                18
        );

        name.setTextColor(
                DARK_TEXT
        );

        name.setTypeface(
                null,
                Typeface.BOLD
        );

        nameLayout.addView(
                name
        );

        TextView relation =
                new TextView(this);

        relation.setText(
                "♡  " +
                        contact.optString(
                                "relation",
                                "Trusted Contact"
                        )
        );

        relation.setTextColor(
                PINK
        );

        relation.setTextSize(
                12
        );

        relation.setPadding(
                0,
                dpToPx(4),
                0,
                0
        );

        nameLayout.addView(
                relation
        );

        headerRow.addView(
                nameLayout
        );

        mainLayout.addView(
                headerRow
        );

        // ========================================================
        // DIVIDER
        // ========================================================

        View divider =
                new View(this);

        LinearLayout.LayoutParams dividerParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dpToPx(1)
                );

        dividerParams.topMargin =
                dpToPx(14);

        dividerParams.bottomMargin =
                dpToPx(10);

        divider.setLayoutParams(
                dividerParams
        );

        divider.setBackgroundColor(
                SOFT_PINK
        );

        mainLayout.addView(
                divider
        );

        // ========================================================
        // MOBILE
        // ========================================================

        mainLayout.addView(
                createDetailText(
                        "📞  " +
                                contact.optString(
                                        "mobile",
                                        "-"
                                )
                )
        );

        // ========================================================
        // BLOOD GROUP
        // ========================================================

        mainLayout.addView(
                createDetailText(
                        "🩸  Blood Group: " +
                                contact.optString(
                                        "bloodGroup",
                                        "-"
                                )
                )
        );

        // ========================================================
        // EMAIL
        // ========================================================

        String email =
                contact.optString(
                        "email",
                        ""
                );

        if (!email.isEmpty()) {

            mainLayout.addView(
                    createDetailText(
                            "✉  " + email
                    )
            );
        }

        // ========================================================
        // ADDRESS
        // ========================================================

        String address =
                contact.optString(
                        "address",
                        ""
                );

        if (!address.isEmpty()) {

            mainLayout.addView(
                    createDetailText(
                            "📍  " + address
                    )
            );
        }

        // ========================================================
        // ACTION BUTTON ROW
        // ========================================================

        LinearLayout buttonRow =
                new LinearLayout(this);

        buttonRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        buttonRow.setGravity(
                Gravity.END
        );

        buttonRow.setPadding(
                0,
                dpToPx(12),
                0,
                0
        );

        // ========================================================
        // EDIT BUTTON
        // ========================================================

        MaterialButton editButton =
                new MaterialButton(this);

        editButton.setText(
                "✏ Edit"
        );

        editButton.setTextSize(
                12
        );

        editButton.setAllCaps(
                false
        );

        editButton.setTextColor(
                PURPLE
        );

        editButton.setCornerRadius(
                dpToPx(12)
        );

        editButton.setStrokeWidth(
                dpToPx(1)
        );

        editButton.setStrokeColor(
                ColorStateList.valueOf(
                        PURPLE
                )
        );

        editButton.setBackgroundTintList(
                ColorStateList.valueOf(
                        WHITE
                )
        );

        LinearLayout.LayoutParams editParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        dpToPx(42)
                );

        editParams.rightMargin =
                dpToPx(8);

        editButton.setLayoutParams(
                editParams
        );

        editButton.setOnClickListener(
                v -> showAddContactDialog(
                        position
                )
        );

        buttonRow.addView(
                editButton
        );

        // ========================================================
        // REMOVE BUTTON
        // ========================================================

        MaterialButton removeButton =
                new MaterialButton(this);

        removeButton.setText(
                "Remove"
        );

        removeButton.setTextSize(
                12
        );

        removeButton.setAllCaps(
                false
        );

        removeButton.setTextColor(
                PINK
        );

        removeButton.setCornerRadius(
                dpToPx(12)
        );

        removeButton.setStrokeWidth(
                dpToPx(1)
        );

        removeButton.setStrokeColor(
                ColorStateList.valueOf(
                        PINK
                )
        );

        removeButton.setBackgroundTintList(
                ColorStateList.valueOf(
                        WHITE
                )
        );

        LinearLayout.LayoutParams removeParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        dpToPx(42)
                );

        removeButton.setLayoutParams(
                removeParams
        );

        removeButton.setOnClickListener(
                v -> confirmDelete(
                        position
                )
        );

        buttonRow.addView(
                removeButton
        );

        mainLayout.addView(
                buttonRow
        );

        card.addView(
                mainLayout
        );

        contactsContainer.addView(
                card
        );
    }

    // ============================================================
    // DETAIL TEXT
    // ============================================================

    private TextView createDetailText(
            String text
    ) {

        TextView view =
                new TextView(this);

        view.setText(
                text
        );

        view.setTextSize(
                13
        );

        view.setTextColor(
                GREY_TEXT
        );

        view.setPadding(
                0,
                dpToPx(5),
                0,
                dpToPx(5)
        );

        return view;
    }

    // ============================================================
    // DELETE CONFIRMATION
    // ============================================================

    private void confirmDelete(
            int position
    ) {

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "Remove Trusted Contact?"
                        )
                        .setMessage(
                                "This person will no longer receive " +
                                        "your emergency alerts."
                        )
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .setPositiveButton(
                                "Remove",
                                (dialogInterface, which) ->
                                        deleteContact(
                                                position
                                        )
                        )
                        .create();

        dialog.setOnShowListener(
                d -> {

                    if (dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    ) != null) {

                        dialog.getButton(
                                AlertDialog.BUTTON_POSITIVE
                        ).setTextColor(
                                PINK
                        );
                    }

                    if (dialog.getButton(
                            AlertDialog.BUTTON_NEGATIVE
                    ) != null) {

                        dialog.getButton(
                                AlertDialog.BUTTON_NEGATIVE
                        ).setTextColor(
                                PURPLE
                        );
                    }
                }
        );

        dialog.show();
    }

    // ============================================================
    // DELETE CONTACT
    // ============================================================

    private void deleteContact(
            int position
    ) {

        try {

            String existing =
                    preferences.getString(
                            CONTACTS_KEY,
                            "[]"
                    );

            JSONArray contacts =
                    new JSONArray(
                            existing
                    );

            if (position >= 0 &&
                    position < contacts.length()) {

                contacts.remove(
                        position
                );
            }

            preferences.edit()
                    .putString(
                            CONTACTS_KEY,
                            contacts.toString()
                    )
                    .apply();

            loadContacts();

            Toast.makeText(
                    this,
                    "Trusted contact removed",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Could not remove contact",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // ============================================================
    // DP TO PX
    // ============================================================

    private int dpToPx(
            int dp
    ) {

        float density =
                getResources()
                        .getDisplayMetrics()
                        .density;

        return Math.round(
                dp * density
        );
    }
}