package com.hwagain.system.order.entity;

import java.io.Serializable;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@TableName("om_order_user")
public class OmOrderUser implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
	@TableField("user_id")
	private Long userId;
	private String phone;
	@TableField("ps_user_id")
	private Long psUserId;
	@TableField("user_code")
	private String userCode;
	@TableField("user_name")
	private String userName;
	@TableField("dept_id")
	private String deptId;
	@TableField("dept_name")
	private String deptName;
	@TableField("workship_id")
	private String workshipId;
	private String workship;
	@TableField("position_id")
	private String positionId;
	private String position;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getPsUserId() {
		return psUserId;
	}

	public void setPsUserId(Long psUserId) {
		this.psUserId = psUserId;
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

}
