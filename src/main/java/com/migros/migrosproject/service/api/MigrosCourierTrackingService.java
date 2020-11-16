package com.migros.migrosproject.service.api;

public interface MigrosCourierTrackingService {
    void checkCourierDistance(String courier, double courierLat, double courierLng);
    Double getTotalTravelDistance(String courier);
}
