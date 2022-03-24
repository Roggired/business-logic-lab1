package ru.yofik.bank.context.account.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yofik.bank.context.account.api.requests.CreateAccountRequest;
import ru.yofik.bank.context.account.api.requests.PutMoneyRequest;
import ru.yofik.bank.context.account.api.responses.CreateAccountResponse;
import ru.yofik.bank.context.account.services.AccountService;
import ru.yofik.bank.context.account.view.AccountView;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/api/v2/account",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ApplicationResource {
    @Autowired
    private AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }

    @PutMapping(value = "/{accountId}")
    public void putMoney(@RequestBody @Valid PutMoneyRequest request, @PathVariable String accountId) {
        accountService.putMoney(request, accountId);
    }

    @GetMapping(value = "/{accountId}")
    public AccountView getAccountInfo(@PathVariable String accountId, @RequestParam int pinCode) {
        return accountService.getAccountInfo(accountId, pinCode);
    }
}
