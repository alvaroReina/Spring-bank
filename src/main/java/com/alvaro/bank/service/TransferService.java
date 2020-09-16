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
import java.util.UUID;

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
     *
     * The balance manipulation is done using USD as a reference, the result is converted to the account currency.
     * @param transfer a {@link Transfer}
     */
    public void doTransfer(Transfer transfer) {
        UUID from = transfer.getSender();
        UUID to = transfer.getReceiver();
        if (from.equals(to)) {
            throw new TransferException("The sender and receiver account cannot be the same");
        }

        //Check if both accounts exists
        Account fromAcc = accountRepository.findById(from).orElseThrow(() -> new AccountNotFoundException(from));
        Account toAcc = accountRepository.findById(to).orElseThrow(() -> new AccountNotFoundException(to));

        //Get the sender balance in USD
        BigDecimal normalizedAmount = transfer.getAmount().multiply(transfer.getCurrency().getRate());
        BigDecimal normalizedSenderBalance = getNormalizedAccountBalance(fromAcc);

        //Only a treasury account can have a negative balance
        if (normalizedSenderBalance.subtract(normalizedAmount).compareTo(BigDecimal.ZERO) < 0 &&
                !fromAcc.getTreasury()
        ) {
            throw new TransferException(transfer.getAmount(), transfer.getCurrency());
        }

        //Calculates the new balance for the sender account and convert it to the sender currency.
        BigDecimal senderNewBalance = normalizedSenderBalance.subtract(normalizedAmount);
        fromAcc.setBalance(senderNewBalance.multiply(fromAcc.getCurrency().getInverseRate()));

        //Same to the receiver account.
        BigDecimal normalizedReceiverBalance = getNormalizedAccountBalance(toAcc);
        BigDecimal receiverNewBalance = normalizedReceiverBalance.add(normalizedAmount);
        toAcc.setBalance(receiverNewBalance.multiply(toAcc.getCurrency().getInverseRate()));

        accountRepository.saveAll(Arrays.asList(fromAcc, toAcc));
        //Explicitly order JPA to commit the changes to the database
        accountRepository.flush();
    }


}
