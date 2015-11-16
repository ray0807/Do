package com.dobest.ray.raydo.constants;

/**
 * url路径
 * 
 * @author wangl01
 * 
 */
public class Urls {

	/**
	 * 根URL
	 */
	public static final String ROOT_URL = "http://202.100.85.84:8686/xbrc-ws-mobile/";

	/**
	 * 版本更新 get方式
	 */
	public static final String VERSION_UPDATE_URL = ROOT_URL
			+ "version/headversion";
	/**
	 * 字典值 使用get方式请求
	 */
	public static final String DICTIONARY_VAR = ROOT_URL + "generic/dictionary";
	/**
	 * 地域列表
	 */
	public static final String LOCATIONS_URL = ROOT_URL + "generic/locations";
	/**
	 * 职位列表
	 */
	public static final String POSITION_URL = ROOT_URL
			+ "generic/positiontypes";

	/**
	 * 启动图片
	 */
	public static final String WELCOME_IMG = ROOT_URL + "generic/welcomeimg";

	/**
	 * 获取短信验证码
	 */
	public static final String GET_MSGCODE = ROOT_URL + "generic/sendvalidate";

	/**
	 * 验证短信验证码
	 */
	public static final String VERIFY_CODE = ROOT_URL + "generic/checkvalidate";

	/**
	 * 用户注册
	 */
	public static final String USER_REGIST = ROOT_URL + "user/reg/person";
	/**
	 * 资讯详情
	 */
	public static final String NEW_INFO_DETAILS = ROOT_URL
			+ "generic/getinformation";
	/**
	 * 资讯列表
	 */
	public static final String NEW_INFO_LIST = ROOT_URL
			+ "generic/informations";

	/**
	 * 用户登录
	 */
	public static final String USER_LOGIN = ROOT_URL + "/user/login";

	/**
	 * 教育经历
	 */
	public static final String EDUCATION_LIST = ROOT_URL
			+ "resume/education/list";

	/**
	 * 工作经历
	 */
	public static final String WOREXPREIENCE_LIST = ROOT_URL
			+ "resume/workexperience/list";

	/**
	 * 获取字典值
	 */
	public static final String GET_DICTIONARY = ROOT_URL + "generic/dictionary";

	/**
	 * 个人端获取招聘会列表
	 */
	public static final String GET_JOBFAIRLIST = ROOT_URL + "jobfair/recently";

	/**
	 * 个人端获取招聘会详情
	 */
	public static final String GET_JOBFAIRDETAIL = ROOT_URL + "jobfair/detail";
	/**
	 * 积分查询
	 */
	public static final String INTEGRAL_DETAIL = ROOT_URL + "integral/detail";
	/**
	 * 修改密码
	 */
	public static final String CHANGE_PASSWORD = ROOT_URL + "user/changpwd";

	/**
	 * 刷新简历
	 */
	public static final String REFRESH_RESUMENT = ROOT_URL + "resume/refresh";
	/**
	 * 意见反馈
	 */
	public static final String ADVICE_FEEDBACK = ROOT_URL + "generic/feedback";
	/**
	 * 我的投递
	 */
	public static final String MY_DELIVERY = ROOT_URL + "person/job/record";
	/**
	 * 黑名单列表
	 */
	public static final String BLACK_LIST = ROOT_URL
			+ "person/blacklist/record";
	/**
	 * 新增黑名单
	 */
	public static final String add_to_BLACK_LIST = ROOT_URL
			+ "person/blacklist/add";
	/**
	 * 删除黑名单
	 */
	public static final String DELETE_TO_BLACK_LIST = ROOT_URL
			+ "person/blacklist/delete";
	/**
	 * 退出登录
	 */
	public static final String USER_LOGOUT = ROOT_URL + "user/logout";
	/**
	 * 职位收索
	 */
	public static final String POSITION_SEARCH_LIST = ROOT_URL
			+ "person/position/list";

	/**
	 * 设置简历的公开类型
	 */
	public static final String SET_RESUMENTTYPE = ROOT_URL
			+ "resume/changeopendegree";

	/**
	 * 个人端获取招聘会的职位列表
	 */
	public static final String GET_JOBFAIRJOBLIST = ROOT_URL
			+ "person/jobfair/position/list";

	/**
	 * 根据楼层获取不同的展位详情
	 */
	public static final String GET_BOOTHDETAIL = ROOT_URL + "jobfair/boothrels";

	/**
	 * 获取简历
	 */
	public static final String GET_RESUMENT = ROOT_URL + "resume/baseinfo/show";

