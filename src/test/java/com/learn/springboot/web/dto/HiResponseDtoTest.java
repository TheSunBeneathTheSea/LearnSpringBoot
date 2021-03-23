package com.learn.springboot.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HiResponseDtoTest {

    @Test
    public void test_Lombok(){
        String name = "test";
        int amount = 1000;

        HiResponseDto dto = new HiResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
