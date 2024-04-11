package com.inexture.service.impl;

import com.gbs.common.constants.RabbitMqConstants;
import com.gbs.common.dto.AddressDTO;
import com.gbs.common.dto.UserDTO;
import com.inexture.entity.Address;
import com.inexture.entity.User;
import com.inexture.repository.UserRepository;
import com.inexture.service.UserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;


    @Override
    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = convertToEntity(userDTO);
        Map<String, Boolean> map = new HashMap<>();
        map.put("address", true);
        try {
            User saveUser = userRepository.save(user);
            sendNotification(convertToDTO(saveUser, map));
            return convertToDTO(saveUser, map);

        } catch (Exception e) {
            log.error("Error while saving user: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("address", false);
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> {
            userDTOS.add(convertToDTO(user, map));
        });
        return userDTOS;
    }


    private void sendNotification(UserDTO userDTO) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.USER_ROUTING_KEY, userDTO);
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();

        List<Address> addresses = new ArrayList<>();
        List<AddressDTO> userAddresses = userDTO.getUserAddresses();
        for (AddressDTO addressDTO : userAddresses) {
            Address address = new Address();
            BeanUtils.copyProperties(addressDTO, address);
            address.setUser(user);
            addresses.add(address);
        }
        user.setUserAddresses(addresses);
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    private UserDTO convertToDTO(User user, Map<String, Boolean> map) {
        UserDTO userDTO = new UserDTO();

        if (map.get("address")) {
            List<AddressDTO> addressDTOs = user.getUserAddresses().stream()
                    .map(address -> {
                        AddressDTO addressDTO = new AddressDTO();
                        BeanUtils.copyProperties(address, addressDTO, "id", "uuid", "createdAt", "createdBy", "updatedAt", "updatedBy");
                        return addressDTO;
                    })
                    .collect(Collectors.toList());
            userDTO.setUserAddresses(addressDTOs);
        }

        BeanUtils.copyProperties(user, userDTO, "createdAt", "createdBy", "updatedAt", "updatedBy", "password");
        return userDTO;
    }

}
