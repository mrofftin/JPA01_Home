package co.mr.web.jpatest.repository;

import co.mr.web.jpatest.entity.VisitBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

// insert : save(엔티티객체)
// select : findById(키 타입), getOne(키 타입)
// update : save(엔티티객체)
// delete : deleteById(키 타입), delete(엔티티 객체)




@SpringBootTest
class VisitRepositoryTest {
    @Autowired
    VisitRepository visitRepository;

    @Test
    public void testClass() {
        System.out.println(visitRepository.getClass().getName());
    }

//    @Builder 어노테이션
//    해당 클래스에 자동으로 빌더를 추가해준다
//    빌더란?
//    디자인 패턴 중 하나인 빌더 패턴Builder pattern을 의미한다. 객체의 생성 방법과 표현 방법을 분리하는 것을 의미한다.
//
//    Member customer = Member.build()
//            .name("홍길동")
//            .age(30)
//            .build(); // 마지막에 build()를 이용해서 객체를 생성
//위와 같은 스타일의 자바 코딩을 의미


    // 범위를 지정하는 메소드 rangeClosed(1, 100)는 끝에 포함, range는 포함하지 않음.
    @Test // 저장테스트
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            VisitBook visitBook = VisitBook.builder().visitMemo("Test..." + i).build();
            visitRepository.save(visitBook);
        });
    }

    @Test
    public void testSelect() {
        Long vno = 100L;

        Optional<VisitBook> resultById = visitRepository.findById(vno);

        System.out.println("====================");
        if (resultById.isPresent()) {
            VisitBook visitBook = resultById.get();
            System.out.println(visitBook);
        }
    }

    @Test
    public void testUpdate() {
        VisitBook update_text = VisitBook.builder().vno(100L).visitMemo("Update Text").build();
        System.out.println(visitRepository.save(update_text));
    }

//    @Test
//    public void testDelete() {
//        Long vno = 90L;
//
//        visitRepository.deleteById(vno);
//    }

    // Spring Data JPA를 이용해서 페이지 처리할 때는 반드시 0부터 시작
    // 페이지 처리는 Pageble인터페이스를 이용(페이지 처리에 필요한 정보를 전달하는 용도)
    // 실제 객체를 생성할 때는 PageRequest객체를 사용한다. new를 이용하지 않고 of를 이용해서 처리


    @Test
    public void testPageDefault() {
        // 1페이지에 데이터 10개를 가져오기 위해 0, 10 파라미터 설정
        Pageable pageable = PageRequest.of(0, 10);

        Page<VisitBook> result = visitRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("=====================");
        // 전체 페이지
        System.out.println("Total Page : " + result.getTotalPages());
        // 전체 데이터 개수
        System.out.println("Total Count : " + result.getTotalElements());
        // 현재 페이지 번호
        System.out.println("Page Number : " + result.getNumber());
        // 페이지당 데이터 개수
        System.out.println("Page Size : " + result.getSize());
        // 다음 페이지 존재 여부
        System.out.println("has Next Page? : "+result.hasNext());
        // 시작페이지(0) 여부
        System.out.println("first page? : "+result.isFirst());

        System.out.println("=====================");
        // getContent()는 List<엔티티타입>으로 처리
        // get()은 Stream<엔티티타압>으로 반환
        for (VisitBook visitBook : result.getContent()) {
            System.out.println(visitBook);
        }
    }

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("vno").descending();
        Sort sort2 = Sort.by("visitMemo").ascending();
        Sort sortAll = sort1.and(sort2); // and를 이용한 연결

//        PageRequest pageRequest = PageRequest.of(0, 10, sort1);
        Pageable pageable = PageRequest.of(0, 10, sortAll);


        Page<VisitBook> result = visitRepository.findAll(pageable);

        // get() : Stream<엔티티 타입 >반환
        result.get().forEach(visitBook -> {
            System.out.println(visitBook);
        });
    }

    @Test
    public void testQueryMethod() {

        List<VisitBook> list = visitRepository.findByVnoBetweenOrderByVnoDesc(30L, 40L);

        for (VisitBook visitBook : list) {
            System.out.println(visitBook);
        }
    }

    @Test
    public void testQueryMethodWithPageable(){
        List<VisitBook> list = visitRepository.findByVnoBetweenOrderByVnoDesc(30L, 40L);

        Pageable pageable = PageRequest.of(0, 20, Sort.by("vno").descending());

        Page<VisitBook> result = visitRepository.findByVnoBetween(10L, 50L, pageable);

        result.get().forEach(visitBook -> System.out.println(visitBook));

    }

    @Test
    @Commit
    @Transactional
    public void testDeleteQueryMethods() {
        visitRepository.deleteVisitBookByVnoLessThan(10L);
    }




}