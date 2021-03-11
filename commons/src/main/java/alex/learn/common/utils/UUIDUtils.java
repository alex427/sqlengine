package alex.learn.common.utils;

import java.util.UUID;


public class UUIDUtils {
	
	/**
	 * 
	 * @Title: getUUID
	 * @Description: 获取UUID
	 * @return   String
	 * @author yunwang2
	 * @date 2015-8-19 下午2:32:47
	 * @throws
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
