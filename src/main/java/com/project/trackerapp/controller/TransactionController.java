package com.project.trackerapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.project.trackerapp.repository.CategoryRepository;
import com.project.trackerapp.repository.TransactionRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.project.trackerapp.model.User;
import com.project.trackerapp.model.Transaction;
import com.project.trackerapp.service.UserService;
import com.project.trackerapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.trackerapp.model.Category;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        User user = userService.getCurrentUser(principal);
        model.addAttribute("user", user);
        model.addAttribute("transactions", transactionService.getInDescendingOrder(user.getId()));
        return "dashboard";
    }

    @GetMapping("/transaction/add")
    public String add(Model model, Principal principal){
        model.addAttribute("user", userService.getCurrentUser(principal));
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("expenses", categoryRepository.findByCategoryType("Expense"));
        model.addAttribute("income", categoryRepository.findByCategoryType("Income"));
        return "add_transaction";
    }


    @PostMapping("/transaction/save")
    public String save(@Valid @ModelAttribute ("transaction") Transaction transaction, BindingResult result, Model model, Principal principal){
        if(result.hasErrors()){
            model.addAttribute("user", userService.getCurrentUser(principal));
            model.addAttribute("expenses", categoryRepository.findByCategoryType("Expense"));
            model.addAttribute("income", categoryRepository.findByCategoryType("Income"));
            return "add_transaction";
        }

        transaction.setUser(userService.getCurrentUser(principal));
        transactionRepository.save(transaction);
        return "redirect:/dashboard";
    }

    @GetMapping("/transaction/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model, Principal principal){

        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid transaction: " + id));
        model.addAttribute("transaction", transaction);
        model.addAttribute("expenses", categoryRepository.findByCategoryType("Expense"));
        model.addAttribute("income", categoryRepository.findByCategoryType("Income"));
        model.addAttribute("user", userService.getCurrentUser(principal));
        return "edit_transaction";
    }

    @PostMapping("/transaction/update/{id}")
    public String update(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("transaction") Transaction transaction,
            BindingResult result,
            Model model,
            Principal principal
    ){
        User user = userService.getCurrentUser(principal);
        if(result.hasErrors()){
            model.addAttribute("user", user);
            model.addAttribute("expenses", categoryRepository.findByCategoryType("Expense"));
            model.addAttribute("income", categoryRepository.findByCategoryType("Income"));
            return "edit_transaction";
        }
        transaction.setUser(user);
        transactionRepository.save(transaction);
        return "redirect:/dashboard";
    }

    @GetMapping("/transaction/delete/{id}")
    public String delete(@PathVariable("id") long id){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid transaction: " + id));
        transactionRepository.delete(transaction);
        return "redirect:/dashboard";
    }

    @GetMapping("/search")
    public String search(@RequestParam (name = "search", required = false) String criteria, Model model, Principal principal){
        model.addAttribute("transactions", transactionRepository.searchTransaction(criteria));
        model.addAttribute("user", userService.getCurrentUser(principal));
        return "dashboard";
    }

    @GetMapping("/my-wallet")
    public String myWallet(Model model, Principal principal){
        User user = userService.getCurrentUser(principal);
        model.addAttribute("user", user);

        Float expenseSum = transactionService.calculateSumByType(user.getId(), "Expense");
        Float incomeSum = transactionService.calculateSumByType(user.getId(), "Income");
        Float balance = incomeSum - expenseSum;

        model.addAttribute("expenseSum", expenseSum);
        model.addAttribute("incomeSum", incomeSum);
        model.addAttribute("balance", balance);

        List<Object[]> categorySums = transactionRepository.sumTransactionAmountByCategoryAndUser(user.getId());

        List<String> categoryNames = new ArrayList<>();
        List<String> chartColors = new ArrayList<>();
        List<Double> sums = new ArrayList<>();

        for (Object[] categorySum : categorySums) {
            String categoryName = (String) categorySum[0];
            String chartColor = (String) categorySum[1];
            Double sum = (Double) categorySum[2];
            categoryNames.add(categoryName);
            chartColors.add(chartColor);
            sums.add(sum);
        }

        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("chartColors", chartColors);
        model.addAttribute("sums", sums);

        return "my_wallet";
    }
}


