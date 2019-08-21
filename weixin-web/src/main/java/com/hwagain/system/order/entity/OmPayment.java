package com.hwagain.system.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 支付表
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@TableName("om_payment")
public class OmPayment implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 订单ID
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 支付类型: 1,余额; 2,微信; 3, 支付宝等
     */
	@TableField("pay_type")
	private Integer payType;
    /**
     * 支付时间
     */
	@TableField("pay_time")
	private Date payTime;
    /**
     * 支付完成时间
     */
	@TableField("pay_complate_time")
	private Date payComplateTime;
    /**
     * 支付金额
     */
	@TableField("pay_amount")
	private BigDecimal payAmount;
    /**
     * 支付状态: (1,未支付, 2,已支付, 3,支付失败)
     */
	@TableField("pay_status")
	private Integer payStatus;
    /**
     * 预留字段1
     */
	private String reference1;
    /**
     * 预留字段2
     */
	private String reference2;
    /**
     * 预留字段3
     */
	private String reference3;
    /**
     * 预留字段4
     */
	private String reference4;
    /**
     * 预留字段5
     */
	private String reference5;


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

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getPayComplateTime() {
		return payComplateTime;
	}

	public void setPayComplateTime(Date payComplateTime) {
		this.payComplateTime = payComplateTime;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getReference1() {
		return reference1;
	}

	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}

	public String getReference2() {
		return reference2;
	}

	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}

	public String getReference3() {
		return reference3;
	}

	public void setReference3(String reference3) {
		this.reference3 = reference3;
	}

	public String getReference4() {
		return reference4;
	}

	public void setReference4(String reference4) {
		this.reference4 = reference4;
	}

	public String getReference5() {
		return reference5;
	}

	public void setReference5(String reference5) {
		this.reference5 = reference5;
	}

}
