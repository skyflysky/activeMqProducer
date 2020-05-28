package sky.tool.activemq.calculation.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import sky.tool.activemq.calculation.constant.HtywType;

@Entity
@Table(indexes = {@Index(name="collId_indx", columnList = "collId") , @Index(name = "mosId_index" , columnList = "mosId")})
@Data
public class HtywRedisEntity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8023041683449030134L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 航天云网的设备Id
	 */
	@Column(nullable = false)
	private Long devId;
	
	/**
	 * 航天云网的测量定义Id ，与mosId一一对应
	 */
	@Column(nullable = false)
	private Long collId;
	
	/**
	 * 数据类型
	 */
	@Enumerated(EnumType.STRING)
	private HtywType type;
	
	/**
	 * mos里面的Id，
	 */
	@Column(nullable = true , columnDefinition = "Varchar(36) default 'sky'")
	private String mosId;
	
	/**
	 * 最后一次从航天云网redis拉下来的时间
	 */
	@Column(nullable = false)
	private Calendar lastModify;
	
	/**
	 * 这个数据的更新时间，单位：秒
	 */
	@Column(nullable = false)
	private Integer updateGap;
	
	/**
	 * 本次运行是否活跃
	 */
	@Column(nullable = false)
	private Boolean active;

	public HtywRedisEntity(Long devId , Long collId , HtywType type , String mosId , Calendar lastModify ,
			Integer updateGap , Boolean active)
	{
		super();
		this.devId = devId;
		this.collId = collId;
		this.type = type;
		this.mosId = mosId;
		this.lastModify = lastModify;
		this.updateGap = updateGap;
		this.active = active;
	}

	public HtywRedisEntity()
	{
		super();
	}
}
