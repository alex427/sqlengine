package alex.learn.common.stmt.exceptions;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class IlegalFunctionException extends Exception{

    public IlegalFunctionException(){
        super();
    }

    public IlegalFunctionException(String msg){
        super(msg);
    }
}
