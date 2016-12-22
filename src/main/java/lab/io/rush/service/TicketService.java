package lab.io.rush.service;

import lab.io.rush.dao.TicketDAO;
import lab.io.rush.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 票 service
 * Created by FIN on 2016/12/8.
 */
@Service
public class TicketService {
    @Autowired
    private TicketDAO ticketDAO;

    public List<Ticket> getLastTicket(int user_id,
                                      int offset,
                                      int limit){
        return ticketDAO.selectByUserIdAndOffset(user_id,offset,limit);
    }

    public int updateLikeCount(int id,long likecount){
        return ticketDAO.updateLikeCount(id,likecount);
    }

    public long selectLikeCount(int id){
        return ticketDAO.selectLikecount(id);
    }

    public Ticket getById(int id){
        return ticketDAO.getById(id);
    }
}
