package com.weyland.synthetic_core_starter.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandDTO {

    @NotBlank(message = "Нужно указать описание")
    @Size(max = 1000, message = "Описание должно быть меньше 1000 символов")
    private String description;

    @NotNull(message = "Нужно указать приоритет")
    private Priority priority;

    @NotBlank(message = "Нужно указать автора")
    @Size(max = 100, message = "ФИО автора должно быть меньше 100 символов")
    private String author;

    @NotBlank(message = "Нужно указать время")
    private String time;
}
