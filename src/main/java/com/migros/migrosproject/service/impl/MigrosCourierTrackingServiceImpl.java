package com.migros.migrosproject.service.impl;

import com.migros.migrosproject.service.api.MigrosCourierTrackingService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.lang.Math.sin;

@Component
public class MigrosCourierTrackingServiceImpl implements MigrosCourierTrackingService {
    Logger log = LoggerFactory.getLogger(MigrosCourierTrackingServiceImpl.class);

    @Override
    public void checkCourierDistance(String courier, double courierLat, double courierLng) {
        JSONArray storesList = readJsonData("/Users/esenturkay/stores.json");
        double storeRadius = 100.19;

        for (Object obj : storesList) {
            JSONObject jsonStoreObject = (JSONObject) obj;
            String storeName = (String) jsonStoreObject.get("name");

            double storeLat = (Double) jsonStoreObject.get("lat");

            double storeLng = (Double) jsonStoreObject.get("lng");

            double distanceBetweenTwoPoints = getDistanceBetweenTwoPoints(storeLat, storeLng, courierLat, courierLng);
            if (distanceBetweenTwoPoints <= storeRadius) {
                LocalTime currentTime = LocalTime.now();
                JSONArray courierData = readJsonData("/Users/esenturkay/couriers.json");
                if (courierData.isEmpty()){
                    writeJsonData(courier, courierLat, courierLng, currentTime.toString(), storeName, distanceBetweenTwoPoints);
                }else {
                    JSONObject courierDetails = new JSONObject();
                    courierDetails.put("courier", courier);
                    courierDetails.put("lat", courierLat);
                    courierDetails.put("lng", courierLng);
                    courierDetails.put("time", currentTime.toString());
                    courierDetails.put("store", storeName);
                    courierDetails.put("distance",distanceBetweenTwoPoints);
                    int size = courierData.size();
                    JSONObject o = (JSONObject)courierData.get(size-1);
                    LocalTime courierTime = LocalTime.parse(o.get("time").toString());
                    LocalTime courierNewTime = LocalTime.parse(courierDetails.get("time").toString());

                    if (courierTime.getHour() != courierNewTime.getHour() || (courierTime.getHour() == courierNewTime.getHour() && courierTime.getMinute() != courierNewTime.getMinute()))
                    {
                        courierData.add(courierDetails);
                        try (FileWriter file = new FileWriter("/Users/esenturkay/couriers.json")) {
                            file.write(courierData.toJSONString());
                            file.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        log.info("Courier can't re-entered");
                    }
                }
            }
        }
    }

    @Override
    public Double getTotalTravelDistance(String courier) {
        JSONArray courierData = readJsonData("/Users/esenturkay/couriers.json");
        double totalTravelDistance = 0.0;
        for (Object courierObj : courierData){
            JSONObject jsonCourierObject = (JSONObject) courierObj;
            String courierName = (String)jsonCourierObject.get("courier");
            if (courierName.equals(courier)){
                totalTravelDistance =  (Double)jsonCourierObject.get("distance") + totalTravelDistance;
            }
        }
        return totalTravelDistance;
    }

    private Double getDistanceBetweenTwoPoints(double storeLat, double storeLng, double courierLat, double courierLng) {
        double r = 6371;

        // Haversine formul
        double latDistance = Math.toRadians(courierLat - storeLat);
        double lonDistance = Math.toRadians(courierLng - storeLng);
        double a = sin(latDistance / 2) * sin(latDistance / 2)
                + Math.cos(Math.toRadians(storeLat)) * Math.cos(Math.toRadians(courierLat))
                * sin(lonDistance / 2) * sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return r * c * 1000;
    }

    private JSONArray readJsonData(String fileName) {
        JSONParser jsonParser = new JSONParser();
        JSONArray storeList = new JSONArray();
        try (FileReader reader = new FileReader(fileName)) {
            //Read file
            Object obj = jsonParser.parse(reader);
            storeList = (JSONArray) obj;
            System.out.println(storeList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return storeList;
    }

    private void writeJsonData(String courier, double lat, double lng, String time, String store, double distance) {
        JSONObject courierDetails = new JSONObject();
        courierDetails.put("courier", courier);
        courierDetails.put("lat", lat);
        courierDetails.put("lng", lng);
        courierDetails.put("time", time);
        courierDetails.put("store", store);
        courierDetails.put("distance", distance);
        JSONArray courierList = new JSONArray();
        courierList.add(courierDetails);
        try (FileWriter file = new FileWriter("/Users/esenturkay/couriers.json")) {
            file.write(courierList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
