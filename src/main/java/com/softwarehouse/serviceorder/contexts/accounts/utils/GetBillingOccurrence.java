package com.softwarehouse.serviceorder.contexts.accounts.utils;

import com.softwarehouse.serviceorder.contexts.accounts.models.BillingOccurrence;
import com.softwarehouse.serviceorder.contexts.payment.entities.Payment;
import com.softwarehouse.serviceorder.contexts.payment.models.PaymentModality;

import java.util.Map;

public class GetBillingOccurrence {
    private GetBillingOccurrence() {
    }

    private static final Map<String, BillingOccurrence> OCCURRENCE_MAP = Map.of(
            PaymentModality.CREDIT_CARD.name(), BillingOccurrence.INSTALLMENT,
            "default", BillingOccurrence.ONCE
    );

    public static BillingOccurrence get(final Payment payment) {
        return OCCURRENCE_MAP.getOrDefault(payment.getPaymentMethod().getModality().name(), BillingOccurrence.ONCE);
    }
}
