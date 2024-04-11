package com.inextue.document.service;

import com.gbs.common.dto.UserDTO;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    InputStreamResource getAllUsers() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    List<UserDTO> getAllUsersUsingGRPC();

}
