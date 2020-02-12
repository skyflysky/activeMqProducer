package sky.tool.activemq.producer.entity;

import java.io.Serializable;
import java.util.Calendar;

public class ActiveEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2730190367670433482L;
	
	private Long id;
	
	private Boolean active;
	
	private String description;
	
	private Calendar createTime;
	
	private int hashcode;

	@Override
	public int hashCode()
	{
		Integer i = id.hashCode() - active.hashCode() + description.hashCode() - createTime.hashCode();
		return i.hashCode();
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Calendar getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Calendar createTime)
	{
		this.createTime = createTime;
	}

	public int getHashcode()
	{
		return hashcode;
	}

	public void setHashcode(int hashcode)
	{
		this.hashcode = hashcode;
	}

	public ActiveEntity(Long id , String description)
	{
		super();
		this.id = id;
		this.active = id % 2 == 0;
		this.description = description;
		this.createTime = Calendar.getInstance();
		this.hashcode = hashCode();
	}
}
