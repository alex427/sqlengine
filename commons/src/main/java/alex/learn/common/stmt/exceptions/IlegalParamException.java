package alex.learn.common.stmt.exceptions;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class IlegalParamException extends Exception{

    public IlegalParamException(){
        super();
    }

    public IlegalParamException(String msg){
        super(msg);
    }
}
