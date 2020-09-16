package com.alvaro.bank.controller;

import com.alvaro.bank.model.Transfer;
import com.alvaro.bank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = "/transfer", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void transferAmount(@RequestBody @Valid Transfer transfer) {
        transferService.doTransfer(transfer);
    }

}
