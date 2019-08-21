package com.hwagain.system.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 退款表
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@TableName("om_refund")
public class OmRefund implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 支付表id
     */
	@TableField("pay_id")
	private Long payId;
    /**
     * 订单ID
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 订单列表id
     */
	@TableField("order_detail_id")
	private Long orderDetailId;
    /**
     * 退款完成时间
     */
	@TableField("refund_complate_time")
	private Date refundComplateTime;
    /**
     * 退款时间
     */
	@TableField("refund_time")
	private Date refundTime;
    /**
     * 退款金额
     */
	@TableField("refund_amount")
	private BigDecimal refundAmount;
    /**
     * 退款状态
     */
	@TableField("refund_status")
	private Integer refundStatus;
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

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Date getRefundComplateTime() {
		return refundComplateTime;
	}

	public void setRefundComplateTime(Date refundComplateTime) {
		this.refundComplateTime = refundComplateTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
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
