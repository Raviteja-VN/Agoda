package com.Agoda.Agoda.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Table(name="listing_propertys")
@AllArgsConstructor
@NoArgsConstructor
public class ListingProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="property_type",nullable = false)
    private String propertyType;
    @Column(name="owned_by",nullable = false)
    private String ownedBy;
    @Column(name="total_rooms",nullable = false)
    private String totalRooms;
    @Column(name="room_type",nullable = false)
    private String roomType;
    @Column(name="price",nullable = false)
    private String price;
    @Column(name="city",nullable = false)
    private String city;
    private String propertyPicture;
    @Column(name = "property_image")
    private String propertyImage;


}
