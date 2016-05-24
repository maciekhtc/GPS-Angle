package com.gmail.maciekhtc.gpsangle;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * Created by 15936 on 01.05.2016.
 */
public class MyUtils {
    public static Location locFromLatLng(LatLng input)
    {
        Location result = new Location("");
        result.setLatitude(input.latitude);
        result.setLongitude(input.longitude);
        return result;
    }
    public static LatLng latlngFromLocation(Location input)
    {
        return new LatLng(input.getLatitude(),input.getLongitude());
    }
    public static double fieldOfTriangle(double lengthA, double lengthB, double lengthC)
    {
        double lengthP=(lengthA+lengthB+lengthC)/2;
        return Math.sqrt(lengthP*(lengthP-lengthA)*(lengthP-lengthB)*(lengthP-lengthC));
    }
    public static double fieldFromLocations(Location loc1, Location loc2, Location loc3)
    {
        return (fieldOfTriangle(loc1.distanceTo(loc2),loc2.distanceTo(loc3),loc3.distanceTo(loc1)));
    }
    public static Location middleLocation(Location[] listOfPoints)
    {
        Location result=new Location(listOfPoints[0]);      //przykladowy punkt do modyfikacji, bo ciezko stworzyc od zera
        double sumLatitudes=0, sumLongitudes=0, sumAltitudes=0;     //init zmiennych
        for (Location loc : listOfPoints)
        {
            sumLatitudes += loc.getLatitude();
            sumLongitudes += loc.getLongitude();
            sumAltitudes += loc.getAltitude();
        }
        int quantity = listOfPoints.length;
        result.setLatitude(sumLatitudes / quantity);
        result.setLongitude(sumLongitudes / quantity);
        result.setAltitude(sumAltitudes / quantity);
        return result;
    }
    public static double calculateField(Location[] listOfPoints)
    {
        double result=0;
        Location middlePoint = middleLocation(listOfPoints);
        for (int i=0;i<listOfPoints.length-1;i++)
        {
            result+=fieldFromLocations(middlePoint,listOfPoints[i],listOfPoints[i+1]);
        }
        result+=fieldFromLocations(middlePoint,listOfPoints[0],listOfPoints[listOfPoints.length-1]);
        return result;
    }
}
