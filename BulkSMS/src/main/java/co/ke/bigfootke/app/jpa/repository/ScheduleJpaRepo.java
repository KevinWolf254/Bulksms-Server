package co.ke.bigfootke.app.jpa.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.ke.bigfootke.app.jpa.entities.Schedule;

public interface ScheduleJpaRepo extends JpaRepository<Schedule, Long>, 
											PagingAndSortingRepository<Schedule,Long>{

	public Schedule findByTitle(String title);
	
	public Schedule findByDate(Date date);
	
	public Page<Schedule> findAll(Pageable page);
	
	@Transactional
    Page<Schedule> findByGroupsGroupIdOrderByDate(Long groupId, Pageable pageable);
	
	@Transactional
	List<Schedule> findByGroupsGroupIdOrderByDate(Long groupId);
}
