package com.softwarehouse.serviceorder.config;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPlan;
import com.softwarehouse.serviceorder.contexts.accounts.services.AccountsPlanService;
import com.softwarehouse.serviceorder.contexts.address.entities.Address;
import com.softwarehouse.serviceorder.contexts.address.entities.AddressType;
import com.softwarehouse.serviceorder.contexts.address.services.AddressTypeService;
import com.softwarehouse.serviceorder.contexts.costcenter.entities.CostCenter;
import com.softwarehouse.serviceorder.contexts.costcenter.entities.CostCenterStatus;
import com.softwarehouse.serviceorder.contexts.customer.entities.Customer;
import com.softwarehouse.serviceorder.contexts.customer.services.CustomerService;
import com.softwarehouse.serviceorder.contexts.employee.entities.Employee;
import com.softwarehouse.serviceorder.contexts.employee.entities.PositionSalary;
import com.softwarehouse.serviceorder.contexts.employee.services.EmployeeService;
import com.softwarehouse.serviceorder.contexts.employee.services.PositionSalaryService;
import com.softwarehouse.serviceorder.contexts.order.entities.*;
import com.softwarehouse.serviceorder.contexts.order.services.OrderManagementService;
import com.softwarehouse.serviceorder.contexts.payment.entities.Payment;
import com.softwarehouse.serviceorder.contexts.payment.entities.PaymentMethod;
import com.softwarehouse.serviceorder.contexts.payment.models.PaymentMethodAvailability;
import com.softwarehouse.serviceorder.contexts.payment.models.PaymentModality;
import com.softwarehouse.serviceorder.contexts.payment.services.PaymentMethodService;
import com.softwarehouse.serviceorder.contexts.product.entities.Details;
import com.softwarehouse.serviceorder.contexts.product.entities.Inventory;
import com.softwarehouse.serviceorder.contexts.product.entities.Product;
import com.softwarehouse.serviceorder.contexts.product.services.ProductService;
import com.softwarehouse.serviceorder.contexts.provider.entities.Provider;
import com.softwarehouse.serviceorder.contexts.service.entities.Service;
import com.softwarehouse.serviceorder.contexts.service.services.ServiceService;
import com.softwarehouse.serviceorder.contexts.shared.entities.Contact;
import com.softwarehouse.serviceorder.contexts.shared.entities.ContactType;
import com.softwarehouse.serviceorder.contexts.shared.entities.GeneralInformation;
import com.softwarehouse.serviceorder.contexts.shared.entities.Price;
import com.softwarehouse.serviceorder.contexts.shared.models.CompanyType;
import com.softwarehouse.serviceorder.contexts.shared.models.Gender;
import com.softwarehouse.serviceorder.contexts.shared.models.Situation;
import com.softwarehouse.serviceorder.contexts.costcenter.services.CostCenterService;
import com.softwarehouse.serviceorder.contexts.provider.services.ProviderService;
import com.softwarehouse.serviceorder.contexts.shared.services.ContactTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class InitScript implements InitializingBean {
    @Autowired
    private AddressTypeService addressTypeService;

    @Autowired
    private CostCenterService costCenterService;

    @Autowired
    private ContactTypeService contactTypeService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private PositionSalaryService positionSalaryService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AccountsPlanService accountsPlanService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private OrderManagementService orderManagementService;

    public void afterPropertiesSet() throws Exception {
        // cadastra o tipo de endereço
        AddressType addressType = null;
        if (this.addressTypeService.findAll().isEmpty()) {
            final AddressType newAddressType = new AddressType();
            newAddressType.setLabel("Rua");

            addressType = this.addressTypeService.save(newAddressType);
        } else {
            addressType = this.addressTypeService.findAll().get(0);
        }

        // cadastra centro de custo
        CostCenter costCenter = null;
        var costCenters = this.costCenterService.find(PageRequest.of(1, 10), "", CostCenterStatus.ENABLED);
        if (costCenters.getTotalRecordsQuantity() == 0) {
            final CostCenter newCostCenter = new CostCenter();
            newCostCenter.setName("Serviços");
            newCostCenter.setStatus(CostCenterStatus.ENABLED);

            costCenter = this.costCenterService.register(newCostCenter);
        } else {
            costCenter = costCenters.getItems().get(0);
        }

        // cadastra tipos de contato telefone e email
        var contactTypes = this.contactTypeService.findAll();
        final ContactType phoneContactType = contactTypes
                .stream()
                .filter(c -> c.getLabel().equals("Telefone"))
                .findFirst()
                .orElseGet(() -> {
                    final ContactType newPhoneContactType = new ContactType();
                    newPhoneContactType.setLabel("Telefone");
                    return this.contactTypeService.save(newPhoneContactType);
                });
        final ContactType emailContactType = contactTypes
                .stream()
                .filter(c -> c.getLabel().equals("E-mail"))
                .findFirst()
                .orElseGet(() -> {
                    final ContactType newEmailContactType = new ContactType();
                    newEmailContactType.setLabel("E-mail");
                    return this.contactTypeService.save(newEmailContactType);
                });

        // cadastra provider
        Provider provider = null;
        var providers = this.providerService.find(PageRequest.of(1, 10)).getItems();
        if (providers.isEmpty()) {
            final GeneralInformation providerInfo = new GeneralInformation();
            providerInfo.setName("Samsung");
            providerInfo.setCnpj("11111111000100");
            providerInfo.setEmail("samsung@providers.com");
            providerInfo.setPhone("11977776666");
            providerInfo.setSituation(Situation.ACTIVE);
            providerInfo.setCompanyType(CompanyType.Company);

            final Address providerAddress = new Address();
            providerAddress.setAddressType(addressType);
            providerAddress.setStreet("Rua Teste");
            providerAddress.setNumber("s/n");
            providerAddress.setNeighborhood("Bairro Teste");
            providerAddress.setCity("Cidade Teste");
            providerAddress.setState("TE");
            providerAddress.setZipCode("55555555");
            providerAddress.setComplement("Complemento Teste");

            final Contact providerPhoneContact = new Contact();
            providerPhoneContact.setContactType(phoneContactType);
            providerPhoneContact.setOccupation("Gerente");
            providerPhoneContact.setContact("5588988888888");
            final Contact providerEmailContact = new Contact();
            providerEmailContact.setContactType(emailContactType);
            providerEmailContact.setOccupation("Gerente");
            providerEmailContact.setContact("gerente@email.test.com");

            final Provider newProvider = new Provider();
            newProvider.setGeneralInformation(providerInfo);
            newProvider.setAddresses(List.of(providerAddress));
            newProvider.setContacts(List.of(providerPhoneContact, providerEmailContact));

            provider = this.providerService.save(newProvider);
        } else {
            provider = providers.get(0);
        }

        // cadastra produto
        Product product = null;
        var products = this.productService.find(PageRequest.of(1, 10)).getItems();
        if (products.isEmpty()) {
            Product newProduct = new Product();

            newProduct.setProductName("Tela Samsung A3");
            newProduct.setDescription("Tela nova para troca em aparelho danificado (não acompanha display)");
            newProduct.setDetails(Details.builder().enabled(true).soldSeparately(true).enabledOnPDV(true).build());
            newProduct.setPrice(Price.builder()
                    .salePrice(BigDecimal.valueOf(100))
                    .unitCost(BigDecimal.valueOf(50))
                    .finalCost(BigDecimal.valueOf(50))
                    .usedSaleValue(BigDecimal.valueOf(100))
                    .suggestedSaleValue(BigDecimal.valueOf(100))
                    .build());
            newProduct.setInventory(Inventory.builder().currentQuantity(2).minQuantity(1).maxQuantity(10).build());
            newProduct.setProvider(provider);

            product = this.productService.register(newProduct);
        } else {
            product = products.get(0);
        }

        // cadastra serviço
        Service service = null;
        var services = this.serviceService.find(PageRequest.of(1, 10), "").getItems();
        if (services.isEmpty()) {
            Service newService = new Service();
            newService.setServiceName("Serviço de Troca de Tela de Smartphone");
            newService.setDescription("Troca de tela de smart");
            newService.setUnitCost(BigDecimal.valueOf(100));

            service = this.serviceService.create(newService);
        } else {
            service = services.get(0);
        }

        // cadastra Position and Salary
        PositionSalary positionSalary = null;
        var positions = this.positionSalaryService.findAll("",
                "",
                PageRequest.of(1, 10)).getItems();
        if (positions.isEmpty()) {
            PositionSalary newPosition = new PositionSalary();

            newPosition.setPosition("Gerente");
            newPosition.setRole("Senior");
            newPosition.setSalary(BigDecimal.valueOf(15000.00));
            newPosition.setCommission(BigDecimal.valueOf(5.50));

            positionSalary = this.positionSalaryService.save(newPosition);
        } else {
            positionSalary = positions.get(0);
        }

        // cadastra funcionario
        Employee employee = null;
        var employees = this.employeeService.find("",
                "",
                "",
                PageRequest.of(1, 10)).getItems();
        if (employees.isEmpty()) {
            Employee newEmployee = new Employee();

            newEmployee.setName("Colaborador Teste");
            newEmployee.setCpf("11111111111");
            newEmployee.setEmail("colaborador.teste@test.mail");
            newEmployee.setPhone("5511988888888");
            newEmployee.setPositionSalary(positionSalary);
            newEmployee.setBirthDate(LocalDate.of(1991, Month.FEBRUARY, 20));
            newEmployee.setGender(Gender.Male);

            final Address employeeAddress = new Address();
            employeeAddress.setAddressType(addressType);
            employeeAddress.setStreet("Rua Colaborador Teste");
            employeeAddress.setNumber("s/n");
            employeeAddress.setNeighborhood("Bairro Colaborador Teste");
            employeeAddress.setCity("Cidade Colaborador Teste");
            employeeAddress.setState("TE");
            employeeAddress.setZipCode("44444444");
            employeeAddress.setComplement("Complemento Colaborador Teste");

            newEmployee.setAddress(employeeAddress);

            employee = this.employeeService.save(newEmployee);
        } else {
            employee = employees.get(0);
        }

        // cadastra plano de contas
        AccountsPlan accountsPlan = null;
        var allPlans = this.accountsPlanService.findAll();
        if (!allPlans.isEmpty()) {
            accountsPlan = allPlans.get(0);
        } else {
            AccountsPlan newPlan = new AccountsPlan();

            newPlan.setLabel("Serviços");
            newPlan.setReceivingAccount(true);
            newPlan.setPayingAccount(false);

            accountsPlan = this.accountsPlanService.register(newPlan);
        }

        // cadastra cliente
        Customer customer = null;

        var allCustomers = this.customerService.find(PageRequest.of(1, 10), "").getItems();
        if (!allCustomers.isEmpty()) {
            customer = allCustomers.get(0);
        } else {
            Customer newCustomer = new Customer();

            final GeneralInformation customerInfo = new GeneralInformation();
            customerInfo.setName("Cliente Teste");
            customerInfo.setCpf("77777777777");
            customerInfo.setEmail("customer@customers.com");
            customerInfo.setPhone("5511966667777");
            customerInfo.setSituation(Situation.ACTIVE);
            customerInfo.setCompanyType(CompanyType.Person);

            newCustomer.setGeneralInformation(customerInfo);

            final Address customerAddress = new Address();
            customerAddress.setAddressType(addressType);
            customerAddress.setStreet("Rua Cliente Teste");
            customerAddress.setNumber("s/n");
            customerAddress.setNeighborhood("Bairro Cliente Teste");
            customerAddress.setCity("Cidade Cliente Teste");
            customerAddress.setState("TE");
            customerAddress.setZipCode("33333333");
            customerAddress.setComplement("Complemento Cliente Teste");

            newCustomer.setAddresses(List.of(customerAddress));

            final Contact customerPhoneContact = new Contact();
            customerPhoneContact.setContactType(phoneContactType);
            customerPhoneContact.setOccupation("Gerente");
            customerPhoneContact.setContact("5511988888888");
            final Contact customerEmailContact = new Contact();
            customerEmailContact.setContactType(emailContactType);
            customerEmailContact.setOccupation("Gerente");
            customerEmailContact.setContact("gerente@customer.test.com");

            newCustomer.setContacts(List.of(customerPhoneContact, customerEmailContact));

            customer = this.customerService.save(newCustomer);
        }

        // cadastra metodo de pagamento
        PaymentMethod paymentMethod = null;
        var paymentMethods = this.paymentMethodService.find(PageRequest.of(1, 10),
                "",
                PaymentMethodAvailability.BOTH).getItems();

        if (!paymentMethods.isEmpty()) {
            paymentMethod = paymentMethods.get(0);
        } else {
            PaymentMethod newPaymentMethod = new PaymentMethod();

            newPaymentMethod.setName("À vista");
            newPaymentMethod.setAvailability(PaymentMethodAvailability.BOTH);
            newPaymentMethod.setModality(PaymentModality.MONEY);
            newPaymentMethod.setCanCreateBill(false);
            newPaymentMethod.setAvailableOnSellingStation(true);

            paymentMethod = this.paymentMethodService.create(newPaymentMethod);
        }

        // cadastra ordem de serviço (junto com contas a receber)
        var orders = this.orderManagementService.find(PageRequest.of(1, 10)).getItems();
        if (orders.isEmpty()) {
            Order order = new Order();

            order.setCustomer(customer);
            order.setChannelSale("Web");
            order.setAttendant(employee);
            order.setExpert(employee);
            order.setStartDate(LocalDate.now());
            order.setEndDate(LocalDate.now());
            order.setDiscountAmount(BigDecimal.valueOf(1.00d));
            order.setDiscountPercent(BigDecimal.valueOf(0.00d));

            OrderEquipment orderEquipment = new OrderEquipment();
            orderEquipment.setName("Smartphone Samsung Galaxy A3");
            orderEquipment.setBrand("Samsung");
            orderEquipment.setModel("A3");
            orderEquipment.setSeriesNumber("12300824");
            orderEquipment.setConditions("Tela trincada");
            orderEquipment.setFlaws("Touch sem reconhecer o local certo");
            orderEquipment.setFittings("");
            orderEquipment.setSolution("Troca da tela");
            orderEquipment.setTechnicalReport("Tela com trinco superficial sem afetar display");
            orderEquipment.setWarrantyTerms("Garantia de 3 meses");

            order.setOrderEquipments(List.of(orderEquipment));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(1);

            order.setOrderProducts(List.of(orderProduct));

            OrderService orderService = new OrderService();
            orderService.setService(service);
            orderService.setQuantity(1);
            orderService.setDiscountPercent(BigDecimal.valueOf(5.00d));

            order.setOrderServices(List.of(orderService));

            order.setCostCenter(costCenter);

            order.setAccountsPlan(accountsPlan);

            Payment payment = new Payment();
            payment.setValue(BigDecimal.valueOf(94.00d));
            payment.setPaymentMethod(paymentMethod);
            payment.setProcessed(true);
            payment.setDate(LocalDate.now());

            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setPayment(payment);

            order.setPayments(List.of(orderPayment));

            this.orderManagementService.open(order);
        }
    }
}
