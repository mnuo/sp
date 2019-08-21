package com.hwagain.system.recipe.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 菜单表-普通菜
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
public class OmRecipeDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 就餐时间(2019-08-01)
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date fdDate;
    /**
     * 菜品区域类型(广西菜,江西菜)
     */
	private String areaType;
    /**
     * 就餐时间段类型(早,中,晚,18点夜,2点夜)
     */
	private String mealType;
	
	private String mealTypeName;
    /**
     * 菜品类型(早餐/夜餐, 荤,素)
     */
	private Integer type;
    /**
     * 菜品类型(早餐/夜餐, 荤,素)
     */
	private String typeName;
    /**
     * 品种1-id
     */
	private Long dishe1Id;
    /**
     * 品种1
     */
	private String dishe1Name;
    /**
     * 品种2-id
     */
	private Long dishe2Id;
    /**
     * 品种2
     */
	private String dishe2Name;
    /**
     * 品种3-id
     */
	private Long dishe3Id;
    /**
     * 品种3
     */
	private String dishe3Name;
    /**
     * 品种4-id
     */
	private Long dishe4Id;
    /**
     * 品种4
     */
	private String dishe4Name;
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
	private String creatorId;
    /**
     * 创建时间
     */
	private Date createDate;
    /**
     * 最后更新人
     */
	private String lastUpdateId;
    /**
     * 最后更新时间
     */
	private Date lastUpdateDate;

	private String oaCode;
	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Date getFdDate() {
		return fdDate;
	}

	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getDishe1Id() {
		return dishe1Id;
	}

	public void setDishe1Id(Long dishe1Id) {
		this.dishe1Id = dishe1Id;
	}

	public String getDishe1Name() {
		return dishe1Name;
	}

	public void setDishe1Name(String dishe1Name) {
		this.dishe1Name = dishe1Name;
	}

	public Long getDishe2Id() {
		return dishe2Id;
	}

	public void setDishe2Id(Long dishe2Id) {
		this.dishe2Id = dishe2Id;
	}

	public String getDishe2Name() {
		return dishe2Name;
	}

	public void setDishe2Name(String dishe2Name) {
		this.dishe2Name = dishe2Name;
	}

	public Long getDishe3Id() {
		return dishe3Id;
	}

	public void setDishe3Id(Long dishe3Id) {
		this.dishe3Id = dishe3Id;
	}

	public String getDishe3Name() {
		return dishe3Name;
	}

	public void setDishe3Name(String dishe3Name) {
		this.dishe3Name = dishe3Name;
	}

	public Long getDishe4Id() {
		return dishe4Id;
	}

	public void setDishe4Id(Long dishe4Id) {
		this.dishe4Id = dishe4Id;
	}

	public String getDishe4Name() {
		return dishe4Name;
	}

	public void setDishe4Name(String dishe4Name) {
		this.dishe4Name = dishe4Name;
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

	public String getMealTypeName() {
		return mealTypeName;
	}

	public void setMealTypeName(String mealTypeName) {
		this.mealTypeName = mealTypeName;
	}

	public String getOaCode() {
		return oaCode;
	}

	public void setOaCode(String oaCode) {
		this.oaCode = oaCode;
	}
}
