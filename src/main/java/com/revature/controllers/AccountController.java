package com.revature.controllers;

import org.slf4j.MDC;

import com.revature.models.CheckingsAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import jdk.internal.org.jline.utils.Log;

public class AccountController implements Controller {
	
	private AccountService accountService = new AccountService();

	private Handler Transfer = (ctx) -> {
		String idString1 = ctx.pathParam("id1");
		int id1 = Integer.parseInt(idString1);
		String idString2 = ctx.pathParam("id2");
		int id2 = Integer.parseInt(idString2);
		String amountString = ctx.pathParam("amount");
		double amount=Double.parseDouble(amountString);
		ctx.json(accountService.Transfer(id1,id2,amount));
		ctx.status(200);
		MDC.clear();
	};
	
	private Handler findAllAccounts = (ctx) -> {
		ctx.json(accountService.findAll());
		ctx.status(200);
		MDC.clear();
	};
	private Handler findAllSavingsAccounts = (ctx) -> {
		ctx.json(accountService.findAllSavings());
		ctx.status(200);
		MDC.clear();
	};
	private Handler findAllCheckingsAccounts = (ctx) -> {
		ctx.json(accountService.findAllCheckings());
		ctx.status(200);
		MDC.clear();
	};
	
	private Handler createSavingsAccount = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		SavingsAccount a = ctx.bodyAsClass(SavingsAccount.class);
		ctx.json(accountService.insert(a,id));
		ctx.status(200);
		MDC.clear();
	};
	private Handler createCheckingsAccount = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		CheckingsAccount a = ctx.bodyAsClass(CheckingsAccount.class);
		ctx.json(accountService.insert(a,id));
		ctx.status(200);
		MDC.clear();
	};
	private Handler findAccountbyID = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		ctx.json(accountService.findAccountById(id));
		ctx.status(200);
		MDC.clear();
	};
	private Handler findAccountbyUserID = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		ctx.json(accountService.findAccountByUserId(id));
		ctx.status(200);
		MDC.clear();
	};
	private Handler grantAccess = (ctx) -> {
		String idString = ctx.pathParam("id1");
		int id = Integer.parseInt(idString);
		String userIdString = ctx.pathParam("userId");
		int userId = Integer.parseInt(userIdString);
		ctx.json(accountService.grantAccess(id,userId));
		ctx.status(200);
		MDC.clear();
	};
	private Handler closeAccount = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		ctx.json(accountService.Close(id));
		ctx.status(200);
		MDC.clear();
	};
	
	public void addRoutes(Javalin app) {
		app.post("/transfer/:id1/:id2/:amount", this.Transfer);
		
		app.post("/account/s/:id", this.createSavingsAccount);
		
		app.post("/account/c/:id", this.createCheckingsAccount);
		
		app.get("/account", this.findAllAccounts);
		
		app.get("/savings", this.findAllSavingsAccounts);
		
		app.get("/checkings", this.findAllCheckingsAccounts);
		
		app.get("/account/:id", this.findAccountbyID);
		
		app.post("/access/:id1/:userId", this.grantAccess);
		
		app.get("/account/user/:id", this.findAccountbyUserID);
		
		app.post("/account/close/:id", this.closeAccount);
	}
}
