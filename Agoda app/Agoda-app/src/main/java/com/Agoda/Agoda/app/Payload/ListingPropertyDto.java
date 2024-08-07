package com.Agoda.Agoda.app.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ListingPropertyDto {
    private Long id;
    private String propertyType;
    private String ownedBy;
    private String totalRooms;
    private String roomType;
    private String price;
    private String city;
    private String propertyPicture;
    private MultipartFile propertyImage;
}
