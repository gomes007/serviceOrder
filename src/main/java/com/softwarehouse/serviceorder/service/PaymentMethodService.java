package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.PaymentMethod;
import com.softwarehouse.serviceorder.domain.PaymentMethodAvailability;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.PaymentMethodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository repository;

    public PaymentMethodService(final PaymentMethodRepository repository) {
        this.repository = repository;
    }

    public PaymentMethod create(final PaymentMethod paymentMethod) {
        return this.repository.save(paymentMethod);
    }

    public void paymentMethodExists(final Long id) {
        this.findById(id);
    }

    public PaymentMethod update(final PaymentMethod paymentMethod, final Long id) {
        paymentMethod.setId(id);
        return this.repository.save(paymentMethod);
    }

    public Response<PaymentMethod> find(
            final PageRequest pageRequest,
            final String filter,
            final PaymentMethodAvailability availability
    ) {
        Page<PaymentMethod> paymentMethods = this.repository.findAllByNameIgnoreCaseContainingAndAvailability(
                filter,
                availability,
                PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
        );
        return Response
                .<PaymentMethod>builder()
                .items(paymentMethods.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(paymentMethods.getTotalElements())
                .build();
    }

    public PaymentMethod findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("payment method not available"));
    }

    public PaymentMethod deleteById(final Long id) {
        final PaymentMethod found = this.findById(id);

        this.repository.delete(found);

        return found;
    }
}
