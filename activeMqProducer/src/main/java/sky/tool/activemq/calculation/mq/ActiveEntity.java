package sky.tool.activemq.calculation.mq;

import java.io.Serializable;
import java.util.Calendar;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sky.tool.activemq.calculation.entity.HtywRedisEntity;

@Data
@AllArgsConstructor
public class ActiveEntity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8608342131942004171L;
	
	private JSONObject jo;
	
	private HtywRedisEntity entity;
}
