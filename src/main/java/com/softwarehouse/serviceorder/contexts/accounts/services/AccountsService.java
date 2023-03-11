package com.softwarehouse.serviceorder.contexts.accounts.services;

import com.softwarehouse.serviceorder.contexts.order.entities.Order;
import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPayable;
import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsReceivable;
import com.softwarehouse.serviceorder.contexts.accounts.models.BillingEntity;
import com.softwarehouse.serviceorder.contexts.accounts.repositories.AccountsPayableRepository;
import com.softwarehouse.serviceorder.contexts.accounts.repositories.AccountsReceivableRepository;
import com.softwarehouse.serviceorder.contexts.accounts.utils.GetBillingOccurrence;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {
    private final AccountsPayableRepository accountsPayableRepository;
    private final AccountsReceivableRepository accountsReceivableRepository;

    public AccountsService(final AccountsPayableRepository accountsPayableRepository,
                           final AccountsReceivableRepository accountsReceivableRepository) {
        this.accountsPayableRepository = accountsPayableRepository;
        this.accountsReceivableRepository = accountsReceivableRepository;
    }

    public void placeAccountsReceivable(final Order order) {
        for (var orderPayment : order.getPayments()) {
            var payment = orderPayment.getPayment();
            final AccountsReceivable accountsReceivable = new AccountsReceivable();

            accountsReceivable.setDescription("Conta a Receber (ordem nº %s)".formatted(order.getId()));
            accountsReceivable.setOccurrence(GetBillingOccurrence.get(payment));
            accountsReceivable.setValue(payment.getValue());
            accountsReceivable.setDiscount(payment.getDiscount());
            accountsReceivable.setFee(payment.getPaymentMethod().getLateFeePercent());
            accountsReceivable.setEntity(BillingEntity.CUSTOMER);
            accountsReceivable.setCustomer(order.getCustomer());
            accountsReceivable.setCostCenter(order.getCostCenter());
            accountsReceivable.setDate(payment.getDate());
            accountsReceivable.setPayment(payment);
            accountsReceivable.setDescription("Conta a receber automaticamente criada ao gerar pagamento pendente para cliente");

            this.accountsReceivableRepository.save(accountsReceivable);
        }
    }

    public void placeAccountsPayable(final Order order) {
        for (var orderPayment : order.getPayments()) {
            var payment = orderPayment.getPayment();
            final AccountsPayable accountsPayable = new AccountsPayable();

            accountsPayable.setDescription("Conta a Pagar (ordem nº %s)".formatted(order.getId()));
            accountsPayable.setOccurrence(GetBillingOccurrence.get(payment));
            accountsPayable.setValue(payment.getValue());
            accountsPayable.setDiscount(payment.getDiscount());
            accountsPayable.setFee(payment.getPaymentMethod().getLateFeePercent());
            accountsPayable.setEntity(BillingEntity.CUSTOMER);
            accountsPayable.setCustomer(order.getCustomer());
            accountsPayable.setCostCenter(order.getCostCenter());
            accountsPayable.setDate(payment.getDate());
            accountsPayable.setPayment(payment);
            accountsPayable.setDescription("Conta a pagar automaticamente criada ao gerar pagamento pendente");

            this.accountsPayableRepository.save(accountsPayable);
        }
    }
}
