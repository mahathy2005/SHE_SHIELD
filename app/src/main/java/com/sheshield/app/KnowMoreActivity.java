package com.sheshield.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class KnowMoreActivity extends BaseActivity {

    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_more);

        // ============================================================
        // INITIALIZE VIEWS
        // ============================================================

        ImageButton btnBack = findViewById(R.id.btnBack);
        ViewPager2 viewPager = findViewById(R.id.viewPagerCards);
        TabLayout tabDots = findViewById(R.id.tabDots);

        // ============================================================
        // BACK BUTTON
        // ============================================================

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // ============================================================
        // KNOW MORE CARDS
        // ============================================================

        List<CardItem> cards = new ArrayList<>();

        // ============================================================
        // CARD 1 - LEGAL RIGHTS
        // ============================================================

        cards.add(new CardItem(
                "LEGAL RIGHTS & LAWS ⚖️",
                "Important Legal Rights Every Woman Should Know",

                "• Zero FIR:\n" +
                        "You can report a cognizable offence at a police station even when the incident occurred outside that station's jurisdiction. The complaint can later be transferred to the appropriate police station.\n\n" +

                        "• Arrest During Night:\n" +
                        "There are legal safeguards regarding the arrest of women after sunset and before sunrise, with limited exceptions requiring additional legal procedure.\n\n" +

                        "• Search by a Woman:\n" +
                        "A woman's personal search must generally be carried out by another woman while maintaining dignity and decency.\n\n" +

                        "• Identity Protection:\n" +
                        "The identity of victims in certain sexual-offence cases is protected from unlawful disclosure.\n\n" +

                        "• Complaint Without Going Alone:\n" +
                        "If you feel unsafe, take a trusted person with you and ask for acknowledgement or a diary/reference number for your complaint.",

                "#7C3AED",
                "#F3E8FF",
                "#5B21B6"
        ));

        // ============================================================
        // CARD 2 - WORKPLACE AND CAMPUS
        // ============================================================

        cards.add(new CardItem(
                "WORKPLACE & CAMPUS 🏢",
                "Know Your Protection Against Sexual Harassment",

                "• POSH Protection:\n" +
                        "The POSH framework provides a mechanism to address sexual harassment at workplaces. Educational institutions may also have complaint mechanisms and committees.\n\n" +

                        "• Internal Complaints Committee:\n" +
                        "Eligible workplaces are required to establish an Internal Committee for handling sexual-harassment complaints.\n\n" +

                        "• Keep Evidence:\n" +
                        "Save emails, chats, screenshots, call details and other relevant records. Write down dates, times, locations and names of witnesses.\n\n" +

                        "• Do Not Stay Silent:\n" +
                        "You can approach the designated complaints committee, appropriate authority or police depending on the nature of the incident.\n\n" +

                        "• Ask About Interim Support:\n" +
                        "Depending on applicable rules and circumstances, interim measures such as changes in work arrangements or other protective steps may be available.",

                "#0D9488",
                "#CCFBF1",
                "#115E59"
        ));

        // ============================================================
        // CARD 3 - CYBER SAFETY
        // ============================================================

        cards.add(new CardItem(
                "CYBER SAFETY & STALKING 🔒",
                "Protect Yourself From Online Harassment",

                "• Do Not Delete Evidence Immediately:\n" +
                        "Before blocking or reporting an account, capture screenshots showing usernames, profile links, messages, dates and timestamps.\n\n" +

                        "• Save Original Files:\n" +
                        "Keep original images, emails and messages when possible. Do not edit the only copy of important evidence.\n\n" +

                        "• Change Passwords:\n" +
                        "If you suspect account compromise, change passwords using a trusted device and enable two-factor authentication.\n\n" +

                        "• Review Privacy Settings:\n" +
                        "Check who can see your phone number, location, stories, posts and friend lists.\n\n" +

                        "• Avoid Real-Time Location Sharing:\n" +
                        "Posting your exact live location publicly can create unnecessary risk. Consider posting after leaving the place.",

                "#0284C7",
                "#E0F2FE",
                "#075985"
        ));

        // ============================================================
        // CARD 4 - EMERGENCY STRATEGIES
        // ============================================================

        cards.add(new CardItem(
                "EMERGENCY STRATEGIES 🚨",
                "Simple Actions That Can Help During Danger",

                "• If You Think You Are Being Followed:\n" +
                        "Do not lead the person directly to your home. Move toward a crowded, well-lit public place or seek help from authorities.\n\n" +

                        "• Tell Someone Specific:\n" +
                        "Instead of shouting only 'Help!', point to a person and give a direct instruction such as 'Please call the police.'\n\n" +

                        "• Use Your Phone Wisely:\n" +
                        "Set up emergency contacts and learn your phone's SOS features before an emergency happens.\n\n" +

                        "• Stay Near Exits:\n" +
                        "In an uncomfortable situation, notice exits and avoid allowing yourself to be trapped in an isolated location.\n\n" +

                        "• Trust Your Instincts:\n" +
                        "You do not need to remain polite when you feel unsafe. Creating distance and seeking help is more important.",

                "#E11D48",
                "#FFE4E6",
                "#9F1239"
        ));

        // ============================================================
        // CARD 5 - DIGITAL EVIDENCE
        // ============================================================

        cards.add(new CardItem(
                "DIGITAL EVIDENCE 📱",
                "How To Preserve Important Proof",

                "• Screenshot Carefully:\n" +
                        "Capture the complete conversation where possible, including the sender's username and timestamps.\n\n" +

                        "• Record Important Details:\n" +
                        "Write down dates, times, phone numbers, profile links and account names.\n\n" +

                        "• Keep Backup Copies:\n" +
                        "Store copies of important evidence securely so it is not lost if your phone is damaged or compromised.\n\n" +

                        "• Do Not Alter Original Evidence:\n" +
                        "Avoid editing, cropping or overwriting the only original version of a file.\n\n" +

                        "• Maintain a Timeline:\n" +
                        "Create a simple chronological list describing what happened, when it happened and who was involved.",

                "#9333EA",
                "#F3E8FF",
                "#6B21A8"
        ));

        // ============================================================
        // CARD 6 - STALKING WARNING SIGNS
        // ============================================================

        cards.add(new CardItem(
                "STALKING WARNING SIGNS 👁️",
                "Recognize Patterns Before They Escalate",

                "• Repeated Unwanted Contact:\n" +
                        "Constant calls, messages or attempts to contact you after you have clearly expressed disinterest can be a warning sign.\n\n" +

                        "• Appearing Everywhere:\n" +
                        "Repeatedly showing up near your home, college, workplace or regular locations may indicate a pattern worth documenting.\n\n" +

                        "• Fake Accounts:\n" +
                        "Do not assume blocking one account ends the problem. Save evidence of repeated accounts before reporting them.\n\n" +

                        "• Unwanted Gifts:\n" +
                        "Repeated gifts or deliveries after you have asked someone to stop can also be documented.\n\n" +

                        "• Tell Trusted People:\n" +
                        "Inform friends, family, security staff or colleagues about the situation so others can recognize the person or pattern.",

                "#EA580C",
                "#FFF7ED",
                "#9A3412"
        ));

        // ============================================================
        // CARD 7 - TRAVEL SAFETY
        // ============================================================

        cards.add(new CardItem(
                "TRAVEL SAFETY 🚕",
                "Stay Alert While Travelling Alone",

                "• Verify Your Ride:\n" +
                        "Before entering a cab, compare the vehicle number and driver details with your booking.\n\n" +

                        "• Share Trip Details:\n" +
                        "When appropriate, share your trip status with a trusted person.\n\n" +

                        "• Avoid Revealing Personal Information:\n" +
                        "You do not need to tell strangers where you live, whether you are alone or your daily routine.\n\n" +

                        "• Sit Where You Feel Safer:\n" +
                        "Choose a position that gives you reasonable awareness of your surroundings and access to an exit.\n\n" +

                        "• If Something Feels Wrong:\n" +
                        "Ask to stop at a populated safe location, contact someone you trust and seek assistance.",

                "#2563EB",
                "#EFF6FF",
                "#1E40AF"
        ));

        // ============================================================
        // CARD 8 - PHONE PRIVACY
        // ============================================================

        cards.add(new CardItem(
                "PHONE & PRIVACY 🔐",
                "Small Settings That Can Improve Your Safety",

                "• Use a Strong Screen Lock:\n" +
                        "Use a secure PIN, password or supported biometric lock.\n\n" +

                        "• Check App Permissions:\n" +
                        "Review which apps can access your location, microphone, camera, contacts and storage.\n\n" +

                        "• Enable Two-Factor Authentication:\n" +
                        "This can make unauthorized access to important accounts more difficult.\n\n" +

                        "• Review Shared Devices:\n" +
                        "Log out of accounts you no longer use on shared or public devices.\n\n" +

                        "• Check Unknown Apps:\n" +
                        "If you notice unusual battery drain, permissions or unfamiliar apps, review them carefully and consider seeking technical help.",

                "#0891B2",
                "#ECFEFF",
                "#155E75"
        ));

        // ============================================================
        // CARD 9 - SOCIAL MEDIA SAFETY
        // ============================================================

        cards.add(new CardItem(
                "SOCIAL MEDIA SMART 🛡️",
                "Avoid Accidentally Sharing Too Much",

                "• Check Who Can View Your Posts:\n" +
                        "Review privacy settings regularly because platforms can change their options.\n\n" +

                        "• Avoid Posting Live Locations:\n" +
                        "Consider sharing travel photos after leaving a location instead of while you are still there.\n\n" +

                        "• Be Careful With Background Details:\n" +
                        "Photos may reveal addresses, college IDs, vehicle numbers or other personal information.\n\n" +

                        "• Verify Unknown Requests:\n" +
                        "A familiar photo does not guarantee a real person. Fake accounts can copy names and pictures.\n\n" +

                        "• Block, Report and Document:\n" +
                        "For harassment, preserve evidence first when safe to do so, then use platform reporting and appropriate authorities.",

                "#DB2777",
                "#FDF2F8",
                "#9D174D"
        ));

        // ============================================================
        // CARD 10 - PERSONAL BOUNDARIES
        // ============================================================

        cards.add(new CardItem(
                "PERSONAL BOUNDARIES ✋",
                "You Are Allowed To Say No",

                "• No Explanation Required:\n" +
                        "You do not have to justify every decision when you feel uncomfortable or unsafe.\n\n" +

                        "• Leave When Needed:\n" +
                        "You are allowed to leave a conversation, event, vehicle or location if you feel unsafe.\n\n" +

                        "• Do Not Ignore Repeated Discomfort:\n" +
                        "A pattern of behaviour that repeatedly makes you feel unsafe deserves attention.\n\n" +

                        "• Practice Direct Responses:\n" +
                        "Simple phrases such as 'Stop', 'Do not contact me' and 'I am leaving now' can be useful when used safely.\n\n" +

                        "• Safety Over Politeness:\n" +
                        "You never have to sacrifice your safety just to avoid appearing rude.",

                "#DC2626",
                "#FEF2F2",
                "#991B1B"
        ));

        // ============================================================
        // CARD 11 - AFTER AN INCIDENT
        // ============================================================

        cards.add(new CardItem(
                "AFTER AN INCIDENT 📝",
                "What You Can Do After Reaching Safety",

                "• Get To Safety First:\n" +
                        "Move away from immediate danger and contact emergency services or trusted people if necessary.\n\n" +

                        "• Document What You Remember:\n" +
                        "Write down details while they are fresh, including time, location, people involved and witnesses.\n\n" +

                        "• Preserve Relevant Evidence:\n" +
                        "Keep messages, photographs, clothing or other relevant material where appropriate.\n\n" +

                        "• Seek Support:\n" +
                        "Reach out to trusted family members, friends, institutional support systems or appropriate authorities.\n\n" +

                        "• Keep Copies Of Complaints:\n" +
                        "Save acknowledgement numbers, complaint copies, emails and other records related to your report.",

                "#7C2D12",
                "#FFF7ED",
                "#7C2D12"
        ));

        // ============================================================
        // CARD 12 - SAFETY PREPARATION
        // ============================================================

        cards.add(new CardItem(
                "BE PREPARED 🎒",
                "Prepare Before You Need Help",

                "• Add Emergency Contacts:\n" +
                        "Keep important contacts easy to access and ensure they know how to respond if you send an emergency message.\n\n" +

                        "• Learn Your SOS Shortcut:\n" +
                        "Different phone brands have different emergency features, so test and understand yours in advance.\n\n" +

                        "• Keep Devices Charged:\n" +
                        "A charged phone or power bank can be extremely useful during travel or emergencies.\n\n" +

                        "• Inform Someone When Necessary:\n" +
                        "For unfamiliar travel or late-night journeys, consider sharing your expected route and arrival time with someone trusted.\n\n" +

                        "• Plan Safe Places:\n" +
                        "Know nearby public places, security points or other locations where you could seek assistance.",

                "#16A34A",
                "#F0FDF4",
                "#166534"
        ));

        // ============================================================
        // INITIALIZE ADAPTER
        // ============================================================

        cardAdapter = new CardAdapter(cards);

        // ============================================================
        // SET VIEWPAGER ADAPTER
        // ============================================================

        if (viewPager != null) {

            viewPager.setAdapter(cardAdapter);

            // ========================================================
            // SWIPE ANIMATION
            // ========================================================

            viewPager.setPageTransformer((page, position) -> {

                float scale =
                        0.85f +
                                (1 - Math.abs(position)) * 0.15f;

                float alpha =
                        0.5f +
                                (1 - Math.abs(position)) * 0.5f;

                page.setScaleX(scale);
                page.setScaleY(scale);
                page.setAlpha(alpha);
            });

            // ========================================================
            // DOT INDICATOR
            // ========================================================

            if (tabDots != null) {

                new TabLayoutMediator(
                        tabDots,
                        viewPager,
                        (tab, position) -> {
                            // Empty - dots only
                        }
                ).attach();
            }
        }

        // ============================================================
        // TRANSLATE ALL CARDS
        // ============================================================

        translateCards(cards);

        // ============================================================
        // TRANSLATE PAGE STATIC CONTENT
        // ============================================================

    }

    // ================================================================
    // TRANSLATE ALL CARDS
    // ================================================================

    private void translateCards(List<CardItem> cards) {

        String selectedLang =
                LocaleHelper.getSavedLanguage(this);

        // English - no translation required
        if (selectedLang == null ||
                selectedLang.equals("en")) {

            cardAdapter.notifyDataSetChanged();
            return;
        }

        final int totalCards = cards.size();
        final int[] completedCount = {0};

        for (CardItem card : cards) {

            // ========================================================
            // TRANSLATE BADGE
            // ========================================================

            LanguageManager.translateText(
                    this,
                    card.getBadge(),
                    selectedLang,
                    translatedBadge -> {

                        if (translatedBadge != null) {
                            card.setBadge(translatedBadge);
                        }

                        // ====================================================
                        // TRANSLATE TITLE
                        // ====================================================

                        LanguageManager.translateText(
                                this,
                                card.getTitle(),
                                selectedLang,
                                translatedTitle -> {

                                    if (translatedTitle != null) {
                                        card.setTitle(translatedTitle);
                                    }

                                    // ========================================
                                    // TRANSLATE DESCRIPTION
                                    // ========================================

                                    LanguageManager.translateText(
                                            this,
                                            card.getDescription(),
                                            selectedLang,
                                            translatedDescription -> {

                                                if (translatedDescription != null) {
                                                    card.setDescription(
                                                            translatedDescription
                                                    );
                                                }

                                                completedCount[0]++;

                                                // ====================================
                                                // ALL CARDS TRANSLATED
                                                // ====================================

                                                if (completedCount[0] ==
                                                        totalCards) {

                                                    runOnUiThread(() -> {

                                                        if (cardAdapter != null) {
                                                            cardAdapter
                                                                    .notifyDataSetChanged();
                                                        }
                                                    });
                                                }
                                            }
                                    );
                                }
                        );
                    }
            );
        }
    }

    // ================================================================
    // CARD MODEL
    // ================================================================

    static class CardItem {

        private String badge;
        private String title;
        private String description;

        private final String accentColor;
        private final String badgeBgColor;
        private final String badgeTextColor;

        CardItem(
                String badge,
                String title,
                String description,
                String accentColor,
                String badgeBgColor,
                String badgeTextColor
        ) {

            this.badge = badge;
            this.title = title;
            this.description = description;

            this.accentColor = accentColor;
            this.badgeBgColor = badgeBgColor;
            this.badgeTextColor = badgeTextColor;
        }

        // ============================================================
        // GETTERS
        // ============================================================

        public String getBadge() {
            return badge;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getAccentColor() {
            return accentColor;
        }

        public String getBadgeBgColor() {
            return badgeBgColor;
        }

        public String getBadgeTextColor() {
            return badgeTextColor;
        }

        // ============================================================
        // SETTERS
        // ============================================================

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    // ================================================================
    // VIEWPAGER ADAPTER
    // ================================================================

    static class CardAdapter
            extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

        private final List<CardItem> cardList;

        CardAdapter(List<CardItem> cardList) {
            this.cardList = cardList;
        }

        // ============================================================
        // CREATE VIEW HOLDER
        // ============================================================

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(
                @NonNull ViewGroup parent,
                int viewType
        ) {

            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(
                            R.layout.item_know_more_card,
                            parent,
                            false
                    );

            return new CardViewHolder(view);
        }

        // ============================================================
        // BIND DATA
        // ============================================================

        @Override
        public void onBindViewHolder(
                @NonNull CardViewHolder holder,
                int position
        ) {

            CardItem item = cardList.get(position);

            // ========================================================
            // SET TEXT
            // ========================================================

            holder.tvBadge.setText(
                    item.getBadge()
            );

            holder.tvTitle.setText(
                    item.getTitle()
            );

            holder.tvDescription.setText(
                    item.getDescription()
            );

            // ========================================================
            // APPLY COLORS
            // ========================================================

            holder.cardView.setStrokeColor(
                    Color.parseColor(
                            item.getAccentColor()
                    )
            );

            holder.sideAccentStrip.setBackgroundColor(
                    Color.parseColor(
                            item.getAccentColor()
                    )
            );

            holder.tvBadge.setBackgroundColor(
                    Color.parseColor(
                            item.getBadgeBgColor()
                    )
            );

            holder.tvBadge.setTextColor(
                    Color.parseColor(
                            item.getBadgeTextColor()
                    )
            );
        }

        // ============================================================
        // ITEM COUNT
        // ============================================================

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        // ============================================================
        // VIEW HOLDER
        // ============================================================

        static class CardViewHolder
                extends RecyclerView.ViewHolder {

            MaterialCardView cardView;

            View sideAccentStrip;

            TextView tvBadge;
            TextView tvTitle;
            TextView tvDescription;

            CardViewHolder(@NonNull View itemView) {

                super(itemView);

                cardView =
                        (MaterialCardView) itemView;

                sideAccentStrip =
                        itemView.findViewById(
                                R.id.sideAccentStrip
                        );

                tvBadge =
                        itemView.findViewById(
                                R.id.tvCardBadge
                        );

                tvTitle =
                        itemView.findViewById(
                                R.id.tvCardTitle
                        );

                tvDescription =
                        itemView.findViewById(
                                R.id.tvCardDescription
                        );
            }
        }
    }
}