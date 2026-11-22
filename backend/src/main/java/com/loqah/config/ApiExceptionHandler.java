package com.loqah.config;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(NoSuchElementException.class)
 ResponseEntity<?> notFound(NoSuchElementException ex){return ResponseEntity.status(404).body(Map.of("message","Resource not found"));}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<?> validation(MethodArgumentNotValidException ex){
  var errors=new LinkedHashMap<String,String>();
  ex.getBindingResult().getFieldErrors().forEach(e->errors.put(e.getField(),e.getDefaultMessage()));
  return ResponseEntity.badRequest().body(Map.of("message","Validation failed","errors",errors));
 }
}