package com.hwagain.system.recipe.dto;

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
public class RecipeRpsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String areaType;
    
    private Long disheId;
    private String disheName;
    
    private Long meatDisheId;
    private String meatDisheName;
    
    
    private String mealType;
    private String mealTypeName;
    
    private Integer type;
    private String typeName;
    
    private Integer meatType;
    private String meatTypeName;
    
    private BigDecimal price;
    private BigDecimal meatPrice;
    
    public RecipeRpsDto() {
	}
	public RecipeRpsDto(Long disheId, String disheName, Long meatDisheId, String meatDisheName, String areaType, String mealType, String mealTypeName, Integer type, String typeName, Integer meatType, String meatTypeName) {
		this.disheId = disheId;
		this.disheName = disheName;
		this.meatDisheName = meatDisheName;
		this.meatDisheId = meatDisheId;
		this.areaType = areaType;
		this.mealType = mealType;
		this.mealTypeName = mealTypeName;
		this.type= type;
		this.typeName = typeName;
		this.meatType = meatType;
		this.meatTypeName = meatTypeName;
	}
	public RecipeRpsDto(int flag, Long disheId, String disheName, String areaType, 
			String mealType, String mealTypeName, Integer type, String typeName, BigDecimal price) {
		if(flag == 0){//素菜
			this.disheId = disheId;
			this.disheName = disheName;
			this.type= type;
			this.typeName = typeName;
			this.price = price;
		}else if(flag == 1){
			this.meatDisheId = disheId;
			this.meatDisheName = disheName;
			this.meatType = type;
			this.meatTypeName = typeName;
			this.meatPrice = price;
		}
		this.areaType = areaType;
		this.mealType = mealType;
		this.mealTypeName = mealTypeName;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
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
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public String getMealTypeName() {
		return mealTypeName;
	}
	public void setMealTypeName(String mealTypeName) {
		this.mealTypeName = mealTypeName;
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
	public Long getMeatDisheId() {
		return meatDisheId;
	}
	public void setMeatDisheId(Long meatDisheId) {
		this.meatDisheId = meatDisheId;
	}
	public String getMeatDisheName() {
		return meatDisheName;
	}
	public void setMeatDisheName(String meatDisheName) {
		this.meatDisheName = meatDisheName;
	}
	public Integer getMeatType() {
		return meatType;
	}
	public void setMeatType(Integer meatType) {
		this.meatType = meatType;
	}
	public String getMeatTypeName() {
		return meatTypeName;
	}
	public void setMeatTypeName(String meatTypeName) {
		this.meatTypeName = meatTypeName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getMeatPrice() {
		return meatPrice;
	}
	public void setMeatPrice(BigDecimal meatPrice) {
		this.meatPrice = meatPrice;
	}
}
