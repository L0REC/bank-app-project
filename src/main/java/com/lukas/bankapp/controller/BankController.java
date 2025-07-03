package com.lukas.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lukas.bankapp.exception.InsufficientFundsException;
import com.lukas.bankapp.model.Account;
import com.lukas.bankapp.service.AccountService;

@Controller
public class BankController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/")
	public String index(Model model) {
		Account account = accountService.getAccount();
		model.addAttribute("account", account);
		return "index";
	}
	
	@GetMapping("/account")
	public String accountDetails(Model model) {
		Account account = accountService.getAccount();
		model.addAttribute("account", account);
		return "account";
	}
	
	@GetMapping("/transaction")
	public String transactionPage(Model model) {
		Account account = accountService.getAccount();
		model.addAttribute("account", account);
		return "transaction";
	}
	
	@PostMapping("/deposit")
	public String processDeposit(@RequestParam Double amount, RedirectAttributes redirectAttributes) {
		try {
			accountService.deposit(amount);
			redirectAttributes.addFlashAttribute("successMessage", String.format("Deposit of $%.2f successful!", amount));
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/transaction";
	}
	
	@PostMapping("/withdraw")
	public String processWithdrawal(@RequestParam Double amount, RedirectAttributes redirectAttributes) {
		try {
			accountService.withdraw(amount);
			redirectAttributes.addFlashAttribute("successMessage", String.format("Withdrawal of $%.2f successful!", amount));
		} catch (IllegalArgumentException | InsufficientFundsException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/transaction";
	}
	
}
















