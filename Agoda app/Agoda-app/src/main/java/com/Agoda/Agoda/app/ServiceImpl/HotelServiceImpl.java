package com.Agoda.Agoda.app.ServiceImpl;

import com.Agoda.Agoda.app.Entity.CreateAccount;
import com.Agoda.Agoda.app.Entity.Hotel;
import com.Agoda.Agoda.app.Exception.ResourceNotFoundException;
import com.Agoda.Agoda.app.Payload.HotelDto;
import com.Agoda.Agoda.app.Repository.HotelRepository;
import com.Agoda.Agoda.app.Service.HotelService;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private  final HotelRepository hotelRepository;
    @Autowired
    private final ModelMapper modelMapper;

    private final String uploadDirectory = "src/main/resources/static/Agoda_images/";


    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        Hotel hotel = mapToEntity(hotelDto);

        MultipartFile hotelImage = hotelDto.getHotelImage();
        if (hotelImage != null && !hotelImage.isEmpty()) {
            String fileName = saveHotelImage(hotelImage);
            hotel.setHotelPicture(fileName);
        }

        Hotel savedHotel = hotelRepository.save(hotel);
        hotel.setId(savedHotel.getId());
        return mapToDto(savedHotel);
    }

    @Override
    public Page<HotelDto> getHotel(Pageable pageable) {
        Page<Hotel> hotelPage= hotelRepository.findAll(pageable);
        List<HotelDto> hotelDtos= hotelPage.stream().map(this::mapToDto).collect(Collectors.toList());
        return new PageImpl<>(hotelDtos,pageable,hotelPage.getTotalElements());
    }

    @Override
    public HotelDto getHotelById(long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel", "Id", id)
        );
        return mapToDto(hotel);
    }

    @Override
    public List<HotelDto> searchHotels(String location, String roomType, String price) {
        List<Hotel> hotels =   hotelRepository.findByLocationAndRoomTypeAndPrice(location,roomType,price);
        return hotels.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public HotelDto updateHotel(Long hotelId, HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new ResourceNotFoundException("Hotel", "Id", hotelId)
        );
        hotel.setHotelName(hotelDto.getHotelName());
        hotel.setLocation(hotelDto.getLocation());
        hotel.setRoomType(hotelDto.getRoomType());
        hotel.setPersonsPerRoom(hotelDto.getPersonsPerRoom());
        hotel.setPrice(hotelDto.getPrice());
        hotel.setPinCode(hotelDto.getPinCode());

        Hotel updatedHotel = hotelRepository.save(hotel);
        return mapToDto(updatedHotel);

    }

    @Override
    public void deleteHotel(Long hotelId) {
        hotelRepository.findById(hotelId).orElseThrow(
                ()->new ResourceNotFoundException("Hotel", "Id", hotelId)
        );
        hotelRepository.deleteById(hotelId);

    }

    private String saveHotelImage(MultipartFile file) {
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
            throw new RuntimeException("Failed to save Hotel image", e);
        }

    }
    HotelDto mapToDto (Hotel hotel){
        HotelDto hotelDto = modelMapper.map(hotel, HotelDto.class);
        return hotelDto;
    }
    Hotel mapToEntity (HotelDto hotelDto){
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        return hotel;
    }
}
