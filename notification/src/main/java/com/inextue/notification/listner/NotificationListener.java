package com.inextue.notification.listner;

import com.gbs.common.constants.RabbitMqConstants;
import com.gbs.common.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class NotificationListener {

    @RabbitListener(queues = RabbitMqConstants.USER_QUEUE)
    public void listenNotification(UserDTO userDTO) {
        log.info("Sending email to {}", userDTO.getEmail());
    }
}
