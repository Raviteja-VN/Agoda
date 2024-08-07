package com.Agoda.Agoda.app.Repository;

import com.Agoda.Agoda.app.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

   List<Hotel> findByLocationAndRoomTypeAndPrice(
           String location,
           String roomType,
           String price
   );
}
