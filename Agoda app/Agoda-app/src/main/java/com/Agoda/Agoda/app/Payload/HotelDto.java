package com.Agoda.Agoda.app.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Long id;
    private String hotelName;
    private String location;
    private String roomType;
    private String personsPerRoom;
    private String price;
    private String pinCode;
    private String hotelPicture;
    private MultipartFile hotelImage;
}
