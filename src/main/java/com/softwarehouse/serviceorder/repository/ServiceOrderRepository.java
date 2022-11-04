package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.ServiceOrder;
import com.softwarehouse.serviceorder.domain.ServiceOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    @Query(
            nativeQuery = true,
            value = """
                     select so.* from service_order so 
                     join customer c on c.id = so.customer_id
                     join employee at on at.id = so.attendant_id
                     join employee ex on ex.id = so.expert_id
                     where (upper(c.name) like upper("%?%")
                     or upper(at.name) like upper("%?%")
                     or upper(ex.name) like upper("%?%"))
                     and so.status = "?"
                    """
    )
    Page<ServiceOrder> findAllFiltering(
            String customerName,
            String attendantName,
            String expertName,
            ServiceOrderStatus status,
            LocalDate fromStartDate,
            LocalDate toStartDate,
            LocalDate fromEndDate,
            LocalDate toEndDate,
            Pageable pageable
    );
}
