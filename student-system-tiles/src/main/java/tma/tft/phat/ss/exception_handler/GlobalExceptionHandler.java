package tma.tft.phat.ss.exception_handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public ModelAndView notFoundExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("errPage");
        modelAndView.addObject("error", "Unknown error");

        return modelAndView;
    }

}
