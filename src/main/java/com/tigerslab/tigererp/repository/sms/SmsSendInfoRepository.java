package com.tigerslab.tigererp.repository.sms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.sms.SendSMSInfo;

@Repository("smsSendInfoRepository")
public interface SmsSendInfoRepository extends JpaRepository<SendSMSInfo, Long> {

	@Query("select b from SendSMSInfo b where b.sendNumber like %?1% or b.id like %?1%")
	Page<SendSMSInfo> findAllBySortAndOrder(String sendNumber, Pageable pageable);

}
