package vttp.persistence.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import vttp.persistence.config.RecordNotFoundException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({ RecordNotFoundException.class })
    public ModelAndView handleRecordNotFound(HttpServletRequest req, RecordNotFoundException ex) {
        final ModelAndView mav = new ModelAndView("record_not_found.html");
        mav.addObject("tableName", ex.getTableName());
        mav.addObject("primaryKey", ex.getTableName());
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
