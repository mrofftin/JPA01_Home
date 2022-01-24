package co.mr.web.jpatest.entity;

import lombok.*;

import javax.persistence.*;

//-------------------
//객체 - 테이블 맵핑 : @Entity, @Table
//필드 - 컬럼의 팹핑 : @Column
//기본키의 맵핑 : @Id
//조인 맵핑 : @ManyToOne, @JoinColumn
//-------------------

// 해당 클래스가 Entity를 위한 클래스라는 것을 명시,
// 이클래스의 인스턴스들은 JPA에의해 관리되는 엔티티 객체가 됨
// 옵션에 따라 자동으로 테이블을 생성, 이클래스의 멤버변수에 따라 자동으로 컬럼이 생성
@Entity
// @Entity와 같이 사용하는 어노테이션으로 테이블생성에 필요한 정보를 담고 있음
// 생성되는 테이블이름이 tbl_visit으로 생성되며, name이 없는 경우 클래스명과 동일한 명으로 테이블 생성
// 일반적으로 name 속성을 생략하고 사용하는 경우가 많음.
@Table(name = "tbl_visit")
@ToString
@Getter
@Builder // 객체 생성시 사용
@AllArgsConstructor
@NoArgsConstructor
public class VisitBook {
    // @Entity가 붙은 클래스는 primary key 설정을 위한 필드에는 @Id를 붙여야 한다.
    @Id
    // 자동으로 생성되는 번호를 사용하기위한 어노테이션
    // 키생성전략을 지정하여 자동으로 번호를 생성하는데 IDENTITY를 주로 사용
    // AUTO: 하이버네이트가 키생성을 결정, SEQUENCE: 데이터베이스의 sequence를 이용해서 키생성
    // TABLE: 키생성 전용테이블을 생성해서 키생성
    // IDENTITY는 데이터베이스에 따라 키생성을 결정(MySQL, MariaDB는 auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vno;

    // 추가적인 필드(컬럼)가 필요한 경우 사용
    // 다양한 속성 지정 가능 nullable, name, length
    @Column(length = 200, nullable = false)
    private String visitMemo;

    // properties 설정 후 실행하면 데이터베이스에 테이블이 생성됨
}
