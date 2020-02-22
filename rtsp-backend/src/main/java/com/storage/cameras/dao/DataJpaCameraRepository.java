package com.storage.cameras.dao;

import com.storage.cameras.model.Camera;
import com.storage.cameras.model.CameraStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataJpaCameraRepository extends JpaRepository<Camera, Long> {
    Camera getById(Long id);

    Camera getByUrl(String url);

    List<Camera> findAll();

    List<Camera> findAllByStatus(CameraStatus status);

    List<Camera> findAllByCountryCode(String countryCode);

    List<Camera> findAllByStatusAndCountryCode(CameraStatus status, String countryCode);

    List<Camera> findAllByStatusAndCity(CameraStatus status, String city);

    @Query(nativeQuery = true,
            value = "SELECT * FROM cameras_db.camera " +
                    "WHERE city IS NULL " +
                    "OR country_code IS NULL " +
                    "OR country_name IS NULL")
    List<Camera> findWithNoGeolocation();

    @Query(nativeQuery = true, value = "SELECT id FROM camera")
    List<Long> getAllIds();
}
