package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.model.repository.TransactionRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.TransactionDTO;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class RedirectAdminPageCommand implements Command {
    private static final String TRANSACTIONS_PARAM = "transactions";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransactionRepository repository = MysqlRepositoryFactory.getInstance().getTransactionRepository();
        List<TransactionDTO> transactions = repository.findAll();
        request.setAttribute(TRANSACTIONS_PARAM, transactions);
        return Page.get(Page.USER);
    }
}
