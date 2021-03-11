package alex.learn.common.utils;

public class MessageCode {

	///////////////////////
	// API调用消息返回编码
	///////////////////////
	// 接口调用成功
	public static int INVOLK_SUCCESS = 100100;
	public static String INVOLK_SUCCESS_STR = "接口调用成功";
	// 接口调用失败
	public static int INVOLK_FAILED = 100200;
	public static String INVOLK_FAILED_STR = "接口内部异常";
	// 解析参数错误：参数为空
	public static int NULL_PARAMS = 100300;
	public static String NULL_PARAMS_STR = "参数为空";
	// 数据库异常
	public static int DB_EXCEPTION = 100400;
	public static String DB_EXCEPTION_STR = "数据库异常";
	// 参数格式不对
	public static int WRONG_PARAMS = 100500;
	public static String WRONG_PARAMS_STR = "参数格式不正确";
	// 解析参数错误：解析参数Annotation错误
	public static int WRONG_PARAMS_ANNOTATION = 100501;
	public static String WRONG_PARAMS_ANNOTATION_STR = "解析参数Annotation错误";
	// 解析参数错误：无法获取参数属性
	public static int WRONG_PARAMS_KEY = 100502;
	public static String WRONG_PARAMS_KEY_STR = "无法获取参数属性%s";
	// 解析参数错误：参数值为空
	public static int WRONG_PARAMS_VALUE = 100503;
	public static String WRONG_PARAMS_VALUE_STR = "参数%s的值为空";

	// 校验参数成功
	public static int WRONG_PARAMS_SUCCESS = 100600;
	public static String WRONG_PARAMS_SUCCESS_STR = "参数校验成功";
	//校验参数错误：必填参数不能为空
	public static int WRONG_PARAMS_NOT_EMPTY = 100601;
	public static String WRONG_PARAMS_NOT_EMPTY_STR = "必填参数不能为空";
	//校验参数错误：参数格式不对
	public static int WRONG_PARAMS_FORMAT = 100602;
	public static String WRONG_PARAMS_FORMAT_STR = "参数格式不对";
	//校验参数错误：参数长度超出范围
	public static int WRONG_PARAMS_LENGTH = 100603;
	public static String WRONG_PARAMS_LENGTH_STR = "参数长度超出范围";
	//校验参数错误：名称不能有特殊字符
	public static int WRONG_PARAMS_CHARACTER= 100604;
	public static String WRONG_PARAMS_CHARACTER_STR = "名称不能有特殊字符";
	//校验参数错误：无效的IP地址
	public static int WRONG_PARAMS_IP= 100605;
	public static String WRONG_PARAMS_IP_STR = "无效的IP地址";
	//校验参数错误：无效的端口号
	public static int WRONG_PARAMS_PORT= 100606;
	public static String WRONG_PARAMS_PORT_STR = "无效的端口号";
	//校验参数错误：结束时间需大于开始时间
	public static int WRONG_PARAMS_TIME= 100607;
	public static String WRONG_PARAMS_TIME_STR = "结束时间需大于开始时间";

	//校验参数错误：任务执行频率不能为空
	public static int WRONG_PARAMS_FREQUENCY= 100608;
	public static String WRONG_PARAMS_FREQUENCY_STR = "任务执行频率不能为空";
	//校验参数错误：任务执行间隔不能为空
	public static int WRONG_PARAMS_INTERVAL= 100609;
	public static String WRONG_PARAMS_INTERVAL_STR = "任务执行间隔不能为空";
	//数据源类型选择错误
	public static int WRONG_DATA_SOURCE_TYPE= 100610;
	public static String WRONG_DATA_SOURCE_TYPE_STR = "系统暂不支持该数据源类型";

	//错误的手机号格式
	public static int WRONG_MOBILE_PHONE = 100611;
	public static String WRONG_MOBILE_PHONE_STR = "系统暂不支持该数据源类型";

	//校验参数错误：结束时间需大于系统当前时间
	public static int WRONG_PARAMS_ENDTIME= 100612;
	public static String WRONG_PARAMS_ENDTIME_STR = "任务结束时间需大于系统当前时间";

	// 用户未登录
	public static int INVOLK_LOGINOUT = 100700;
	public static String INVOLK_LOGINOUT_STR = "请登录后再进行操作";


	// 非法正则表达式
	public static int INVOLK_REGEX = 100800;
	public static String INVOLK_REGEX_STR = "非法的正则表达式";


}
