package alex.learn.common.stmt.exceptions;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class IlegalValueException extends Exception{

    public IlegalValueException(){
        super();
    }

    public IlegalValueException(String msg){
        super(msg);
    }
}
