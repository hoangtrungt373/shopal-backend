package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Announcement;

/**
 *
 * @author ttg
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer>, AnnouncementRepositoryCustom {
}
