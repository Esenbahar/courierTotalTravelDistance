package com.migros.migrosproject.service.rest;

import com.migros.migrosproject.service.api.MigrosCourierTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final MigrosCourierTrackingService migrosCourierTrackingService;

    @Autowired
    public Controller(MigrosCourierTrackingService migrosCourierTrackingService) {
        this.migrosCourierTrackingService = migrosCourierTrackingService;
    }

    @RequestMapping(value = "/check-courier", method = RequestMethod.GET)
    public String checkCourier(@RequestParam(value = "courier", defaultValue = "test")String courierName,@RequestParam(value = "lat", defaultValue = "0.0") double courierLat, @RequestParam(value = "lng", defaultValue = "0.0") double courierLng){
        migrosCourierTrackingService.checkCourierDistance(courierName, courierLat, courierLng);
        return "Check Courier Distance !!";
    }

    @RequestMapping(value = "/travel-distance", method = RequestMethod.GET)
    public String getTotalTravelDistance(@RequestParam(value = "courier", defaultValue = "test")String courierName){
        Double totalTravelDistance = migrosCourierTrackingService.getTotalTravelDistance(courierName);
        return "Courier Total Travel Distance :" + totalTravelDistance;
    }
}