	/**
	 * 获取个人招聘会公司列表
	 */
	public static final String GET_USERJOBFAIRCOMPANY_LIST = ROOT_URL
			+ "jobfair/partakes";

	/**
	 * 预约面试
	 */
	public static final String BOOKINGTHE_INTERVIEW = ROOT_URL
			+ "person/jobfair/position/subscribe";

	/**
	 * 修改简历
	 */
	public static final String MODIFY_RESUME = ROOT_URL
			+ "resume/baseinfo/edit";

	/**
	 * 添加教育经历
	 */
	public static final String ADD_EDUCATIONITEM = ROOT_URL
			+ "resume/education/edit";

	/**
	 * 我的收藏
	 */
	public static final String MY_COLLECT = ROOT_URL + "person/collect/list";
	/**
	 * 职位详情
	 */
	public static final String POSITION_DETAILS = ROOT_URL
			+ "person/position/detail";
	/**
	 * 企业详情
	 */
	public static final String COMPANY_DETAILS = ROOT_URL
			+ "person/position/comdetail";

	/**
	 * 职位收藏
	 */
	public static final String POSITION_COLLECT = ROOT_URL
			+ "person/position/collect";
	public static final String DELETE_MESSAGE = ROOT_URL + "message/delete";
	/**
	 * 职位举报
	 */
	public static final String POSITION_REPORT = ROOT_URL
			+ "person/position/report";
	/**
	 * 面试邀请
	 */
	public static final String INTERVIEW_INVITE = ROOT_URL
			+ "person/interview/record";
	/**
	 * 删除面试邀请
	 */
	public static final String DELETE_INTERVIEW_INVITE = ROOT_URL
			+ "person/interview/delete";
	/**
	 * 查看面试邀请
	 */
	public static final String READ_INTERVIEW_INVITE = ROOT_URL
			+ "person/interview/read";
	/**
	 * 消息记录
	 */
	public static final String MESSAGE_RECORD = ROOT_URL + "message/record";
	public static final String MESSAGE_READ = ROOT_URL + "message/read";

	/**
	 * 添加工作经历
	 */
	public static final String ADD_WORKITEM = ROOT_URL
			+ "resume/workexperience/edit";
	/**
	 * 申请职位
	 */
	public static final String POSITION_APPLICATION = ROOT_URL
			+ "person/position/delivery";

	/**
	 * 获取行业列表
	 */
	public static final String GET_INDUSTRY = ROOT_URL + "generic/industrys";

	/**
	 * 获取简历详情
	 */
	public static final String GET_RESUMENTINFO = ROOT_URL
			+ "company/resume/info";

	/**
	 * 上传简历头像
	 */
	public static final String POST_RESUMENTIMG = ROOT_URL
			+ "resume/photoupload";

	// ===============企业端=============================
	/**
	 * 申请职位
	 */
	public static final String RESUME_SEARCH = ROOT_URL
			+ "company/resume/search";
	/**
	 * 我的简历库
	 */
	public static final String RESUME_STORE = ROOT_URL
			+ "company/resume/deliver";
	/**
	 * 我的简历库 下载过的简历
	 */
	public static final String DOWNLOAD_RESUME = ROOT_URL
			+ "company/resume/download";
	/**
	 * 删除简历库
	 */
	public static final String REMOVE_RESUME_STORE = ROOT_URL
			+ "company/resume/rmv";
	/**
	 * 简历收藏夹
	 */
	public static final String RESUME_FAVORITE = ROOT_URL
			+ "company/resume/favorite";
	/**
	 * 查看营业执照
	 */
	public static final String SCAN_LICENSE = ROOT_URL
			+ "company/position/scanlicense";
	/**
	 * 已发送的邀请
	 */
	public static final String DELIVERIED_INTERVIEW = ROOT_URL
			+ "company/resume/interview";

	/**
	 * 已发送的邀请详情
	 */
	public static final String INTERVIEW_DETAILS = ROOT_URL
			+ "company/resume/interview/info";
	/**
	 * 发送的邀请
	 */
	public static final String INVITED_PERSON = ROOT_URL
			+ "company/resume/invited";
	/**
	 * 发送的邀请
	 */
	public static final String COLLECT_RESUME = ROOT_URL
			+ "company/resume/collect";
	/**
	 * 产品详情
	 */
	public static final String PRODUCT_DETAILS = ROOT_URL
			+ "order/product/detail";
	/**
	 * 产品详情单价
	 */
	public static final String PRODUCT_PRICERULES = ROOT_URL
			+ "order/product/pricerules";
	/**
	 * 产品购买
	 */
	public static final String PRODUCT_BUY = ROOT_URL + "order/product/buy";
	/**
	 * 订单详情
	 */
	public static final String ORDER_DETAILS = ROOT_URL + "order/order/detail";
	/**
	 * 未付款订单列表
	 */
	public static final String ORDER_LISTS = ROOT_URL + "order/order/list";

