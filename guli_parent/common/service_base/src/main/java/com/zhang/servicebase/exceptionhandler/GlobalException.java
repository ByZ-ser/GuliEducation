package com.zhang.servicebase.exceptionhandler;

import com.zhang.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e)
    {
        if( e instanceof GuliException)
        {
            log.error(e.getMessage());
            return R.error().code(((GuliException) e).getCode()).message(e.getMessage());
        }
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("全局异常");
    }
}
