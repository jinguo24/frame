package com.simple.common.mybatis.entity;

import java.util.Date;


/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c)  2015-3-12</p>
 * <p>Company: lenovo</p>
 * @author <a href="mailto:soldiers888@gmail.com">panbing.wu</a>
 * @version 1.0  2015-3-12 下午2:47:08
 */
public abstract class BaseEntity implements java.io.Serializable
{
	private static final long serialVersionUID = 3277750218358510732L;
	
	protected Long id;
	
	protected Date createTime;
	
	protected Date updateTime;

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	
	
}
