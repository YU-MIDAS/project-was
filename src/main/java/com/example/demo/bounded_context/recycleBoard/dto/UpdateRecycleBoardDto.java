package com.example.demo.bounded_context.recycleBoard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRecycleBoardDto {
    private String title;

    private String content;

    private String shareTarget;

    private String location;
}
