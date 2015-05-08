package nz.ac.aut.DMS.Hangouts;

import android.location.Location;

/**
 * Created by Ashellan Edmonds on 8/05/15.
 */
public class User {
    String username, phone, lng, lat, date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Location getLocation(){
        Location lastKnownLocation = new Location("");
        lastKnownLocation.setLatitude(Double.valueOf(getLat()));
        lastKnownLocation.setLongitude(Double.valueOf(getLng()));
        return lastKnownLocation;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
