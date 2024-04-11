package com.inexture.service.impl;

import com.devProblems.UserProto;
import com.devProblems.UserResponse;
import com.devProblems.UserServiceGrpc;
import com.inexture.entity.User;
import com.inexture.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
@AllArgsConstructor
@Slf4j
public class UserGRPCServiceImpl extends UserServiceGrpc.UserServiceImplBase{

    private final UserRepository userRepository;

    /**
     * Get all users
     */
    @Override
    public void getUsers(UserProto request, StreamObserver<UserResponse> responseObserver) {
        List<User> users = userRepository.findAll();
        List<UserProto> userProtos = new ArrayList<>();
        for (User user : users) {
            UserProto userProto = UserProto.newBuilder()
                    .setId(user.getId())
                    .setUuid(user.getUuid())
                    .setPhone(user.getPhone())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail())
                    .build();
            userProtos.add(userProto);
        }
        UserResponse userResponse = UserResponse.newBuilder().addAllUserResponse(userProtos).build();
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }
}
