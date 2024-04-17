package com.project.wishilist.config;


import com.project.wishilist.model.dto.response.ErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity handleBindException(BindException ex) {
        String errorMessage = (ex.getFieldError() != null)? ex.getFieldError().getDefaultMessage() : "Erro na requisição";
            return new ResponseEntity(new ErrorResponse<String>(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity handleBadRequestException(DuplicateKeyException ex) {
        return new ResponseEntity(new ErrorResponse<String>(ex.getMessage()) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResponseEntity handleDuplicateException(DuplicateKeyException ex) {
        return new ResponseEntity(new ErrorResponse<String>(ex.getMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public ResponseEntity handleNumberFormatException(NumberFormatException ex) {
        return new ResponseEntity(new ErrorResponse<String>(ex.getMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception ex) {
        return new ResponseEntity(new ErrorResponse<String>(ex.getMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity(new ErrorResponse<String>(ex.getMessage()) , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    @ResponseBody
    public ResponseEntity handleInconsistentData(IncorrectResultSizeDataAccessException ex) {
        return new ResponseEntity(new ErrorResponse<String>("Dados informados inconsistentes") , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
