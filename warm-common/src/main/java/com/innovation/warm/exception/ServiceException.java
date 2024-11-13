package com.innovation.warm.exception;



import com.innovation.warm.enumeration.ResultCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: LeaseException
 * PackageName: com.enquan.lease.common.exception
 * Description:
 *
 * @Author: 32782
 * @Date: 10/8/2024 下午5:21
 * @Version: 1.0
 */
@Data
@NoArgsConstructor

public class ServiceException extends RuntimeException{

    private Integer code;

    public ServiceException(Integer code, String message){
        super(message);
        this.code = code;
    }
    public ServiceException(String message){
        super(message);
    }
    public ServiceException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        code = resultCodeEnum.getCode();
    }

}
