package com.sockettcp.owneruser.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;



/**
 * 用户信息表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-07-16 12:08:28
 */
public class UserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//头像
	private String heardUrl;
	//邀请二维码
	private String QRCode;
	//用户名
	private String username;
	//性别：值为1时是男性，值为2时是女性，值为0时是未知
	private Integer sex;
	//出生年月
	private Date birthday;
	//最后登录时间
	private Date loginTime;
	//总使用时间
	private Long usedTime;
	//昵称
	private String nickname;
	//用户id
	private Long userId;
	//微信id
	private String openId;
	//密码
	private String password;
	//手机号
	private String phone;
	//真实姓名
	private String name;
	//注册时间
	private Date registerTime;
	//编辑时间
	private Date updateTime;
	//毕业院校
	private String graduateInstitutions;
	//职称
	private String professionName;
	//第一学历
	private String firstDegree;
	//最高学历
	private String highestEducation;
	//科室
	private String subject;
	//邮寄地址
	private String mailingAddress;
	//删除标志  0 未删除   1已删除
	private Integer deleteFlag;
	//注册来源
	private String registrationSource;
	//会员等级
	private Integer level;
	//审核状态(0未审核 1审核成功 2审核失败)
	private Integer auditStatus;
	//导出状态(0已导出 1未导出)
	private Integer exportStatus;
	//获得回报
	private String redound;
	//最终分成
	private String separateInto;
	//讲师级别
	private String teacherLevel;
	//问答金额
	private Double questionMoney;
	//登陆标识 0：用户 1：讲师
	private Integer userLoginType;
	//字符串生日
	private String birth;
	//字符串性别
	private String sexStr;
	//优惠券数量
	private Integer couponCount;
	//积分
	private Integer integral;
	//金币
	private Integer gold;
	//购买vip的时间
	private Date goumaiLevel;
	//头像
	private MultipartFile imgFile;
	
	public MultipartFile getImgFile() {
		return imgFile;
	}
	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public String getSexStr() {
		return sexStr;
	}
	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：头像
	 */
	public void setHeardUrl(String heardUrl) {
		this.heardUrl = heardUrl;
	}
	/**
	 * 获取：头像
	 */
	public String getHeardUrl() {
		return heardUrl;
	}
	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：性别：值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别：值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：出生年月
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：出生年月
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * 设置：最后登录时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * 获取：最后登录时间
	 */
	public Date getLoginTime() {
		return loginTime;
	}
	/**
	 * 设置：总使用时间
	 */
	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}
	/**
	 * 获取：总使用时间
	 */
	public Long getUsedTime() {
		return usedTime;
	}
	/**
	 * 设置：昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：微信id
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：微信id
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：真实姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：真实姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：注册时间
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	/**
	 * 获取：注册时间
	 */
	public Date getRegisterTime() {
		return registerTime;
	}
	/**
	 * 设置：编辑时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：编辑时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：毕业院校
	 */
	public void setGraduateInstitutions(String graduateInstitutions) {
		this.graduateInstitutions = graduateInstitutions;
	}
	/**
	 * 获取：毕业院校
	 */
	public String getGraduateInstitutions() {
		return graduateInstitutions;
	}
	/**
	 * 设置：职称
	 */
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}
	/**
	 * 获取：职称
	 */
	public String getProfessionName() {
		return professionName;
	}
	/**
	 * 设置：第一学历
	 */
	public void setFirstDegree(String firstDegree) {
		this.firstDegree = firstDegree;
	}
	/**
	 * 获取：第一学历
	 */
	public String getFirstDegree() {
		return firstDegree;
	}
	/**
	 * 设置：最高学历
	 */
	public void setHighestEducation(String highestEducation) {
		this.highestEducation = highestEducation;
	}
	/**
	 * 获取：最高学历
	 */
	public String getHighestEducation() {
		return highestEducation;
	}
	/**
	 * 设置：科室
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 获取：科室
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 设置：邮寄地址
	 */
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	/**
	 * 获取：邮寄地址
	 */
	public String getMailingAddress() {
		return mailingAddress;
	}
	/**
	 * 设置：删除标志  0 未删除   1已删除
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * 获取：删除标志  0 未删除   1已删除
	 */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * 设置：注册来源
	 */
	public void setRegistrationSource(String registrationSource) {
		this.registrationSource = registrationSource;
	}
	/**
	 * 获取：注册来源
	 */
	public String getRegistrationSource() {
		return registrationSource;
	}
	/**
	 * 设置：会员等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：会员等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：审核状态(0未审核 1审核成功 2审核失败)
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取：审核状态(0未审核 1审核成功 2审核失败)
	 */
	public Integer getAuditStatus() {
		return auditStatus;
	}
	/**
	 * 设置：导出状态(0已导出 1未导出)
	 */
	public void setExportStatus(Integer exportStatus) {
		this.exportStatus = exportStatus;
	}
	/**
	 * 获取：导出状态(0已导出 1未导出)
	 */
	public Integer getExportStatus() {
		return exportStatus;
	}
	/**
	 * 设置：获得回报
	 */
	public void setRedound(String redound) {
		this.redound = redound;
	}
	/**
	 * 获取：获得回报
	 */
	public String getRedound() {
		return redound;
	}
	/**
	 * 设置：最终分成
	 */
	public void setSeparateInto(String separateInto) {
		this.separateInto = separateInto;
	}
	/**
	 * 获取：最终分成
	 */
	public String getSeparateInto() {
		return separateInto;
	}
	/**
	 * 设置：讲师级别
	 */
	public void setTeacherLevel(String teacherLevel) {
		this.teacherLevel = teacherLevel;
	}
	/**
	 * 获取：讲师级别
	 */
	public String getTeacherLevel() {
		return teacherLevel;
	}
	/**
	 * 设置：问答金额
	 */
	public void setQuestionMoney(Double questionMoney) {
		this.questionMoney = questionMoney;
	}
	/**
	 * 获取：问答金额
	 */
	public Double getQuestionMoney() {
		return questionMoney;
	}
	/**
	 * 设置：登陆标识 0：用户 1：讲师
	 */
	public void setUserLoginType(Integer userLoginType) {
		this.userLoginType = userLoginType;
	}
	/**
	 * 获取：登陆标识 0：用户 1：讲师
	 */
	public Integer getUserLoginType() {
		return userLoginType;
	}
	
	public Date getGoumaiLevel() {
		return goumaiLevel;
	}
	public void setGoumaiLevel(Date goumaiLevel) {
		this.goumaiLevel = goumaiLevel;
	}
	
	public String getQRCode() {
		return QRCode;
	}
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}
	@Override
	public String toString() {
		return "UserDO [id=" + id + ", heardUrl=" + heardUrl + ", username=" + username + ", sex=" + sex + ", birthday="
				+ birthday + ", loginTime=" + loginTime + ", usedTime=" + usedTime + ", nickname=" + nickname
				+ ", userId=" + userId + ", openId=" + openId + ", password=" + password + ", phone=" + phone
				+ ", name=" + name + ", registerTime=" + registerTime + ", updateTime=" + updateTime
				+ ", graduateInstitutions=" + graduateInstitutions + ", professionName=" + professionName
				+ ", firstDegree=" + firstDegree + ", highestEducation=" + highestEducation + ", subject=" + subject
				+ ", mailingAddress=" + mailingAddress + ", deleteFlag=" + deleteFlag + ", registrationSource="
				+ registrationSource + ", level=" + level + ", auditStatus=" + auditStatus + ", exportStatus="
				+ exportStatus + ", redound=" + redound + ", separateInto=" + separateInto + ", teacherLevel="
				+ teacherLevel + ", questionMoney=" + questionMoney + ", userLoginType=" + userLoginType + "]";
	}
	
	
}
