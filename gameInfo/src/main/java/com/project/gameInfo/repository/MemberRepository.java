package com.project.gameInfo.repository;

import com.project.gameInfo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select count(m.id) from Member m where m.memberId = :id")
    Long duplicateId(String id);
}
