package com.jun.common.exception;

/**
 * 
 * @Description service层保存抛出次异常
 * @author Guojun
 * @Date 2018年3月31日 下午3:11:06
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
