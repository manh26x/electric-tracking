package com.mike.electrictracking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Tags Found")
public class TagsNotFoundException extends RuntimeException{
    private String message;
}
