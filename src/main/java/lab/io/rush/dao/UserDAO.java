package lab.io.rush.dao;

import lab.io.rush.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户DAO
 * Created by FIN on 2016/12/8.
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME="user";
    String INSERT_FIELDS="name,user_open_id,password,salt";
    String SELECT_FIELDS="id,name,user_open_id,password,salt";

    @Insert({"insert into " ,TABLE_NAME ,"(" , INSERT_FIELDS ,
            ") values (#{name},#{user_open_id},#{password},#{salt})"})
    int addUser(User user);


    @Select({"select",SELECT_FIELDS,"from" ,TABLE_NAME,"where id=#{id}"})
    User selectByUserId(int id);

    @Select({"select",SELECT_FIELDS,"from" ,TABLE_NAME,"where name=#{name}"})
    User selectByUserName(String name);






}
