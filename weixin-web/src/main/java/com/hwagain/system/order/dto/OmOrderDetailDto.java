package com.hwagain.system.order.dto;

import java.math.BigDecimal;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
public class OmOrderDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
	private Long orderListId;
	private String areaType;
	private Integer type;
	private Long disheId;
	private String disheName;
	private BigDecimal price;
	private Integer number;
	private BigDecimal amount;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Long getOrderListId() {
		return orderListId;
	}

	public void setOrderListId(Long orderListId) {
		this.orderListId = orderListId;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getDisheId() {
		return disheId;
	}

	public void setDisheId(Long disheId) {
		this.disheId = disheId;
	}

	public String getDisheName() {
		return disheName;
	}

	public void setDisheName(String disheName) {
		this.disheName = disheName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
