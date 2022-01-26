package co.mr.web.jpatest.repository;

import co.mr.web.jpatest.entity.VisitBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* Spring Data JPA는 JpaRepository를 상속하는 인터페이스 선언만으로
모든 DB연동처리를 SQL없이 처리할 수 있게된다. 즉, 실제 동작시에 내부적으로 해당 인터페이스에 맞는
코드를 자동으로 생성해준다는 의미이다.
*/

//JpaRepository 사용시에는 엔티티의 타입정보와 @Id의 타입정보를 지정해준다.
// Spring Data JPA는 이렇게 인터페이스 선언만으로 자동으로 Bean을 등록된다.
// (내부적으로 스프링이 인터페이스 타입에 맞는 객체를 생성해서 빈으로 등록한다.)
public interface VisitRepository extends JpaRepository<VisitBook, Long> {

//    List<VisitBook> findByVnoBetweenOrderByVnoDesc(Long from, Long to);

    List<VisitBook> findByVnoBetweenOrderByVnoDesc(Long from, Long to);
    Page<VisitBook> findByVnoBetween(Long from, Long to, Pageable pageable);

    // num보다 작은 데이터를 삭제한다는 의미
    void deleteVisitBookByVnoLessThan(Long num);
}
