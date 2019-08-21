package com.hwagain.system.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@TableName("om_order_list")
public class OmOrderList implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
	@TableField("order_id")
	private Long orderId;
    /**
     * 订餐时间-就餐的时间
     */
	@TableField("fd_date")
	private Date fdDate;
    /**
     * 用餐类型-早中晚夜
     */
	@TableField("meal_type")
	private String mealType;
    /**
     * 订餐类型: DINGC-订餐,DIANC-点餐,ZHAOD-招待点餐,WEIX-维修人员点餐
     */
	@TableField("from_type")
	private String fromType;
    /**
     * 就餐用户
     */
	@TableField("dinner_user_name")
	private String dinnerUserName;
    /**
     * 就餐用户ID(待定-招待点餐是多个人一起, 存招待点餐人员表id)
     */
	@TableField("dinner_user_id")
	private String dinnerUserId;
    /**
     * 下单用户
     */
	@TableField("user_name")
	private String userName;
    /**
     * 下单用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 金额
     */
	private BigDecimal amount;
    /**
     * 支付状态
     */
	@TableField("pay_status")
	private Integer payStatus;
    /**
     * 退款状态
     */
	@TableField("refund_status")
	private Integer refundStatus;
    /**
     * 取餐状态
     */
	@TableField("dinner_status")
	private Integer dinnerStatus;
    /**
     * 备注
     */
	private String remark;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 创建人
     */
	@TableField("creator_id")
	private String creatorId;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 最后更新人
     */
	@TableField("last_update_id")
	private String lastUpdateId;
    /**
     * 最后更新时间
     */
	@TableField("last_update_date")
	private Date lastUpdateDate;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getFdDate() {
		return fdDate;
	}

	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getDinnerUserName() {
		return dinnerUserName;
	}

	public void setDinnerUserName(String dinnerUserName) {
		this.dinnerUserName = dinnerUserName;
	}

	public String getDinnerUserId() {
		return dinnerUserId;
	}

	public void setDinnerUserId(String dinnerUserId) {
		this.dinnerUserId = dinnerUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getDinnerStatus() {
		return dinnerStatus;
	}

	public void setDinnerStatus(Integer dinnerStatus) {
		this.dinnerStatus = dinnerStatus;
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

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

}
