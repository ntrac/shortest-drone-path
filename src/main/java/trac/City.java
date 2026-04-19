/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trac;

/**
 *
 * @author nguyentrac
 */
import java.util.HashSet;
import java.util.Set;
public class City extends Government {
    private Set<Integer> zipCodes;  // Track multiple zip codes for each city
    private String stateName;
    private double latitude;
    private double longitude;
    private int timezone;
    private boolean yesDaylight;

    public City(int zip, String cName, String stateName, double lat, double lon, int zone, boolean daylight) {
        super(cName);
        this.zipCodes = new HashSet<>();
        this.zipCodes.add(zip); // Add the initial zip code
        this.stateName = stateName;
        this.latitude = lat;
        this.longitude = lon;
        this.timezone = zone;
        this.yesDaylight = daylight;
    }

    // Add a new zip code to the city (use Set to avoid duplicates)
    public void addZipCode(int zipCode) {
        zipCodes.add(zipCode);
    }

    // Get the number of zip codes associated with the city
    public int getZipCodeCount() {
        return zipCodes.size();
    }

    // Get the original zip code (first added zip code)
    public int getZipcode() {
        return zipCodes.iterator().next();
    }

    // Get all the zip codes associated with the city
    public Set<Integer> getZipCodes() {
        return zipCodes;
    }

    public String getCityName() {
        return govName;
    }

    public String stateName() {
        return stateName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getTimezone() {
        return timezone;
    }

    public Boolean getDaylight() {
        return yesDaylight;
    }

    @Override
public String toString() {
    return getCityName() + ", " + stateName();
}

    // Get timezone abbreviation based on the timezone and DST status
    public String getTimezoneAbbreviation() {
        switch (timezone) {
            case -4: return yesDaylight ? "ADT" : "AST";
            case -5: return yesDaylight ? "EDT" : "EST";
            case -6: return yesDaylight ? "CDT" : "CST";
            case -7: return yesDaylight ? "MDT" : "MST";
            case -8: return yesDaylight ? "PDT" : "PST";
            case -9: return yesDaylight ? "AKDT" : "AKST";
            case -10: return yesDaylight ? "HDT" : "HST";
            default: return "Timezone Unknown";
        }
    }
}