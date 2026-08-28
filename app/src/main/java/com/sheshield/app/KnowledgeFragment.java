package com.sheshield.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class KnowledgeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_knowledge,
                container,
                false
        );

        LinearLayout containerList =
                view.findViewById(R.id.containerKnowledge);

        if (containerList != null) {

            // ============================================================
            // 1. PERSONAL SAFETY & SITUATIONAL AWARENESS
            // ============================================================

            addCard(
                    containerList,

                    "PERSONAL SAFETY & AWARENESS",

                    "Build Habits That Keep You Alert",

                    "Safety begins before an emergency happens. Situational awareness means noticing your surroundings, identifying unusual behaviour and having an exit plan.\n\n" +

                            "• Phone Awareness: Avoid continuously looking at your phone while walking. If you need navigation, stop somewhere safe and check the route rather than remaining distracted.\n\n" +

                            "• Earphone Awareness: Avoid completely blocking surrounding sounds in unfamiliar or isolated places, especially at night.\n\n" +

                            "• Trust Your Instincts: If a person, vehicle, building, street or situation makes you uncomfortable, you do not need to prove that your fear is justified. Leave, change direction or move toward other people.\n\n" +

                            "• Notice Exits: When entering a restaurant, theatre, college building, hotel or unfamiliar location, mentally note the nearest exits.\n\n" +

                            "• Share Your Plans: For late-night travel or unfamiliar places, tell someone you trust where you are going and when you expect to return.\n\n" +

                            "• Avoid Predictability: If you believe someone is following you, do not lead them directly to your home. Enter a busy public location or contact someone you trust.",

                    Color.parseColor("#E91E63"),
                    Color.parseColor("#FCE4EC")
            );


            // ============================================================
            // 2. KNOW YOUR BASIC LEGAL RIGHTS
            // ============================================================

            addCard(
                    containerList,

                    "KNOW YOUR BASIC LEGAL RIGHTS",

                    "You Do Not Have to Stay Silent About Abuse",

                    "Understanding the law helps you make informed decisions when something goes wrong. This information is educational and should not replace advice from a qualified lawyer.\n\n" +

                            "• Zero FIR: A cognizable offence can be reported at a police station even when the incident occurred outside that station's normal territorial jurisdiction. The matter can subsequently be transferred to the appropriate police station.\n\n" +

                            "• Information About an Arrest: If you are arrested, you should be informed of the grounds of arrest and your legal rights under applicable law.\n\n" +

                            "• Arrest of Women: Under BNSS Section 43(5), except in exceptional circumstances, a woman should not be arrested after sunset and before sunrise. In exceptional circumstances, the prescribed procedure involving a woman police officer and prior Magistrate permission applies.\n\n" +

                            "• Legal Assistance: If you cannot afford a lawyer, legal aid may be available through Legal Services Authorities. The National Legal Services Authority (NALSA) provides access to legal-aid mechanisms.\n\n" +

                            "• Evidence Matters: Preserve messages, emails, photographs, call records, medical documents and other relevant evidence instead of deleting them in panic.\n\n" +

                            "• Never Be Ashamed to Report: Harassment, stalking, threats, domestic violence, sexual offences and cyber abuse can have legal consequences. Seek appropriate help rather than handling serious situations alone.",

                    Color.parseColor("#1976D2"),
                    Color.parseColor("#E3F2FD")
            );


            // ============================================================
            // 3. EMERGENCY NUMBERS
            // ============================================================

            addCard(
                    containerList,

                    "EMERGENCY NUMBERS",

                    "Know Who to Call Before You Need Help",

                    "Save important emergency numbers in your phone and, where possible, memorise the most important ones.\n\n" +

                            "• 112 — Integrated emergency response number for police, fire and health-related emergencies.\n\n" +

                            "• 181 — Women Helpline.\n\n" +

                            "• 1091 — Women-related police helpline used in many official resources and jurisdictions.\n\n" +

                            "• 1930 — National Cyber Crime Helpline, particularly important for financial cyber fraud and cybercrime reporting.\n\n" +

                            "• 1098 — Child Helpline.\n\n" +

                            "• 15100 — NALSA legal-aid helpline.\n\n" +

                            "• 14416 — Tele-MANAS mental-health support service.\n\n" +

                            "In a life-threatening emergency, prioritise immediate emergency assistance. Government helpline availability and routing can vary by service and location, so verify current numbers when possible.",

                    Color.parseColor("#C62828"),
                    Color.parseColor("#FFEBEE")
            );


            // ============================================================
            // 4. POLICE & FIR
            // ============================================================

            addCard(
                    containerList,

                    "POLICE, COMPLAINTS & FIR",

                    "What to Do When You Need to Report a Crime",

                    "Many people hesitate to approach the police because they do not know what will happen. Understanding the basic process can make the situation less intimidating.\n\n" +

                            "• Clearly explain what happened, when it happened, where it happened and who was involved.\n\n" +

                            "• Preserve evidence such as screenshots, photographs, recordings, medical documents, transaction records and witness details.\n\n" +

                            "• Ask for the relevant complaint/FIR acknowledgement or reference details where applicable.\n\n" +

                            "• Do not exaggerate or invent facts. Give an accurate account, even if some details are uncertain.\n\n" +

                            "• If you are physically injured, seek medical attention and retain medical records.\n\n" +

                            "• If you feel unsafe while making a complaint, consider taking a trusted person with you where appropriate.\n\n" +

                            "• For serious matters, obtain advice from a qualified lawyer or legal-aid service instead of relying only on social-media advice.",

                    Color.parseColor("#5E35B1"),
                    Color.parseColor("#EDE7F6")
            );


            // ============================================================
            // 5. SEXUAL HARASSMENT
            // ============================================================

            addCard(
                    containerList,

                    "HARASSMENT & SEXUAL HARASSMENT",

                    "Recognise Behaviour That Crosses the Line",

                    "Harassment can happen in public places, educational institutions, workplaces, online platforms and personal relationships.\n\n" +

                            "Examples may include unwanted sexual comments, repeated unwanted contact, stalking, sexually explicit messages, inappropriate touching, threats, coercion or persistent unwanted advances.\n\n" +

                            "• You are allowed to say NO.\n\n" +

                            "• Consent should be voluntary and should not be obtained through threats, pressure, manipulation or fear.\n\n" +

                            "• Being friendly, accepting a date, wearing particular clothes or previously agreeing to something does not automatically mean consent to everything else.\n\n" +

                            "• Save relevant messages and evidence if harassment is occurring digitally.\n\n" +

                            "• At work, the Sexual Harassment of Women at Workplace law provides a formal framework for prevention and complaints.\n\n" +

                            "• At colleges and universities, identify the institution's Internal Committee/appropriate grievance mechanism and learn how complaints can be submitted.",

                    Color.parseColor("#AD1457"),
                    Color.parseColor("#FCE4EC")
            );


            // ============================================================
            // 6. DOMESTIC VIOLENCE & RELATIONSHIPS
            // ============================================================

            addCard(
                    containerList,

                    "RELATIONSHIPS & DOMESTIC VIOLENCE",

                    "Safety Includes Emotional, Financial & Digital Abuse",

                    "Abuse is not limited to physical violence. Controlling behaviour can also affect emotional, financial and digital freedom.\n\n" +

                            "Warning signs can include threats, intimidation, isolation from friends or family, controlling finances, monitoring phones, forced access to passwords, stalking, humiliation, physical violence or threats against loved ones.\n\n" +

                            "• Keep important documents accessible.\n\n" +

                            "• Maintain contact with at least one trusted person who knows what is happening.\n\n" +

                            "• If you are planning to leave an abusive situation, consider safety planning rather than announcing your plan in a potentially dangerous moment.\n\n" +

                            "• Preserve important evidence where it is safe to do so.\n\n" +

                            "• Women experiencing domestic violence can seek assistance through appropriate police, legal-aid, Women Helpline and One Stop Centre mechanisms.",

                    Color.parseColor("#6A1B9A"),
                    Color.parseColor("#F3E5F5")
            );


            // ============================================================
            // 7. CYBER SAFETY
            // ============================================================

            addCard(
                    containerList,

                    "DIGITAL & CYBER SAFETY",

                    "Protect Your Identity, Accounts & Location",

                    "Your digital identity can reveal far more information than you realise. Good cyber hygiene reduces the chance of stalking, account takeover and identity abuse.\n\n" +

                            "• Use strong, unique passwords for important accounts.\n\n" +

                            "• Enable two-factor authentication whenever available.\n\n" +

                            "• Never share OTPs, banking PINs, UPI PINs, passwords or recovery codes with anyone.\n\n" +

                            "• Review app permissions regularly, especially location, microphone, camera, contacts and accessibility permissions.\n\n" +

                            "• Avoid posting your exact location in real time. Consider posting after leaving a place.\n\n" +

                            "• Check privacy settings on social-media accounts.\n\n" +

                            "• Do not accept unknown people into private social accounts simply because they appear to know you.\n\n" +

                            "• If an account is compromised, change the password from a trusted device, revoke unknown sessions and secure the recovery email/phone.",

                    Color.parseColor("#F57C00"),
                    Color.parseColor("#FFF3E0")
            );


            // ============================================================
            // 8. CYBERCRIME & FINANCIAL FRAUD
            // ============================================================

            addCard(
                    containerList,

                    "ONLINE FRAUD & CYBERCRIME",

                    "Act Quickly When Money or Accounts Are at Risk",

                    "Online fraud can happen through fake jobs, investment scams, shopping websites, QR codes, UPI requests, fake customer support, phishing links and impersonation.\n\n" +

                            "• Never share an OTP or UPI PIN to receive money. UPI PINs are generally used to authorise payments, not to receive them.\n\n" +

                            "• Do not install remote-control applications because an unknown caller tells you to.\n\n" +

                            "• Verify payment requests independently.\n\n" +

                            "• If you have suffered financial cyber fraud, contact your bank/payment provider immediately and report the incident through the appropriate cybercrime channel.\n\n" +

                            "• 1930 is the National Cyber Crime Helpline.\n\n" +

                            "• The National Cyber Crime Reporting Portal is the official government portal for reporting cybercrime, including crimes affecting women and children.\n\n" +

                            "Speed matters in financial fraud because rapid reporting can help authorities and financial institutions attempt to stop or trace fraudulent transfers.",

                    Color.parseColor("#EF6C00"),
                    Color.parseColor("#FFF8E1")
            );


            // ============================================================
            // 9. TRAVEL & CAB SAFETY
            // ============================================================

            addCard(
                    containerList,

                    "TRAVEL & TRANSPORT SAFETY",

                    "Make Every Journey Easier to Verify",

                    "Whether using a cab, auto, bus, train or flight, small verification habits can reduce risk.\n\n" +

                            "• Verify the vehicle number and driver details shown in the official booking application before entering.\n\n" +

                            "• Do not reveal unnecessary personal information such as your exact home address to strangers.\n\n" +

                            "• Share trip details or live location with a trusted person when travelling alone, particularly late at night.\n\n" +

                            "• If a driver takes an unexpected route, ask about it and check your map. If uncomfortable, consider ending the trip at a safe, populated location.\n\n" +

                            "• On public transport, prefer populated and well-lit areas when possible.\n\n" +

                            "• At stations and airports, avoid accepting unsolicited transport or accommodation offers from strangers.\n\n" +

                            "• Keep your phone charged and maintain access to emergency contacts.",

                    Color.parseColor("#8E24AA"),
                    Color.parseColor("#F3E5F5")
            );


            // ============================================================
            // 10. COLLEGE & WORKPLACE
            // ============================================================

            addCard(
                    containerList,

                    "COLLEGE & WORKPLACE SAFETY",

                    "Know the Support Systems Around You",

                    "Women should know where to report inappropriate behaviour in educational institutions and workplaces.\n\n" +

                            "• Identify the appropriate Internal Committee/complaints mechanism at your institution or workplace.\n\n" +

                            "• Save official HR, student-support, security and grievance-contact information.\n\n" +

                            "• Keep important academic or employment documents backed up securely.\n\n" +

                            "• Avoid sharing passwords or personal authentication codes with colleagues or classmates.\n\n" +

                            "• If someone pressures you to meet privately in an uncomfortable situation, consider moving to a public location and informing someone you trust.\n\n" +

                            "• Document repeated inappropriate behaviour factually: date, time, location, people present and what happened.\n\n" +

                            "• Retaliation or pressure after making a legitimate complaint should itself be documented and reported through appropriate channels.",

                    Color.parseColor("#3949AB"),
                    Color.parseColor("#E8EAF6")
            );


            // ============================================================
            // 11. FINANCIAL INDEPENDENCE
            // ============================================================

            addCard(
                    containerList,

                    "FINANCIAL SAFETY",

                    "Protect Your Money & Financial Identity",

                    "Financial independence is also a safety skill.\n\n" +

                            "• Keep your bank account, UPI and payment credentials private.\n\n" +

                            "• Never share your ATM PIN, UPI PIN, OTP, CVV or net-banking password.\n\n" +

                            "• Turn on transaction notifications.\n\n" +

                            "• Check bank statements regularly and report suspicious transactions immediately.\n\n" +

                            "• Be careful with 'work from home', investment, loan and prize scams.\n\n" +

                            "• Do not allow someone else to operate your banking application simply because they claim to be helping you.\n\n" +

                            "• Keep copies of important financial documents in a secure location.\n\n" +

                            "• Learn the difference between receiving money and authorising a payment. A request to enter your UPI PIN should be treated as a payment authorisation request.",

                    Color.parseColor("#2E7D32"),
                    Color.parseColor("#E8F5E9")
            );


            // ============================================================
            // 12. EMERGENCY PREPAREDNESS
            // ============================================================

            addCard(
                    containerList,

                    "EMERGENCY PREPAREDNESS",

                    "Prepare Before Something Goes Wrong",

                    "A few minutes of preparation can make a major difference during an emergency.\n\n" +

                            "Keep these ready:\n\n" +

                            "• Emergency contacts.\n" +
                            "• Medical information that you choose to share.\n" +
                            "• Important identification/document copies stored securely.\n" +
                            "• Charged phone and power bank when travelling.\n" +
                            "• Basic first-aid supplies.\n" +
                            "• Essential medication if prescribed.\n" +
                            "• Emergency cash/payment option.\n\n" +

                            "Create an emergency plan with at least one trusted person. Decide where you can go if you need immediate help: a police station, hospital, security desk, trusted neighbour, family member or other safe public location.\n\n" +

                            "Do not wait until an emergency to discover how your phone's emergency features, location sharing and SOS functions work.",

                    Color.parseColor("#00838F"),
                    Color.parseColor("#E0F7FA")
            );


            // ============================================================
            // 13. SELF DEFENSE & DE-ESCALATION
            // ============================================================

            addCard(
                    containerList,

                    "SELF-PROTECTION & DE-ESCALATION",

                    "The Goal Is Escape — Not Winning a Fight",

                    "Physical confrontation should be treated as a last resort. The safest strategy is usually to create distance, attract attention and reach a safer location.\n\n" +

                            "• Use a clear voice: 'STOP', 'BACK AWAY' or 'HELP'.\n\n" +

                            "• Move toward people, security personnel, shops, reception desks or other populated areas.\n\n" +

                            "• Keep physical barriers between you and a threatening person when possible.\n\n" +

                            "• Do not allow an aggressive stranger to isolate you in a secluded location.\n\n" +

                            "• If physical violence becomes unavoidable, prioritise creating an opportunity to escape rather than continuing the confrontation.\n\n" +

                            "• Consider learning practical self-defence from a qualified instructor rather than relying on internet tricks.\n\n" +

                            "• Remember: escaping, calling for help and surviving are more important than protecting your belongings.",

                    Color.parseColor("#388E3C"),
                    Color.parseColor("#E8F5E9")
            );


            // ============================================================
            // 14. EVIDENCE & DOCUMENTATION
            // ============================================================

            addCard(
                    containerList,

                    "EVIDENCE & DOCUMENTATION",

                    "Record Facts Carefully When Something Happens",

                    "Good documentation can make it easier to explain an incident accurately.\n\n" +

                            "Record, where safely possible:\n\n" +

                            "• Date and time.\n" +
                            "• Exact or approximate location.\n" +
                            "• Names/usernames involved.\n" +
                            "• Phone numbers or profile information.\n" +
                            "• Screenshots and relevant URLs.\n" +
                            "• Photos or videos when legally and safely obtained.\n" +
                            "• Witness names/contact details.\n" +
                            "• Medical records.\n" +
                            "• Transaction information in financial fraud cases.\n\n" +

                            "Do not edit screenshots in a way that removes important context. Keep original files where possible and maintain a secure backup.\n\n" +

                            "Do not put yourself in danger merely to collect evidence. Your immediate safety comes first.",

                    Color.parseColor("#455A64"),
                    Color.parseColor("#ECEFF1")
            );


            // ============================================================
            // 15. HEALTH, CONSENT & PERSONAL BOUNDARIES
            // ============================================================

            addCard(
                    containerList,

                    "HEALTH, CONSENT & BOUNDARIES",

                    "Your Body, Privacy & Boundaries Matter",

                    "Basic knowledge about consent and personal boundaries is an important part of personal safety.\n\n" +

                            "• You can say NO to unwanted physical contact.\n\n" +

                            "• Consent should be voluntary and can be withdrawn.\n\n" +

                            "• Being in a relationship does not remove a person's right to boundaries.\n\n" +

                            "• Never feel pressured to share intimate photographs or videos. Once shared digitally, control over the copy can be lost.\n\n" +

                            "• If someone threatens to publish intimate material, do not pay or comply simply because they threaten you. Preserve evidence and seek appropriate legal/cyber assistance.\n\n" +

                            "• Seek medical attention after an assault or injury when needed. Medical professionals can advise on treatment and documentation.\n\n" +

                            "• If you believe you are in immediate danger, move to a safer location and seek emergency assistance.",

                    Color.parseColor("#D81B60"),
                    Color.parseColor("#FCE4EC")
            );


            // ============================================================
            // 16. SOCIAL ENGINEERING & SCAMS
            // ============================================================

            addCard(
                    containerList,

                    "SCAM AWARENESS",

                    "Do Not Let Urgency Override Your Judgment",

                    "Many scams work by creating panic, authority or urgency.\n\n" +

                            "Common warning signs include:\n\n" +

                            "• 'Your account will be closed today.'\n" +
                            "• 'You have won a prize.'\n" +
                            "• 'Pay immediately to avoid arrest.'\n" +
                            "• 'Tell me the OTP so I can verify you.'\n" +
                            "• 'Install this application for verification.'\n" +
                            "• 'Send money first and receive the refund later.'\n\n" +

                            "Pause before acting. Contact the organisation through an independently verified official number or website rather than using a number supplied by the caller.\n\n" +

                            "Government agencies, banks and legitimate organisations should not require you to reveal confidential authentication credentials such as OTPs or UPI PINs.",

                    Color.parseColor("#F4511E"),
                    Color.parseColor("#FBE9E7")
            );


            // ============================================================
            // 17. SOCIAL MEDIA & IMAGE SAFETY
            // ============================================================

            addCard(
                    containerList,

                    "SOCIAL MEDIA SAFETY",

                    "Think Before You Share",

                    "Photos, stories and location information can reveal your routine, relationships, college, workplace and travel patterns.\n\n" +

                            "• Avoid publicly displaying your home address, phone number or personal identification documents.\n\n" +

                            "• Be careful when posting boarding passes, tickets, ID cards or certificates because they may contain sensitive information.\n\n" +

                            "• Review followers and remove suspicious accounts.\n\n" +

                            "• Disable automatic location tagging when it is not necessary.\n\n" +

                            "• Be cautious with strangers who quickly become emotionally close and then request money, private photographs or personal information.\n\n" +

                            "• If someone impersonates you online, preserve evidence and report the account through the platform and appropriate cybercrime channels.\n\n" +

                            "• Remember that deleting a post does not guarantee that copies no longer exist.",

                    Color.parseColor("#7B1FA2"),
                    Color.parseColor("#F3E5F5")
            );


            // ============================================================
            // 18. HOME & ACCOMMODATION SAFETY
            // ============================================================

            addCard(
                    containerList,

                    "HOME, HOSTEL & PG SAFETY",

                    "Your Living Space Should Have a Safety Plan",

                    "Whether living at home, in a hostel, PG or rented accommodation, know the basic safety arrangements around you.\n\n" +

                            "• Know the building's emergency exits.\n\n" +

                            "• Keep doors and windows secured appropriately.\n\n" +

                            "• Do not casually share keys, access codes or security information.\n\n" +

                            "• Verify maintenance workers, delivery personnel and unexpected visitors before allowing access when practical.\n\n" +

                            "• Know the property manager, security desk and emergency contact information.\n\n" +

                            "• If someone repeatedly appears near your residence, document the behaviour and inform a trusted person/security authority instead of confronting them alone.\n\n" +

                            "• Never reveal to strangers that you are home alone.",

                    Color.parseColor("#546E7A"),
                    Color.parseColor("#ECEFF1")
            );


            // ============================================================
            // 19. WHAT TO DO IF FOLLOWED
            // ============================================================

            addCard(
                    containerList,

                    "IF YOU THINK YOU ARE BEING FOLLOWED",

                    "Do Not Lead the Person to Your Home",

                    "If you suspect that someone is following you:\n\n" +

                            "• Stay calm and avoid confronting the person unnecessarily.\n\n" +

                            "• Change direction or cross the road to see whether the behaviour continues.\n\n" +

                            "• Move toward a busy, well-lit location.\n\n" +

                            "• Enter a shop, hotel, hospital, police facility, security desk or other public place.\n\n" +

                            "• Contact a trusted person and share your location.\n\n" +

                            "• If you believe there is immediate danger, contact emergency services.\n\n" +

                            "• Do not go directly to your home if doing so would reveal your address to the person.\n\n" +

                            "• If safe, note useful identifying information such as vehicle number, clothing or direction of travel — but never put yourself at risk to collect it.",

                    Color.parseColor("#C62828"),
                    Color.parseColor("#FFEBEE")
            );


            // ============================================================
            // 20. DIGITAL & PHYSICAL PRIVACY
            // ============================================================

            addCard(
                    containerList,

                    "PRIVACY & IDENTITY PROTECTION",

                    "Protect Information That Can Be Used Against You",

                    "Personal information has value. Treat it like something that needs protection.\n\n" +

                            "Protect your:\n\n" +

                            "• Aadhaar and other identity-document details.\n" +
                            "• Bank information.\n" +
                            "• Phone number.\n" +
                            "• Email address.\n" +
                            "• Passwords and recovery codes.\n" +
                            "• Location history.\n" +
                            "• Private photographs.\n" +
                            "• College/work information.\n\n" +

                            "Before sending an identity document, ask why it is required and whether the recipient is legitimate. Avoid unnecessarily sharing full documents with unknown individuals.\n\n" +

                            "When disposing of sensitive paperwork, destroy it in a way that prevents easy reconstruction.",

                    Color.parseColor("#00695C"),
                    Color.parseColor("#E0F2F1")
            );


            // ============================================================
            // 21. EMERGENCY MINDSET
            // ============================================================

            addCard(
                    containerList,

                    "EMERGENCY MINDSET",

                    "Remember: ESCAPE • ALERT • REPORT • RECOVER",

                    "When something frightening happens, it is normal to freeze or feel confused. Having a simple mental sequence can help.\n\n" +

                            "1. ESCAPE — Move away from immediate danger if possible.\n\n" +

                            "2. ALERT — Call for help, attract attention or contact emergency services.\n\n" +

                            "3. REPORT — Once safe, report the incident through the appropriate channel and preserve evidence.\n\n" +

                            "4. RECOVER — Seek medical care, emotional support, legal assistance or other professional help when needed.\n\n" +

                            "You do not have to handle a serious incident alone. Asking for help is not weakness. Your safety is more important than embarrassment, social pressure or fear of what other people may think.",

                    Color.parseColor("#283593"),
                    Color.parseColor("#E8EAF6")
            );
        }

        return view;
    }


    // ============================================================
    // CARD CREATION
    // ============================================================

    private void addCard(
            LinearLayout container,
            String category,
            String title,
            String description,
            int strokeColor,
            int bgColor) {

        MaterialCardView card =
                new MaterialCardView(requireContext());

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 0, 0, 36);

        card.setLayoutParams(params);

        card.setCardElevation(4);
        card.setRadius(24);

        // Border
        card.setStrokeColor(strokeColor);
        card.setStrokeWidth(4);

        // Background
        card.setCardBackgroundColor(bgColor);


        // ============================================================
        // CONTENT LAYOUT
        // ============================================================

        LinearLayout layout =
                new LinearLayout(requireContext());

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                44,
                44,
                44,
                44
        );


        // ============================================================
        // CATEGORY
        // ============================================================

        TextView tvCategory =
                new TextView(requireContext());

        tvCategory.setText(category);

        tvCategory.setTextColor(
                strokeColor
        );

        tvCategory.setTextSize(12);

        tvCategory.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );


        // ============================================================
        // TITLE
        // ============================================================

        TextView tvTitle =
                new TextView(requireContext());

        tvTitle.setText(title);

        tvTitle.setTextColor(
                Color.parseColor("#1A1A2E")
        );

        tvTitle.setTextSize(18);

        tvTitle.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        tvTitle.setPadding(
                0,
                8,
                0,
                16
        );


        // ============================================================
        // DESCRIPTION
        // ============================================================

        TextView tvDesc =
                new TextView(requireContext());

        tvDesc.setText(description);

        tvDesc.setTextColor(
                Color.parseColor("#2C2C2C")
        );

        tvDesc.setTextSize(14);

        tvDesc.setLineSpacing(
                6f,
                1f
        );


        // ============================================================
        // ADD CONTENT
        // ============================================================

        layout.addView(tvCategory);

        layout.addView(tvTitle);

        layout.addView(tvDesc);

        card.addView(layout);

        container.addView(card);
    }
}