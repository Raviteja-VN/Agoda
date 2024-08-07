package com.Agoda.Agoda.app.ServiceImpl;

import com.Agoda.Agoda.app.Entity.Hotel;
import com.Agoda.Agoda.app.Entity.ListingProperty;
import com.Agoda.Agoda.app.Exception.ResourceNotFoundException;
import com.Agoda.Agoda.app.Payload.HotelDto;
import com.Agoda.Agoda.app.Payload.ListingPropertyDto;
import com.Agoda.Agoda.app.Repository.HotelRepository;
import com.Agoda.Agoda.app.Repository.ListingPropertyRepository;
import com.Agoda.Agoda.app.Service.ListingPropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingPropertyServiceImpl implements ListingPropertyService {
    @Autowired
    private  final ListingPropertyRepository listingPropertyRepository;
    @Autowired
    private final ModelMapper modelMapper;
    private final String uploadDirectory = "src/main/resources/static/Agoda_images/";

    public ListingPropertyServiceImpl(ListingPropertyRepository listingPropertyRepository, ModelMapper modelMapper) {
        this.listingPropertyRepository = listingPropertyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ListingPropertyDto createProperty(ListingPropertyDto listingPropertyDto) {

        ListingProperty listingProperty = mapToEntity(listingPropertyDto);

        MultipartFile propertyImage = listingPropertyDto.getPropertyImage();
        if (propertyImage != null && !propertyImage.isEmpty()) {
            String fileName = savePropertyImage(propertyImage);
            listingProperty.setPropertyPicture(fileName);
        }

        ListingProperty savedProperty = listingPropertyRepository.save(listingProperty);
        listingProperty.setId(savedProperty.getId());
        return mapToDto(savedProperty);
    }
    @Override
    public Page<ListingPropertyDto> getProperty(Pageable pageable) {
        Page<ListingProperty> propertyPage= listingPropertyRepository.findAll(pageable);
        List<ListingPropertyDto> propertyDtos= propertyPage.stream().map(this::mapToDto).collect(Collectors.toList());
        return new PageImpl<>(propertyDtos,pageable,propertyPage.getTotalElements());
    }

    @Override
    public ListingPropertyDto getPropertyById(long id) {
        ListingProperty listingProperty = listingPropertyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Property", "Id", id)
        );
        return mapToDto(listingProperty);
    }

    @Override
    public ListingPropertyDto updateProperty(Long propertyId, ListingPropertyDto listingPropertyDto) {
        ListingProperty listingProperty = listingPropertyRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property", "Id", propertyId)
        );
        listingProperty.setPropertyType(listingPropertyDto.getPropertyType());
        listingProperty.setOwnedBy(listingPropertyDto.getOwnedBy());
        listingProperty.setCity(listingPropertyDto.getCity());
        listingProperty.setTotalRooms(listingPropertyDto.getTotalRooms());
        listingProperty.setRoomType(listingPropertyDto.getRoomType());
        listingProperty.setPrice(listingPropertyDto.getPrice());

        ListingProperty updatedProperty = listingPropertyRepository.save(listingProperty);
        return mapToDto(updatedProperty);
    }

    @Override
    public void deleteProperty(Long propertyId) {
         listingPropertyRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property", "Id", propertyId)
        );
        listingPropertyRepository.deleteById(propertyId);

    }


    private String savePropertyImage(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String uniqueFileName = baseFileName + "_" + System.currentTimeMillis() + fileExtension;
            Path path = Paths.get(uploadDirectory + uniqueFileName);
            Files.write(path, bytes);

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save Property image", e);
        }

    }
    ListingPropertyDto mapToDto (ListingProperty listingProperty){
        ListingPropertyDto listingPropertyDto = modelMapper.map(listingProperty, ListingPropertyDto.class);
        return listingPropertyDto;
    }
    ListingProperty mapToEntity (ListingPropertyDto listingPropertyDto){
        ListingProperty listingProperty = modelMapper.map(listingPropertyDto, ListingProperty.class);
        return listingProperty;
    }
}
