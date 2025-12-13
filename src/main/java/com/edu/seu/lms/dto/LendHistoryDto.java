package com.edu.seu.lms.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class LendHistoryDto {
        Long studentId;
        Long bookId;

}
