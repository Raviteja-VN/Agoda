package com.Agoda.Agoda.app.Controller;


import com.Agoda.Agoda.app.Payload.HotelDto;
import com.Agoda.Agoda.app.Payload.ListingPropertyDto;
import com.Agoda.Agoda.app.Service.ListingPropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/property")
public class ListingPropertyController {
@Autowired
    private final ListingPropertyService listingPropertyService;
@Autowired
private final ModelMapper modelMapper;
@Autowired
    public ListingPropertyController(ListingPropertyService listingPropertyService, ModelMapper modelMapper) {
        this.listingPropertyService = listingPropertyService;
        this.modelMapper = modelMapper;
    }

    //http://localhost:8080/api/property/create
    @PostMapping("/create")
    public ResponseEntity<ListingPropertyDto> createProperty(
            @RequestParam("propertyType") String propertyType,
            @RequestParam("ownedBy") String ownedBy,
            @RequestParam("city") String city,
            @RequestParam("totalRooms") String totalRooms,
            @RequestParam("roomType") String roomType,
            @RequestParam("price") String price,
            @RequestParam(value = "propertyImage", required = false) MultipartFile propertyImage) {

        ListingPropertyDto listingPropertyDto= new ListingPropertyDto();
        listingPropertyDto.setPropertyType(propertyType);
        listingPropertyDto.setOwnedBy(ownedBy);
        listingPropertyDto.setCity(city);
        listingPropertyDto.setTotalRooms(totalRooms);
        listingPropertyDto.setRoomType(roomType);
        listingPropertyDto.setPrice(price);
        listingPropertyDto.setPropertyImage(propertyImage);

        ListingPropertyDto createdProperty=  listingPropertyService.createProperty(listingPropertyDto);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);

    }
    //http://localhost:8080/api/property/getproperty?page=1&size=2&sort=id,asc
    @GetMapping("/getproperty")
    public ResponseEntity<Page<ListingPropertyDto>> getProperty(@PageableDefault(size = 2,sort="id") Pageable pageable){
        Page<ListingPropertyDto> propertyPage= listingPropertyService.getProperty(pageable);
        return new ResponseEntity<>(propertyPage,HttpStatus.OK);

    }

    //http://localhost:8080/api/property/getby/id
@GetMapping("/getby/{id}")
    public ResponseEntity<ListingPropertyDto> getPropertyById(@PathVariable("id") long id){
    return new ResponseEntity<>(listingPropertyService.getPropertyById(id),HttpStatus.OK);
}

    //http://localhost:8080/api/property/update/id
    @PutMapping("/update/{propertyId}")
    public ResponseEntity<ListingPropertyDto> updateProperty(@PathVariable Long propertyId, @RequestBody ListingPropertyDto listingPropertyDto){

        ListingPropertyDto updatedPropertyDto= listingPropertyService.updateProperty(propertyId,listingPropertyDto);
        return new ResponseEntity<>(updatedPropertyDto,HttpStatus.OK);
    }

    //http://localhost:8080/api/property/id
    @DeleteMapping("/{propertyId}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long propertyId){
        listingPropertyService.deleteProperty(propertyId);
        return new ResponseEntity<>("Property Successfully deleted",HttpStatus.OK);

    }
}
