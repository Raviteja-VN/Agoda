package com.Agoda.Agoda.app.Controller;

import com.Agoda.Agoda.app.Payload.HotelDto;
import com.Agoda.Agoda.app.Service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private final HotelService hotelService;
    @Autowired
    private final ModelMapper modelMapper;
@Autowired
    public HotelController(HotelService hotelService, ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    //http://localhost:8080/api/hotels/create
    @PostMapping("/create")
    public ResponseEntity<HotelDto> createHotel(
            @RequestParam("hotelName") String hotelName,
            @RequestParam("location") String location,
            @RequestParam("roomType") String roomType,
            @RequestParam("personsPerRoom") String personsPerRoom,
            @RequestParam("price") String price,
            @RequestParam("pinCode") String pinCode,
            @RequestParam(value = "hotelImage", required = false) MultipartFile hotelImage) {

        HotelDto hotelDto= new HotelDto();
        hotelDto.setHotelName(hotelName);
        hotelDto.setLocation(location);
        hotelDto.setRoomType(roomType);
        hotelDto.setPersonsPerRoom(personsPerRoom);
        hotelDto.setPrice(price);
        hotelDto.setPinCode(pinCode);
        hotelDto.setHotelImage(hotelImage);

      HotelDto createdHotel=  hotelService.createHotel(hotelDto);
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);

    }

    //http://localhost:8080/api/hotels/gethotel?page=1&size=2&sort=id,asc
    @GetMapping("/gethotel")
    public ResponseEntity<Page<HotelDto>> getHotel(@PageableDefault(size = 2,sort="id") Pageable pageable){
        Page<HotelDto> hotelPage= hotelService.getHotel(pageable);
        return new ResponseEntity<>(hotelPage,HttpStatus.OK);

    }

    //http://localhost:8080/api/hotels/getby/id
    @GetMapping("/getby/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id")long id){
    return new ResponseEntity<>(hotelService.getHotelById(id),HttpStatus.OK);

    }
    //http://localhost:8080/api/hotels/update/id
    @PutMapping("/update/{hotelId}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long hotelId, @RequestBody HotelDto hotelDto){

        HotelDto updatedHotelDto= hotelService.updateHotel(hotelId,hotelDto);
        return new ResponseEntity<>(updatedHotelDto,HttpStatus.OK);
    }
    //http://localhost:8080/api/hotels/id
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId){
        hotelService.deleteHotel(hotelId);
        return new ResponseEntity<>("Hotel Successfully deleted",HttpStatus.OK);

    }

    //http://localhost:8080/api/hotels/search?location=&roomType=&price=
    @GetMapping("/search")
    public ResponseEntity<List<HotelDto>> searchHotels(
            @RequestParam(required = true) String location,
            @RequestParam(required = true) String roomType,
            @RequestParam(required = true) String price
    ) {
   List<HotelDto> hotelList= hotelService.searchHotels(location,roomType,price);
   return  new ResponseEntity<>(hotelList,HttpStatus.OK);
    }
}