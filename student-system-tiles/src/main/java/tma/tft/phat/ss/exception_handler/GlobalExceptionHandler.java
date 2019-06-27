package tma.tft.phat.ss.exception_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = { Exception.class })
    public ModelAndView notFoundExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("errPage");
        modelAndView.addObject("error", "Unknown error");
        logger.error(e.getMessage(),e);
        return modelAndView;
    }

}
