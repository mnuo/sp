package com.hwagain.system.base.entity;

import java.io.Serializable;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
@TableName("om_data_dict")
public class OmDataDict implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 字典类型
     */
	@TableField("type_name")
	private String typeName;
    /**
     * 项目编号
     */
	@TableField("item_no")
	private Integer itemNo;
    /**
     * 项目名称
     */
	@TableField("item_name")
	private String itemName;
    /**
     * 项目代号
     */
	@TableField("item_code")
	private String itemCode;
    /**
     * 归类分组名称
     */
	@TableField("group_name")
	private String groupName;
    /**
     * 归类分组代号
     */
	@TableField("group_code")
	private String groupCode;
    /**
     * 排序号
     */
	@TableField("order_index")
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
