package com.sheshield.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ================================================================
 * SAFE ZONES ACTIVITY
 * ================================================================
 *
 * Uses:
 *
 * 1. Android GPS / Fused Location Provider
 * 2. OpenStreetMap
 * 3. Overpass API
 *
 * Does NOT use:
 *
 * - Google Places API
 * - Google Maps Places SDK
 * - Google Cloud billing
 * - Google Maps API key
 *
 * The returned places are actual places contained in OpenStreetMap.
 *
 * IMPORTANT:
 * The app never creates fake locations to fill the list.
 * If OpenStreetMap has 37 mapped places nearby, up to those
 * real places can be displayed.
 */
public class SafeZonesActivity extends BaseActivity {

    // =============================================================
    // CONSTANTS
    // =============================================================

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    /*
     * Search radius in metres.
     *
     * 10 km gives considerably more results than the old 5 km
     * search while still keeping the search geographically useful.
     */
    private static final int SEARCH_RADIUS_METERS = 5000;
    /*
     * Overpass endpoints.
     *
     * If the first server is unavailable, the second one is tried.
     */
    private static final String[] OVERPASS_ENDPOINTS = {
            "https://overpass-api.de/api/interpreter",
            "https://overpass.kumi.systems/api/interpreter"
    };

    // =============================================================
    // LOCATION
    // =============================================================

    private FusedLocationProviderClient fusedLocationClient;

    private Location currentLocation;

    // =============================================================
    // UI
    // =============================================================

    private TextView tvCurrentLocation;
    private TextView tvResultCount;

    private ProgressBar progressBar;

    private RecyclerView rvSafeZones;

    private MaterialButton btnRefreshLocation;

    private Chip chipAll;
    private Chip chipPolice;
    private Chip chipCrowded;
    private Chip chipHospitals;

    // =============================================================
    // ADAPTER
    // =============================================================

    private SafeZonesAdapter adapter;

    // =============================================================
    // DATA
    // =============================================================

    /*
     * All real places returned from OpenStreetMap.
     */
    private final List<SafePlace> allPlaces =
            new ArrayList<>();

    /*
     * Used to prevent duplicate OSM objects appearing twice.
     */
    private final Set<String> existingPlaceKeys =
            new HashSet<>();

    /*
     * Background executor.
     */
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    /*
     * Current filter.
     */
    private String currentFilter = "ALL";


