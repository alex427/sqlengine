package alex.learn.common.stmt.exceptions;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class IlegalParamTypeException extends Exception{

    public IlegalParamTypeException(){
        super();
    }

    public IlegalParamTypeException(String msg){
        super(msg);
    }
}
