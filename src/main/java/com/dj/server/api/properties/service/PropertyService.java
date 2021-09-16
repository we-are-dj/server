package com.dj.server.api.properties.service;

import com.dj.server.api.properties.entity.Property;
import com.dj.server.api.properties.repository.PropertyRepository;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.property.PropertyErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 자주 쓰이는 상수값들에 대한 전반적인 비즈니스 로직을 담당합니다.
 *
 * @author Informix
 * @created 2021-09-16
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    Property fetchProperty(String propValue) {
        return propertyRepository.findByPropValue(propValue)
                .orElseThrow(() -> new BizException(PropertyErrorCode.NOT_FOUND));
    }

    public boolean doesPropertyExist(String propValue) {
        return fetchProperty(propValue).getPropKey() != null;
    }
}
