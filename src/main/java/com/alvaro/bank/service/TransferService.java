package com.alvaro.bank.service;

import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.exception.TransferException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Transfer;
import com.alvaro.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.alvaro.bank.util.Utils.getNormalizedAccountBalance;

@Service
public class TransferService {

    private final AccountRepository accountRepository;

    @Autowired
    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Performs a transference, calculates the new balance for the sender and receiver
     * and commits the result to the database.
     * <p>
     * The balance manipulation is done using USD as a reference, the result is converted to the account currency.
     *
     * @param transfer a {@link Transfer}
     */
    public void doTransfer(Transfer transfer) {
        String sender = transfer.getSender();
        String receiver = transfer.getReceiver();
        if (sender.equals(receiver)) {
            throw new TransferException("The sender and receiver account cannot be the same");
        }

        //Check if both accounts exists
        Account senderAcc = accountRepository.findAccountByName(sender)
                .orElseThrow(() -> new AccountNotFoundException(sender));
        Account receiverAcc = accountRepository.findAccountByName(receiver)
                .orElseThrow(() -> new AccountNotFoundException(receiver));

        //Get the sender balance in USD
        BigDecimal normalizedAmount = transfer.getAmount().multiply(transfer.getCurrency().getRate());
        BigDecimal normalizedSenderBalance = getNormalizedAccountBalance(senderAcc);

        //Calculate the new balance for the sender account and convert it to the sender currency.
        BigDecimal senderNewBalance = normalizedSenderBalance.subtract(normalizedAmount);
        senderAcc.setBalance(senderNewBalance.multiply(senderAcc.getCurrency().getInverseRate()));

        //Only a treasury account can have a negative balance
        if (!senderAcc.checkBalance()) {
            throw new TransferException(transfer.getAmount(), transfer.getCurrency());
        }

        //Calculate new balance for the receiver account
        BigDecimal normalizedReceiverBalance = getNormalizedAccountBalance(receiverAcc);
        BigDecimal receiverNewBalance = normalizedReceiverBalance.add(normalizedAmount);
        receiverAcc.setBalance(receiverNewBalance.multiply(receiverAcc.getCurrency().getInverseRate()));

        accountRepository.saveAll(Arrays.asList(senderAcc, receiverAcc));
        //Explicitly order JPA to commit the changes to the database
        accountRepository.flush();
    }


}
