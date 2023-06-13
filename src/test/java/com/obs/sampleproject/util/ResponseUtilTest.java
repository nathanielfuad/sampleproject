package com.obs.sampleproject.util;

import com.obs.sampleproject.constants.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ResponseUtilTest {
    private ResponseUtil responseUtil;

    @BeforeEach
    void initTestCase() {
        responseUtil = new ResponseUtil();
    }

    @Test
    void whenGenerate_ThenReturnResponseEntity() {
        assertEquals(ResponseEntity.class, responseUtil.generate(ErrorCode.SUCCESS,"").getClass());
    }
}
