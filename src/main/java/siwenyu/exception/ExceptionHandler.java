package siwenyu.exception;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import siwenyu.pojo.Result;

/**
 * 操作失败
 */
@RestControllerAdvice
public class ExceptionHandler {

   @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");
    }
}
