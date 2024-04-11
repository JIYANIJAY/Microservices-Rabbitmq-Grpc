package com.inextue.document.controller;


import com.gbs.common.web.ApiResponse;
import com.inextue.document.service.DocumentService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/document/api/v1")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    private final RestTemplate restTemplate;


    @GetMapping("/all-user")
    public ResponseEntity<Resource> getAllUserIfo() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=users.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(documentService.getAllUsers());
    }

    @GetMapping("/all-user-rest")
    public ResponseEntity<ApiResponse> getAllUserIfoRest() {

        List contects = this.restTemplate.getForObject("http://user-service/user/api/v1/all-user", List.class);

        return new ResponseEntity<>(new ApiResponse(LocalDateTime.now(),
                HttpStatus.OK, "register user successfully", contects), HttpStatus.OK);
    }


    @GetMapping("/all-user-grpc")
    public ResponseEntity<ApiResponse> getAllUserIfoGrpc() {
        return new ResponseEntity<>(new ApiResponse(LocalDateTime.now(),
                HttpStatus.OK, "register user successfully", documentService.getAllUsersUsingGRPC()), HttpStatus.OK);
    }
}
