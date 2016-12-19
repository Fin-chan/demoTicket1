package com.citicbank.dao;

import com.citicbank.model.Ticket;
import com.citicbank.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by FIN on 2016/12/8.
 */
@Mapper
public interface TicketDAO {
    String TABLE_NAME="ticket";
    String INSERT_FIELDS="title,like_count,user_id";
    String SELECT_FIELDS="id,title,like_count,user_id";

    @Insert({"insert into " ,TABLE_NAME ,"(" , INSERT_FIELDS ,
            ") values (#{title},#{likecount},#{user_id})"})
    int addTicket(Ticket ticket);

    List<Ticket> selectByUserIdAndOffset(@Param("user_id") int user_id,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);
}
