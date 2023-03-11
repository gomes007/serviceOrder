package com.softwarehouse.serviceorder.contexts.order.repositories;

import com.softwarehouse.serviceorder.contexts.order.entities.Order;
import com.softwarehouse.serviceorder.contexts.order.models.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
            nativeQuery = true,
            value = """
                     select o.* from order o
                     join customer c on c.id = o.customer_id
                     join employee attendant on attendant.id = o.attendant_id
                     join employee expert on expert.id = o.expert_id
                     where (upper(c.name) like upper("%?%")
                     or upper(attendant.name) like upper("%?%")
                     or upper(expert.name) like upper("%?%"))
                     and o.status = "?"
                    """
    )
    Page<Order> findAllFiltering(
            String customerName,
            String attendantName,
            String expertName,
            OrderStatus status,
            LocalDate fromStartDate,
            LocalDate toStartDate,
            LocalDate fromEndDate,
            LocalDate toEndDate,
            Pageable pageable
    );
}
