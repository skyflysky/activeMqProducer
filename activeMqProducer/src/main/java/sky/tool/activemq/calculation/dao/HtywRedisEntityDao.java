package sky.tool.activemq.calculation.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sky.tool.activemq.calculation.constant.HtywType;
import sky.tool.activemq.calculation.entity.HtywRedisEntity;

public interface HtywRedisEntityDao extends JpaRepository<HtywRedisEntity, Long> , JpaSpecificationExecutor<HtywRedisEntity>
{
	@Modifying
	@Query(value = "UPDATE HtywRedisEntity SET active=false")
	void deactive();
	
	@Modifying
	@Query(value = "UPDATE HtywRedisEntity SET lastModify=:modify , active=true WHERE collId IN :ids")
	void init(@Param(value = "modify")Calendar currTime , @Param(value = "ids") List<Long> idList);
	
	HtywRedisEntity findByCollId(Long CollId);
	
	List<HtywRedisEntity> findByTypeAndActive(HtywType type , Boolean active);
}
