package com.example.demo.customer.service;

import com.example.demo.customer.Customer;
import com.example.demo.customer.dto.CustomerRequest;
import com.example.demo.customer.dto.CustomerResponse;
import com.example.demo.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_success() {
        CustomerRequest req = new CustomerRequest("John", "Doe", "john@example.com");
        when(repository.existsByEmail(req.getEmail())).thenReturn(false);
        Customer toSave = new Customer(req.getFirstName(), req.getLastName(), req.getEmail());
        // simulate generated id and createdAt
        Customer saved = new Customer(req.getFirstName(), req.getLastName(), req.getEmail());
        // set id via reflection isn't necessary — mock repository to return saved with id
        when(repository.save(any(Customer.class))).thenAnswer(inv -> {
            Customer c = inv.getArgument(0);
            // emulate DB assigning id
            java.lang.reflect.Field idField = Customer.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(c, 42L);
            java.lang.reflect.Field createdField = Customer.class.getDeclaredField("createdAt");
            createdField.setAccessible(true);
            createdField.set(c, OffsetDateTime.now());
            return c;
        });

        CustomerResponse resp = service.createCustomer(req);

        assertNotNull(resp.getId());
        assertEquals(req.getEmail(), resp.getEmail());
        verify(repository).existsByEmail(req.getEmail());
        verify(repository).save(any(Customer.class));
    }

    @Test
    void createCustomer_duplicate_throws() {
        CustomerRequest req = new CustomerRequest("Jane", "Doe", "jane@example.com");
        when(repository.existsByEmail(req.getEmail())).thenReturn(true);

        DuplicateCustomerException ex = assertThrows(DuplicateCustomerException.class, () -> service.createCustomer(req));
        assertTrue(ex.getMessage().contains(req.getEmail()));
        verify(repository).existsByEmail(req.getEmail());
        verify(repository, never()).save(any());
    }
}
