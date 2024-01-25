package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entiry.MemberNicknameHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberNicknameHistoryRepository{

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final private String TABLE = "MemberNicknameHistory";

    static final private RowMapper<MemberNicknameHistoryDto> rowMapper= (ResultSet resultSet , int rowNum) -> MemberNicknameHistoryDto.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("id"))
            .nickname(resultSet.getString("nickname"))
            .createdAt(resultSet.getObject("createdAt",LocalDate.class).atStartOfDay())
            .build();



    public List<MemberNicknameHistoryDto> findAllByMemberId(Long memberId) {
        /**
         *  select * from member where id = :id
         **/

        var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId",TABLE);
        var params = new MapSqlParameterSource()
                .addValue("memberId",memberId);
        return namedParameterJdbcTemplate.query(sql,params,rowMapper);
    }

    public MemberNicknameHistoryDto save(MemberNicknameHistoryDto history) {
        /**
         *  member id 를 보고 갱신 또는 삽입을 정함
         *  변함없는 id를 담아서 변환한다 ,
         **/
        if (history.getId() == null) {
            return insert(history);
        }
        throw new UnsupportedOperationException("히스토리 테이블 갱신을 지원하지않습니다.");
    }

    private MemberNicknameHistoryDto insert(MemberNicknameHistoryDto history) {
        /**
         *  member id 를 보고 갱신 또는 삽입을 정함
         *  변함없는 id를 담아서 변환한다 ,
         **/
        SimpleJdbcInsert simleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(history);
        var id = simleJdbcInsert.executeAndReturnKey(params).longValue();
        return MemberNicknameHistoryDto
                .builder()
                .id(id)
                .memberId(history.getMemberId())
                .nickname(history.getNickname())
                .createdAt(history.getCreatedAt())
                .build();
    }


}

