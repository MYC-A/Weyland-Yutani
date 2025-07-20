package com.weyland.starter.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}", message = "Время должно быть в формате yyyy-MM-dd'T'HH:mm:ss")
    private String time;
}
