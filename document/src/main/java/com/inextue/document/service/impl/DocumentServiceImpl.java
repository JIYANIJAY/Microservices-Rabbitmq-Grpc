package com.inextue.document.service.impl;

import com.devProblems.UserProto;
import com.devProblems.UserResponse;
import com.devProblems.UserServiceGrpc;
import com.gbs.common.dto.UserDTO;
import com.inextue.document.service.DocumentService;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @GrpcClient("grpc-document-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public InputStreamResource getAllUsers() throws IOException {
        UserResponse getAllUsers = userServiceBlockingStub.getUsers(null);
        List<UserDTO> userDTOS = new ArrayList<>();

        for (UserProto userProto : getAllUsers.getUserResponseList()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userProto.getId());
            userDTO.setFirstName(userProto.getFirstName());
            userDTO.setLastName(userProto.getLastName());
            userDTO.setEmail(userProto.getEmail());
            userDTOS.add(userDTO);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVWriter csvWriter = new CSVWriter(new PrintWriter(outputStream))) {
            csvWriter.writeAll(() -> userDTOS.stream().map(userDTO -> new String[]{userDTO.getId().toString(),
                    userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail()}).iterator());
        } catch (Exception e) {
            log.error("Error while writing csv file: {}", e.getMessage());
        }
        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    @Override
    public List<UserDTO> getAllUsersUsingGRPC() {
        UserResponse getAllUsers = userServiceBlockingStub.getUsers(null);
        List<UserDTO> userDTOS = new ArrayList<>();

        for (UserProto userProto : getAllUsers.getUserResponseList()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userProto.getId());
            userDTO.setUuid(userProto.getUuid());
            userDTO.setPhone(userProto.getPhone());
            userDTO.setFirstName(userProto.getFirstName());
            userDTO.setLastName(userProto.getLastName());
            userDTO.setEmail(userProto.getEmail());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