	/**
	 * 企业注册
	 */
	public static final String COMPANY_REGIST = ROOT_URL + "user/reg/company";

	/**
	 * 重置密码
	 */
	public static final String RESET_PSW = ROOT_URL + "user/resetpwd";
	/**
	 * 企业发送邀请
	 */
	public static final String RESUME_INVITED = ROOT_URL + "resume/invited";
	/**
	 * 企业查看联系方式
	 */
	public static final String RESUME_CONTACT = ROOT_URL
			+ "company/resume/contact";
	/**
	 * 已发送的职位列表
	 */
	public static final String POSITION_LIST = ROOT_URL
			+ "company/position/list";
	/**
	 * 已发送的有效职位职位列表
	 */
	public static final String POSITION_INVALIDATE = ROOT_URL
			+ "company/position/select";
	/**
	 * 发布兼职 选择部门
	 */
	public static final String DPMENT_LIST = ROOT_URL
			+ "company/position/dpment";

	/**
	 * 上传营业执照
	 */
	public static final String UPLOADLICENCE = ROOT_URL
			+ "company/position/uploadlicence";

	/**
	 * 删除工作经验
	 */
	public static final String DELETE_WORKLIST = ROOT_URL
			+ "resume/workexperience/delete";

	/**
	 * 删除教育经历
	 */
	public static final String DELETE_EDUCATIONLIST = ROOT_URL
			+ "resume/education/delete";

	/**
	 * 我的展位
	 */
	public static final String GET_MYBOOTH = ROOT_URL
			+ "company/jobfair/reserveboothlist";

	/**
	 * 展位详情
	 */
	public static final String GET_BOOTHINFO = ROOT_URL
			+ "company/jobfair/booths/detail";

	/**
	 * 查询消息条数
	 */
	public static final String MESSAGE_COUNT = ROOT_URL + "message/newmsgcount";
	/**
	 * 摇一摇
	 */
	public static final String SHAKE_RESULT = ROOT_URL
			+ "person/position/shake";
	public static final String DELETE_DILIVERY = ROOT_URL + "person/job/delete";
	/**
	 * 企业发布兼职
	 */
	public static final String COMPANY_RELEASE_JOB = ROOT_URL
			+ "company/position/input";
	public static final String DELETE_POSITION = ROOT_URL
			+ "company/position/delete";
	public static final String COMPANY_COMPLETE_INFO = ROOT_URL
			+ "user/get/company/info";
	public static final String COMPANY_CHANGE_INFO = ROOT_URL
			+ "user/save/company/info";

	/**
	 * 委托申请
	 */
	public static final String COMPANY_ENTRUST = ROOT_URL
			+ "company/position/entrust";
	/**
	 * 取消订单
	 */
	public static final String CANCEL_ORDER = ROOT_URL + "order/order/cancel";
	/**
	 * 产品列表
	 */
	public static final String PRODUCT_LIST = ROOT_URL + "order/product/list";
	/**
	 * 产品列表
	 */
	public static final String PRODUCT_ORDER_SERVICES = ROOT_URL
			+ "order/product/productservices";
	/**
	 * 获取主页信息数
	 */
	public static final String GET_MAIN_MARK = ROOT_URL + "generic/getmark";
	/**
	 * 取消收藏
	 */
	public static final String CANCLE_COLLECT = ROOT_URL
			+ "person/collect/delete";

	/**
	 * 第三方登陆
	 */
	public static final String ThirdPartLogin = ROOT_URL + "oauth/login";

	/**
	 * 第三方登陆检查
	 */
	public static final String THIRDPART_CHECK = ROOT_URL + "oauth/check";

	/**
	 * 预定展位
	 */
	public static final String YUDINGZHANWEI = ROOT_URL + "jobfair/reserve";

	/**
	 * 检查是否绑定
	 */
	public static final String CHECK_OAUTH = ROOT_URL + "oauth/check";
	/**
	 * 绑定
	 */
	public static final String BIND_OAUTH = ROOT_URL + "oauth/binding";
	/**
	 * 解除绑定
	 */
	public static final String UNBIND_OAUTH = ROOT_URL + "oauth/unbinding";
	/**
	 * 查询资料是否完整
	 */
	public static final String IS_PERFECT = ROOT_URL + "user/isperfect";

	/**   
	* 第三方注册
	*/ 
	public static final String THIRDPART_REGIST = ROOT_URL + "oauth/reg";

}
