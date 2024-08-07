package com.Agoda.Agoda.app.Service;

import com.Agoda.Agoda.app.Payload.ListingPropertyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingPropertyService {
    ListingPropertyDto createProperty(ListingPropertyDto listingPropertyDto);

    Page<ListingPropertyDto> getProperty(Pageable pageable);

    ListingPropertyDto updateProperty(Long propertyId, ListingPropertyDto listingPropertyDto);

    void deleteProperty(Long propertyId);

    ListingPropertyDto getPropertyById(long id);
}
