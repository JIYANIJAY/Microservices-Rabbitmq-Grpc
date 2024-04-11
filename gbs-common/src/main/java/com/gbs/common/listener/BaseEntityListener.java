package com.gbs.common.listener;

import com.gbs.common.helper.BaseEntity;

import javax.persistence.PrePersist;
import java.util.UUID;

public class BaseEntityListener {
    @PrePersist
    public void prePersist(BaseEntity baseEntity) {
        if (baseEntity.getId() == null) {
            baseEntity.setUuid(UUID.randomUUID().toString());
        }
    }
}