    // =============================================================
    // ACTIVITY CREATED
    // =============================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_safe_zones
        );

        // ---------------------------------------------------------
        // FIND VIEWS
        // ---------------------------------------------------------

        tvCurrentLocation =
                findViewById(
                        R.id.tvCurrentLocation
                );

        tvResultCount =
                findViewById(
                        R.id.tvResultCount
                );

        progressBar =
                findViewById(
                        R.id.progressBar
                );

        rvSafeZones =
                findViewById(
                        R.id.rvSafeZones
                );

        btnRefreshLocation =
                findViewById(
                        R.id.btnRefreshLocation
                );

        chipAll =
                findViewById(
                        R.id.chipAll
                );

        chipPolice =
                findViewById(
                        R.id.chipPolice
                );

        chipCrowded =
                findViewById(
                        R.id.chipCrowded
                );

        chipHospitals =
                findViewById(
                        R.id.chipHospitals
                );


        // ---------------------------------------------------------
        // RECYCLER VIEW
        // ---------------------------------------------------------

        rvSafeZones.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvSafeZones.setHasFixedSize(false);


        adapter =
                new SafeZonesAdapter(
                        new ArrayList<>(),

                        new SafeZonesAdapter.OnPlaceClickListener() {

                            @Override
                            public void onNavigate(
                                    SafePlace place
                            ) {

                                openGoogleMaps(place);
                            }

                            @Override
                            public void onCall(
                                    SafePlace place
                            ) {

                                callPlace(place);
                            }
                        }
                );


        rvSafeZones.setAdapter(adapter);


        // ---------------------------------------------------------
        // LOCATION CLIENT
        // ---------------------------------------------------------

        fusedLocationClient =
                LocationServices
                        .getFusedLocationProviderClient(this);


        // ---------------------------------------------------------
        // REFRESH BUTTON
        // ---------------------------------------------------------

        if (btnRefreshLocation != null) {

            btnRefreshLocation.setOnClickListener(
                    v -> checkLocationPermission()
            );
        }


        // ---------------------------------------------------------
        // FILTERS
        // ---------------------------------------------------------

        setupFilters();


        // ---------------------------------------------------------
        // GET LOCATION
        // ---------------------------------------------------------

        checkLocationPermission();
    }


    // =============================================================
    // FILTER SETUP
    // =============================================================

    private void setupFilters() {

        if (chipAll != null) {

            chipAll.setOnClickListener(v -> {

                currentFilter = "ALL";

                loadFilteredPlaces();
            });
        }


        if (chipPolice != null) {

            chipPolice.setOnClickListener(v -> {

                currentFilter = "POLICE";

                loadFilteredPlaces();
            });
        }


        if (chipHospitals != null) {

            chipHospitals.setOnClickListener(v -> {

                currentFilter = "HOSPITAL";

                loadFilteredPlaces();
            });
        }


        if (chipCrowded != null) {

            chipCrowded.setOnClickListener(v -> {

                currentFilter = "CROWDED";

                loadFilteredPlaces();
            });
        }
    }


    // =============================================================
    // LOCATION PERMISSION
    // =============================================================

    private void checkLocationPermission() {

        if (
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                        != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                    this,

                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },

                    LOCATION_PERMISSION_REQUEST_CODE
            );

        } else {

            getUserLocation();
        }
    }


    // =============================================================
    // GET CURRENT LOCATION
    // =============================================================

    private void getUserLocation() {

        showLoading();


        if (tvCurrentLocation != null) {

            tvCurrentLocation.setText(
                    "Finding your current location..."
            );
        }


        if (
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                        != PackageManager.PERMISSION_GRANTED
        ) {

            hideLoading();

            return;
        }


        fusedLocationClient
                .getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        null
                )
                .addOnSuccessListener(
                        this,

                        location -> {

                            if (location != null) {

                                onLocationFound(location);

                            } else {

                                getLastKnownLocation();
                            }
                        }
                )
                .addOnFailureListener(
                        this,

                        e -> getLastKnownLocation()
                );
    }


    // =============================================================
    // LAST KNOWN LOCATION FALLBACK
    // =============================================================

    private void getLastKnownLocation() {

        if (
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                        != PackageManager.PERMISSION_GRANTED
        ) {

            hideLoading();

            return;
        }


        fusedLocationClient
                .getLastLocation()
                .addOnSuccessListener(
                        this,

                        location -> {

                            if (location != null) {

                                onLocationFound(location);

                            } else {

                                hideLoading();

                                if (tvCurrentLocation != null) {

                                    tvCurrentLocation.setText(
                                            "Unable to determine location"
                                    );
                                }


                                Toast.makeText(
                                        this,
                                        "Please turn on GPS and try again.",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =============================================================
    // LOCATION FOUND
    // =============================================================

    private void onLocationFound(
            Location location
    ) {

        currentLocation = location;


        if (tvCurrentLocation != null) {

            tvCurrentLocation.setText(
                    String.format(
                            Locale.getDefault(),

                            "Current location • %.5f, %.5f",

                            location.getLatitude(),
                            location.getLongitude()
                    )
            );
        }


        searchAllSafePlaces();
    }


    // =============================================================
    // SEARCH ALL SAFE PLACES
    // =============================================================

    private void searchAllSafePlaces() {

        if (currentLocation == null) {

            return;
        }


        showLoading();


        if (tvResultCount != null) {

            tvResultCount.setText(
                    "Searching nearby real places..."
            );
        }


        /*
         * Clear old results before performing a fresh search.
         */

        allPlaces.clear();

        existingPlaceKeys.clear();


        final double latitude =
                currentLocation.getLatitude();

        final double longitude =
                currentLocation.getLongitude();


        executor.execute(() -> {

            List<SafePlace> results =
                    queryOverpass(
                            latitude,
                            longitude
                    );


            runOnUiThread(() -> {

                allPlaces.clear();

                existingPlaceKeys.clear();


                if (results != null) {

                    allPlaces.addAll(results);
                }


                sortByDistance();


                currentFilter = "ALL";


                /*
                 * Show ALL real results.
                 *
                 * There is no artificial limit of 3.
                 */

                loadFilteredPlaces();


                hideLoading();


                if (allPlaces.isEmpty()) {

                    if (tvResultCount != null) {

                        tvResultCount.setText(
                                "No mapped safe places found nearby"
                        );
                    }


                    Toast.makeText(
                            this,
                            "No nearby mapped places were found. Try refreshing or moving to a different area.",
                            Toast.LENGTH_LONG
                    ).show();

                } else {

                    if (tvCurrentLocation != null) {

                        tvCurrentLocation.setText(
                                String.format(
                                        Locale.getDefault(),

                                        "Near you • %.5f, %.5f",

                                        latitude,
                                        longitude
                                )
                        );
                    }
                }
            });
        });
    }


    // =============================================================
    // OVERPASS SEARCH
    // =============================================================

    private List<SafePlace> queryOverpass(
            double latitude,
            double longitude
    ) {

        /*
         * This query asks OpenStreetMap for MANY useful real-world
         * locations within 10 km.
         *
         * We intentionally include:
         *
         * - Police
         * - Hospitals
         * - Clinics
         * - Pharmacies
         * - Fire stations
         * - Schools
         * - Colleges
         * - Universities
         * - Shopping malls
         * - Supermarkets
         * - Markets
         * - Department stores
         * - Banks
         * - ATMs
         * - Hotels
         * - Bus stations
         * - Railway stations
         * - Public transport
         * - Community centres
         * - Libraries
         * - Government/public buildings
         * - Shelters
         *
         * These are actual OSM objects, not generated locations.
         */

        String query =

                "[out:json][timeout:40];"

                        + "("

                        // -------------------------------------------------
                        // POLICE
                        // -------------------------------------------------

                        + "nwr[amenity=police]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // HOSPITALS
                        // -------------------------------------------------

                        + "nwr[amenity=hospital]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // CLINICS
                        // -------------------------------------------------

                        + "nwr[amenity=clinic]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // PHARMACIES
                        // -------------------------------------------------

                        + "nwr[amenity=pharmacy]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // FIRE STATIONS
                        // -------------------------------------------------

                        + "nwr[amenity=fire_station]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // SCHOOLS
                        // -------------------------------------------------

                        + "nwr[amenity=school]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // COLLEGES
                        // -------------------------------------------------

                        + "nwr[amenity=college]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // UNIVERSITIES
                        // -------------------------------------------------

                        + "nwr[amenity=university]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // COMMUNITY CENTRES
                        // -------------------------------------------------

                        + "nwr[amenity=community_centre]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // LIBRARIES
                        // -------------------------------------------------

                        + "nwr[amenity=library]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // SHELTERS
                        // -------------------------------------------------

                        + "nwr[amenity=shelter]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // BANKS
                        // -------------------------------------------------

                        + "nwr[amenity=bank]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // ATMS
                        // -------------------------------------------------

                        + "nwr[amenity=atm]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // SHOPPING MALLS
                        // -------------------------------------------------

                        + "nwr[shop=mall]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // SUPERMARKETS
                        // -------------------------------------------------

                        + "nwr[shop=supermarket]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // DEPARTMENT STORES
                        // -------------------------------------------------

                        + "nwr[shop=department_store]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // CONVENIENCE STORES
                        // -------------------------------------------------

                        + "nwr[shop=convenience]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // MARKETPLACES
                        // -------------------------------------------------

                        + "nwr[amenity=marketplace]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // HOTELS
                        // -------------------------------------------------

                        + "nwr[tourism=hotel]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // HOSTELS
                        // -------------------------------------------------

                        + "nwr[tourism=hostel]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // BUS STATIONS
                        // -------------------------------------------------

                        + "nwr[amenity=bus_station]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // RAILWAY STATIONS
                        // -------------------------------------------------

                        + "nwr[railway=station]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // PUBLIC TRANSPORT
                        // -------------------------------------------------

                        + "nwr[public_transport=station]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        // -------------------------------------------------
                        // GOVERNMENT / PUBLIC BUILDINGS
                        // -------------------------------------------------

                        + "nwr[office=government]"
                        + "(around:"
                        + SEARCH_RADIUS_METERS
                        + ","
                        + latitude
                        + ","
                        + longitude
                        + ");"


                        + ");"

                        /*
                         * center gives ways/relations a usable
                         * representative latitude/longitude.
                         *
                         * tags gives us names, addresses,
                         * phone numbers, categories, etc.
                         */

                        + "out center tags;";


        /*
         * Try multiple Overpass servers.
         */

        for (
                String endpoint :
                OVERPASS_ENDPOINTS
        ) {

            HttpURLConnection connection = null;

            try {

                String encodedQuery =
                        URLEncoder.encode(
                                query,
                                StandardCharsets.UTF_8.toString()
                        );


                URL url =
                        new URL(
                                endpoint
                                        + "?data="
                                        + encodedQuery
                        );


                connection =
                        (HttpURLConnection)
                                url.openConnection();


                connection.setRequestMethod(
                        "GET"
                );

                connection.setConnectTimeout(
                        15000
                );

                connection.setReadTimeout(
                        45000
                );

                connection.setRequestProperty(
                        "User-Agent",
                        "SheShield/1.0 Android Safe Zones"
                );


                int responseCode =
                        connection.getResponseCode();


                if (
                        responseCode >= 200
                                &&
                                responseCode < 300
                ) {

                    InputStream inputStream =
                            connection.getInputStream();


                    String response =
                            readInputStream(
                                    inputStream
                            );


                    return parseOverpassResponse(
                            response
                    );
                }

            } catch (Exception ignored) {

                /*
                 * Try the next Overpass server.
                 */

            } finally {

                if (connection != null) {

                    connection.disconnect();
                }
            }
        }


        return new ArrayList<>();
    }


    // =============================================================
    // READ NETWORK RESPONSE
    // =============================================================

    private String readInputStream(
            InputStream inputStream
    ) throws Exception {

        StringBuilder builder =
                new StringBuilder();


        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                inputStream,
                                StandardCharsets.UTF_8
                        )
                );


        String line;


        while (
                (line = reader.readLine())
                        != null
        ) {

            builder.append(line);
        }


        reader.close();


        return builder.toString();
    }


    // =============================================================
    // PARSE OVERPASS JSON
    // =============================================================

    private List<SafePlace> parseOverpassResponse(
            String json
    ) {

        List<SafePlace> results =
                new ArrayList<>();


        if (
                json == null
                        ||
                        json.trim().isEmpty()
        ) {

            return results;
        }


        try {

            JSONObject root =
                    new JSONObject(json);


            JSONArray elements =
                    root.optJSONArray(
                            "elements"
                    );


            if (elements == null) {

                return results;
            }


            for (
                    int i = 0;
                    i < elements.length();
                    i++
            ) {

                JSONObject element =
                        elements.optJSONObject(i);


                if (element == null) {

                    continue;
                }


                JSONObject tags =
                        element.optJSONObject(
                                "tags"
                        );


                if (tags == null) {

                    continue;
                }


                /*
                 * Get coordinates.
                 *
                 * Node:
                 *   lat / lon
                 *
                 * Way/relation:
                 *   center.lat / center.lon
                 */

                double lat =
                        element.optDouble(
                                "lat",
                                Double.NaN
                        );

                double lon =
                        element.optDouble(
                                "lon",
                                Double.NaN
                        );


                if (
                        Double.isNaN(lat)
                                ||
                                Double.isNaN(lon)
                ) {

                    JSONObject center =
                            element.optJSONObject(
                                    "center"
                            );


                    if (center != null) {

                        lat =
                                center.optDouble(
                                        "lat",
                                        Double.NaN
                                );

                        lon =
                                center.optDouble(
                                        "lon",
                                        Double.NaN
                                );
                    }
                }


                /*
                 * Never add a result without valid coordinates.
                 */

                if (
                        Double.isNaN(lat)
                                ||
                                Double.isNaN(lon)
                ) {

                    continue;
                }


                String elementType =
                        element.optString(
                                "type",
                                ""
                        );


                long elementId =
                        element.optLong(
                                "id",
                                0
                        );


                String placeKey =
                        elementType
                                + "_"
                                + elementId;


                /*
                 * Prevent duplicates.
                 */

                if (
                        existingPlaceKeys.contains(
                                placeKey
                        )
                ) {

                    continue;
                }


                existingPlaceKeys.add(
                        placeKey
                );


                String name =
                        firstNonEmpty(
                                tags.optString(
                                        "name",
                                        ""
                                ),

                                tags.optString(
                                        "official_name",
                                        ""
                                ),

                                tags.optString(
                                        "brand",
                                        ""
                                ),

                                "Nearby Safe Place"
                        );


                String category =
                        getCategoryFromTags(
                                tags
                        );


                String address =
                        getAddressFromTags(
                                tags
                        );


                String phone =
                        firstNonEmpty(
                                tags.optString(
                                        "phone",
                                        ""
                                ),

                                tags.optString(
                                        "contact:phone",
                                        ""
                                ),

                                tags.optString(
                                        "mobile",
                                        ""
                                ),

                                ""
                        );


                /*
                 * Calculate actual distance from user's
                 * actual GPS location.
                 */

                float[] distance =
                        new float[1];


                if (currentLocation != null) {

                    Location.distanceBetween(

                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),

                            lat,
                            lon,

                            distance
                    );

                } else {

                    distance[0] = Float.MAX_VALUE;
                }


                double distanceKm =
                        distance[0] / 1000.0;


                /*
                 * Ignore impossible/outside-radius objects.
                 *
                 * This protects against malformed OSM data.
                 */

                if (
                        distance[0]
                                >
                                SEARCH_RADIUS_METERS + 500
                ) {

                    continue;
                }


                SafePlace safePlace =
                        new SafePlace(

                                name,

                                category,

                                address,

                                phone,

                                distanceKm,

                                lat,

                                lon,

                                placeKey
                        );


                results.add(
                        safePlace
                );
            }


            /*
             * Nearest first.
             */

            Collections.sort(
                    results,

                    Comparator.comparingDouble(
                            place ->
                                    place.distanceKm
                    )
            );


        } catch (Exception ignored) {

            /*
             * Invalid JSON / server response.
             */
        }


        return results;
    }


    // =============================================================
    // CATEGORY FROM OSM TAGS
    // =============================================================

    private String getCategoryFromTags(
            JSONObject tags
    ) {

        String amenity =
                tags.optString(
                                "amenity",
                                ""
                        )
                        .toLowerCase(
                                Locale.ROOT
                        );


        String shop =
                tags.optString(
                                "shop",
                                ""
                        )
                        .toLowerCase(
                                Locale.ROOT
                        );


        String tourism =
                tags.optString(
                                "tourism",
                                ""
                        )
                        .toLowerCase(
                                Locale.ROOT
                        );


        String railway =
                tags.optString(
                                "railway",
                                ""
                        )
                        .toLowerCase(
                                Locale.ROOT
                        );


        String publicTransport =
                tags.optString(
                                "public_transport",
                                ""
                        )
                        .toLowerCase(
                                Locale.ROOT
                        );


        String office =
                tags.optString(
                                "office",
                                ""
                        )
                        .toLowerCase(
                                Locale.ROOT
                        );


        // ---------------------------------------------------------
        // POLICE
        // ---------------------------------------------------------

        if (
                amenity.equals("police")
        ) {

            return "POLICE";
        }


        // ---------------------------------------------------------
        // HOSPITAL
        // ---------------------------------------------------------

        if (
                amenity.equals("hospital")
        ) {

            return "HOSPITAL";
        }


        // ---------------------------------------------------------
        // CLINIC
        // ---------------------------------------------------------

        if (
                amenity.equals("clinic")
        ) {

            return "CLINIC";
        }


        // ---------------------------------------------------------
        // PHARMACY
        // ---------------------------------------------------------

        if (
                amenity.equals("pharmacy")
        ) {

            return "PHARMACY";
        }


        // ---------------------------------------------------------
        // FIRE STATION
        // ---------------------------------------------------------

        if (
                amenity.equals("fire_station")
        ) {

            return "FIRE STATION";
        }


        // ---------------------------------------------------------
        // SCHOOL
        // ---------------------------------------------------------

        if (
                amenity.equals("school")
        ) {

            return "SCHOOL";
        }


        // ---------------------------------------------------------
        // COLLEGE
        // ---------------------------------------------------------

        if (
                amenity.equals("college")
        ) {

            return "COLLEGE";
        }


        // ---------------------------------------------------------
        // UNIVERSITY
        // ---------------------------------------------------------

        if (
                amenity.equals("university")
        ) {

            return "UNIVERSITY";
        }


        // ---------------------------------------------------------
        // COMMUNITY CENTRE
        // ---------------------------------------------------------

        if (
                amenity.equals(
                        "community_centre"
                )
        ) {

            return "COMMUNITY CENTRE";
        }


        // ---------------------------------------------------------
        // LIBRARY
        // ---------------------------------------------------------

        if (
                amenity.equals("library")
        ) {

            return "LIBRARY";
        }


        // ---------------------------------------------------------
        // SHELTER
        // ---------------------------------------------------------

        if (
                amenity.equals("shelter")
        ) {

            return "SHELTER";
        }


        // ---------------------------------------------------------
        // BANK
        // ---------------------------------------------------------

        if (
                amenity.equals("bank")
        ) {

            return "BANK";
        }


        // ---------------------------------------------------------
        // ATM
        // ---------------------------------------------------------

        if (
                amenity.equals("atm")
        ) {

            return "ATM";
        }


        // ---------------------------------------------------------
        // SHOPPING / CROWDED
        // ---------------------------------------------------------

        if (
                shop.equals("mall")
                        ||
                        shop.equals("supermarket")
                        ||
                        shop.equals("department_store")
                        ||
                        shop.equals("convenience")
                        ||
                        amenity.equals("marketplace")
        ) {

            return "CROWDED HUB";
        }


        // ---------------------------------------------------------
        // HOTEL
        // ---------------------------------------------------------

        if (
                tourism.equals("hotel")
        ) {

            return "HOTEL";
        }


        // ---------------------------------------------------------
        // HOSTEL
        // ---------------------------------------------------------

        if (
                tourism.equals("hostel")
        ) {

            return "HOSTEL";
        }


        // ---------------------------------------------------------
        // RAILWAY
        // ---------------------------------------------------------

        if (
                railway.equals("station")
        ) {

            return "RAILWAY STATION";
        }


        // ---------------------------------------------------------
        // BUS / PUBLIC TRANSPORT
        // ---------------------------------------------------------

        if (
                amenity.equals("bus_station")
                        ||
                        publicTransport.equals(
                                "station"
                        )
        ) {

            return "TRANSPORT HUB";
        }


        // ---------------------------------------------------------
        // GOVERNMENT
        // ---------------------------------------------------------

        if (
                office.equals("government")
        ) {

            return "GOVERNMENT";
        }


        return "SAFE PLACE";
    }


    // =============================================================
    // ADDRESS
    // =============================================================

    private String getAddressFromTags(
            JSONObject tags
    ) {

        String fullAddress =
                firstNonEmpty(
                        tags.optString(
                                "addr:full",
                                ""
                        ),

                        tags.optString(
                                "address",
                                ""
                        ),

                        ""
                );


        if (
                !fullAddress.isEmpty()
        ) {

            return fullAddress;
        }


        List<String> parts =
                new ArrayList<>();


        addAddressPart(
                parts,
                tags,
                "addr:housenumber"
        );

        addAddressPart(
                parts,
                tags,
                "addr:street"
        );

        addAddressPart(
                parts,
                tags,
                "addr:suburb"
        );

        addAddressPart(
                parts,
                tags,
                "addr:neighbourhood"
        );

        addAddressPart(
                parts,
                tags,
                "addr:city"
        );

        addAddressPart(
                parts,
                tags,
                "addr:postcode"
        );


        if (parts.isEmpty()) {

            return "Address unavailable";
        }


        StringBuilder builder =
                new StringBuilder();


        for (
                int i = 0;
                i < parts.size();
                i++
        ) {

            if (i > 0) {

                builder.append(", ");
            }

            builder.append(
                    parts.get(i)
            );
        }


        return builder.toString();
    }


    // =============================================================
    // ADDRESS PART
    // =============================================================

    private void addAddressPart(
            List<String> parts,
            JSONObject tags,
            String key
    ) {

        String value =
                tags.optString(
                        key,
                        ""
                ).trim();


        if (!value.isEmpty()) {

            parts.add(value);
        }
    }


    // =============================================================
    // FIRST NON-EMPTY STRING
    // =============================================================

    private String firstNonEmpty(
            String... values
    ) {

        if (values == null) {

            return "";
        }


        for (
                String value :
                values
        ) {

            if (
                    value != null
                            &&
                            !value.trim().isEmpty()
            ) {

                return value.trim();
            }
        }


        return "";
    }


    // =============================================================
    // LOAD FILTERED PLACES
    // =============================================================

    private void loadFilteredPlaces() {

        List<SafePlace> filtered =
                new ArrayList<>();


        // =============================================================
        // APPLY CURRENT FILTER
        // =============================================================

        for (
                SafePlace place :
                allPlaces
        ) {

            if (matchesCurrentFilter(place)) {

                filtered.add(place);
            }
        }


        // =============================================================
        // SORT BY NEAREST DISTANCE
        // =============================================================

        sortList(filtered);


        // =============================================================
        // SHOW MAXIMUM 20 REAL PLACES
        // =============================================================

        if (filtered.size() > 20) {

            filtered =
                    new ArrayList<>(
                            filtered.subList(
                                    0,
                                    20
                            )
                    );
        }


        // =============================================================
        // UPDATE RECYCLER VIEW
        // =============================================================

        adapter.updateList(
                filtered
        );


        // =============================================================
        // UPDATE RESULT COUNT
        // =============================================================

        updateResultCount(
                filtered.size()
        );
    }

    // =============================================================
    // FILTER MATCH
    // =============================================================

    private boolean matchesCurrentFilter(
            SafePlace place
    ) {

        if (
                place == null
                        ||
                        place.category == null
        ) {

            return false;
        }


        if (
                currentFilter.equals("ALL")
        ) {

            return true;
        }


        if (
                currentFilter.equals("POLICE")
        ) {

            return place.category.equals(
                    "POLICE"
            );
        }


        if (
                currentFilter.equals("HOSPITAL")
        ) {

            return place.category.equals(
                    "HOSPITAL"
            )
                    ||
                    place.category.equals(
                            "CLINIC"
                    )
                    ||
                    place.category.equals(
                            "PHARMACY"
                    );
        }


        if (
                currentFilter.equals("CROWDED")
        ) {

            return place.category.equals(
                    "CROWDED HUB"
            );
        }


        return true;
    }


    // =============================================================
    // SORT ALL PLACES
    // =============================================================

    private void sortByDistance() {

        sortList(
                allPlaces
        );
    }


    // =============================================================
    // SORT LIST
    // =============================================================

    private void sortList(
            List<SafePlace> list
    ) {

        Collections.sort(

                list,

                Comparator.comparingDouble(
                        place ->
                                place.distanceKm
                )
        );
    }


    // =============================================================
    // OPEN GOOGLE MAPS
    // =============================================================

    private void openGoogleMaps(
            SafePlace place
    ) {

        if (place == null) {

            return;
        }


        try {

            /*
             * First try the Google Maps Android application.
             */

            Uri navigationUri =
                    Uri.parse(

                            "google.navigation:q="
                                    + place.lat
                                    + ","
                                    + place.lng
                    );


            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            navigationUri
                    );


            intent.setPackage(
                    "com.google.android.apps.maps"
            );


            if (
                    intent.resolveActivity(
                            getPackageManager()
                    ) != null
            ) {

                startActivity(intent);

                return;
            }


            /*
             * Google Maps app unavailable.
             *
             * Open Google Maps in the browser.
             *
             * This does NOT require a Google API key.
             */

            Uri webUri =
                    Uri.parse(

                            "https://www.google.com/maps/dir/?api=1"
                                    + "&destination="
                                    + place.lat
                                    + ","
                                    + place.lng
                    );


            startActivity(
                    new Intent(
                            Intent.ACTION_VIEW,
                            webUri
                    )
            );


        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Unable to open directions.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // =============================================================
    // CALL PLACE
    // =============================================================

    private void callPlace(
            SafePlace place
    ) {

        if (
                place == null
                        ||
                        place.phoneNumber == null
                        ||
                        place.phoneNumber.trim().isEmpty()
        ) {

            Toast.makeText(
                    this,
                    "Phone number is not available.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        try {

            Intent intent =
                    new Intent(

                            Intent.ACTION_DIAL,

                            Uri.parse(
                                    "tel:"
                                            + place.phoneNumber
                            )
                    );


            startActivity(intent);


        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Unable to open dialer.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // =============================================================
    // SHOW LOADING
    // =============================================================

    private void showLoading() {

        if (progressBar != null) {

            progressBar.setVisibility(
                    View.VISIBLE
            );
        }
    }


    // =============================================================
    // HIDE LOADING
    // =============================================================

    private void hideLoading() {

        if (progressBar != null) {

            progressBar.setVisibility(
                    View.GONE
            );
        }
    }


    // =============================================================
    // RESULT COUNT
    // =============================================================

    private void updateResultCount(
            int count
    ) {

        if (tvResultCount == null) {

            return;
        }


        if (count == 0) {

            tvResultCount.setText(
                    "No safe places found"
            );

        } else if (count == 1) {

            tvResultCount.setText(
                    "1 safe place found nearby"
            );

        } else {

            tvResultCount.setText(
                    count
                            + " safe places found nearby"
            );
        }
    }


    // =============================================================
    // PERMISSION RESULT
    // =============================================================

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


        if (
                requestCode
                        ==
                        LOCATION_PERMISSION_REQUEST_CODE
        ) {

            boolean locationGranted =
                    false;


            for (
                    int result :
                    grantResults
            ) {

                if (
                        result
                                ==
                                PackageManager.PERMISSION_GRANTED
                ) {

                    locationGranted = true;

                    break;
                }
            }


            if (locationGranted) {

                getUserLocation();

            } else {

                hideLoading();


                if (tvCurrentLocation != null) {

                    tvCurrentLocation.setText(
                            "Location permission denied"
                    );
                }


                Toast.makeText(
                        this,

                        "Location permission is required to find nearby safe places.",

                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }


    // =============================================================
    // ACTIVITY DESTROYED
    // =============================================================

    @Override
    protected void onDestroy() {

        super.onDestroy();


        /*
         * Stop the background executor when the Activity is
         * destroyed.
         */

        executor.shutdownNow();
    }


    // =============================================================
    // SAFE PLACE MODEL
    // =============================================================

    public static class SafePlace {

        public String name;

        public String category;

        public String address;

        public String phoneNumber;

        public double distanceKm;

        public double lat;

        public double lng;

        public String placeId;


        public SafePlace(

                String name,

                String category,

                String address,

                String phoneNumber,

                double distanceKm,

                double lat,

                double lng,

                String placeId

        ) {

            this.name =
                    name;

            this.category =
                    category;

            this.address =
                    address;

            this.phoneNumber =
                    phoneNumber;

            this.distanceKm =
                    distanceKm;

            this.lat =
                    lat;

            this.lng =
                    lng;

            this.placeId =
                    placeId;
        }
    }
}