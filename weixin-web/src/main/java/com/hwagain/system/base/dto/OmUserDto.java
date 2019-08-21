package com.hwagain.system.base.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author mon
 * @since 2019-08-09
 */
public class OmUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 手机号
     */
	private String phone;
    /**
     * 用户ID(对接系统id)
     */
	private Long userId;
    /**
     * 用户code(对接系统code)
     */
	private String userCode;
    /**
     * 用户名称
     */
	private String userName;
    /**
     * 用户类型(内部人员0, 外部人员1)
     */
	private String type;
	/**
     * 用户类型(性别:M男,W女)
     */
	private String sex;
    /**
     * 注册时间
     */
	private Date registerDate;
    /**
     * 外部人员类型
     */
	private Integer exoType;
    /**
     * 外部人员类型
     */
	private String exoTypeName;
    /**
     * 就餐点-部门ID
     */
	private String deptId;
    /**
     * 就餐点-部门名称
     */
	private String deptName;
    /**
     * 就餐点-PS车间ID
     */
	private String workshipId;
    /**
     * 就餐点-PS车间名称
     */
	private String workship;
	/**
     * PS岗位ID
     */
	private String positionId;
    /**
     * PS岗位
     */
	private String position;
    /**
     * 备注
     */
	private String remark;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 最后更新人
     */
	private String lastUpdateId;
    /**
     * 最后更新时间
     */
	private Date lastUpdateDate;

	/**
	 * 手机验证码
	 */
	private String vcode;
	/**
	 * 
	 */
	private String accessToken;

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getExoType() {
		return exoType;
	}

	public void setExoType(Integer exoType) {
		this.exoType = exoType;
	}

	public String getExoTypeName() {
		return exoTypeName;
	}

	public void setExoTypeName(String exoTypeName) {
		this.exoTypeName = exoTypeName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getWorkshipId() {
		return workshipId;
	}

	public void setWorkshipId(String workshipId) {
		this.workshipId = workshipId;
	}

	public String getWorkship() {
		return workship;
	}

	public void setWorkship(String workship) {
		this.workship = workship;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
