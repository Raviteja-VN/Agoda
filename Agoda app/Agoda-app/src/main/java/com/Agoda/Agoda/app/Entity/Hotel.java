package com.Agoda.Agoda.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;
@Entity
@Data
@Table(name="hotels")
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="hotel_name",nullable = false)
    private String hotelName;
    @Column(name="location",nullable = false)
    private String location;
    @Column(name="room_type",nullable = false)
    private String roomType;
    @Column(name="persons_per_room",nullable = false)
    @Size(min=1,max=3,message = "Should not exceeds 3 per room")
    private String personsPerRoom;
    @Column(name="price",nullable = false)
    private String price;
    @Column(name="pin_code",nullable = false)
    @Size(min=6,max=8,message = "PinCode must be between 6 and 8 character ")
    private String pinCode;
    private String hotelPicture;
    @Column(name = "hotel_image")
    private String hotelImage;

}
