package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.Transaction;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RedirectAdminPageCommand implements Command {
    private static final String TRANSACTIONS_PARAM = "transactions";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransactionDao dao = MysqlDaoFactory.getInstance().getTransactionDao();
        List<Transaction> transactions = dao.findAll();
        request.setAttribute(TRANSACTIONS_PARAM, transactions);
        return Page.get(Page.USER);
    }
}
