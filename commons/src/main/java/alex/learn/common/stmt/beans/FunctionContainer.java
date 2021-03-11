package alex.learn.common.stmt.beans;


import java.util.HashMap;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class FunctionContainer   {
    private static HashMap<String,String> funcmap = new HashMap<>();

    //TODO： 做成解析文件
    static {
       funcmap.put("SUM"," SUM");
       funcmap.put("COUNT"," COUNT");
       funcmap.put("MAX"," MAX");
       funcmap.put("MIN"," MIN");
       funcmap.put("ROUND"," ROUND");
       funcmap.put("TRUNC"," TRUNC");
       funcmap.put("SQRT"," SQRT");
       funcmap.put("CEIL"," CEIL");
       funcmap.put("FLOOR"," FLOOR");
       funcmap.put("CONCAT"," CONCAT");
       funcmap.put("SUBSTR"," SUBSTR");
       funcmap.put("TO_CHAR"," TO_CHAR");
       funcmap.put("TRIM"," TRIM");
       funcmap.put("TO_DATE"," TO_DATE");
    }

    public static String getFunc(String key){
        return  funcmap.containsKey(key)?funcmap.get(key):null;
    }

}