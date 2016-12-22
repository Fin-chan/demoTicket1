package lab.io.rush.dao;

import lab.io.rush.model.Ticket;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 票 dao层
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

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
    Ticket getById(int id);

    @Update({"update",TABLE_NAME,"set like_count=#{likecount} where id=#{id}"})
    int updateLikeCount(@Param("id") int id,@Param("likecount") long likecount);

    @Select({"select like_count","from",TABLE_NAME,"where id=#{id}"})
    long selectLikecount(@Param("id") int id);
}
