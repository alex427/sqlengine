package alex.learn.common.utils;

import java.io.Serializable;


public class JSONResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 错误 APP_COOKIE_NOT_EXISTS
	public static final String APP_COOKIE_NOT_EXISTS = "app_cookie_not_exists";
	// 接口请求参数PARAMS不存在
	public static final String OPENAPI_PARAMS_NOT_EXIST = "接口请求参数params不存在";
	// 接口AES解密失败
	public static final String OPENAPI_PARAMS_AES_DESENCRYPT_FAILED = "接口AES解密失败";
	// 接口访问频次过快
	public static final String ACCESS_TOO_FAST = "访问频次过快";

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private T data;
    
    private int statusCode;
    
    private String message;

	private boolean flag;

    public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public JSONResult() {
        super();
    }

	public JSONResult(T data, int statusCode, String message, boolean flag) {
		super();
		this.data = data;
		this.statusCode = statusCode;
		this.message = message;
		this.flag = flag;
	}

	public JSONResult(int statusCode, String message){
    	super();
		this.statusCode = statusCode;
		this.message = message;
		this.flag = true;
	}

	/**
	 * @Title: invokeSuccess
	 * @Description: 调用成功
	 * @param t
	 */
	public void invokeSuccess(T t){
		this.statusCode = MessageCode.INVOLK_SUCCESS;
		this.message = MessageCode.INVOLK_SUCCESS_STR;
		this.flag = true;
		this.data = t;
	}

	/**
	 * @Title: invokeFailed
	 * @Description: 调用失败
	 */
	public void invokeFailed(){
		this.statusCode = MessageCode.INVOLK_FAILED;
		this.message = MessageCode.INVOLK_FAILED_STR;
		this.flag = false;
	}

	/**
	 * @Title: invokeFailed
	 * @Description: 调用失败
	 */
	public void invokeFailed(int code, String message, boolean flag){
		this.statusCode = code;
		this.message= message;
		this.flag = flag;
	}

}
