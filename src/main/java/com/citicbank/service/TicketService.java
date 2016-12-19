package com.citicbank.service;

import com.citicbank.dao.TicketDAO;
import com.citicbank.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
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
}
