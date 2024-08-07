package com.Agoda.Agoda.app.Service;

import com.Agoda.Agoda.app.Payload.HotelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    HotelDto createHotel(HotelDto hotelDto);

    Page<HotelDto> getHotel(Pageable pageable);

    HotelDto updateHotel(Long hotelId, HotelDto hotelDto);

    void deleteHotel(Long hotelId);

    HotelDto getHotelById(long id);

    List<HotelDto> searchHotels(String location, String roomType, String price);
}
