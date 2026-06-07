package com.kushal.device_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kushal.device_service.dto.DeviceDto;
import com.kushal.device_service.entity.Device;
import com.kushal.device_service.exception.DeviceNotFoundException;
import com.kushal.device_service.repository.DeviceRepository;

@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceDto getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id " + id));
        return mapToDto(device);
    }

    public DeviceDto createDevice(DeviceDto input) {
        Device device = new Device();
        device.setName(input.name());
        device.setType(input.type());
        device.setLocation(input.location());
        device.setUserId(input.userId());

        final Device savedDevice = deviceRepository.save(device);
        return mapToDto(savedDevice);
    }

    public DeviceDto updateDevice(Long id, DeviceDto input) {
        Device existing = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id " + id));

        existing.setName(input.name());
        existing.setType(input.type());
        existing.setLocation(input.location());
        existing.setUserId(input.userId());

        final Device updatedDevice = deviceRepository.save(existing);
        return mapToDto(updatedDevice);
    }

    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new DeviceNotFoundException("Device not found with id " + id);
        }
        deviceRepository.deleteById(id);
    }

    public List<DeviceDto> getAllDevicesByUserId(Long userId) {
        List<Device> devices = deviceRepository.findAllByUserId(userId);
        return devices.stream()
                .map(this::mapToDto)
                .toList();
    }

    private DeviceDto mapToDto(Device device) {
        // Refactored to use the immutable Record Builder pattern
        return DeviceDto.builder()
                .id(device.getId())
                .name(device.getName())
                .type(device.getType())
                .location(device.getLocation())
                .userId(device.getUserId())
                .build();
    }

}
