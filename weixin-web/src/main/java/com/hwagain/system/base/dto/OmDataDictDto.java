package com.hwagain.system.base.dto;


import java.io.Serializable;


/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
public class OmDataDictDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 字典类型
     */
	private String typeName;
    /**
     * 项目编号
     */
	private Integer itemNo;
    /**
     * 项目名称
     */
	private String itemName;
    /**
     * 项目代号
     */
	private String itemCode;
    /**
     * 归类分组名称
     */
	private String groupName;
    /**
     * 归类分组代号
     */
	private String groupCode;
    /**
     * 排序号
     */
	private Integer orderIndex;
    /**
     * 备注
     */
	private String remark;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
