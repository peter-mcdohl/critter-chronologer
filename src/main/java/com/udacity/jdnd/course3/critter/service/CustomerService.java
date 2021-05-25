package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customerFromDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO customerIntoDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        if (customer.getPets() != null) {
            List<Long> petIds = new ArrayList<>();
            customer.getPets().forEach(
                    pet -> {
                        petIds.add(pet.getId());
                    });
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer newCustomer = customerRepository.save(customerFromDTO(customerDTO));
        return customerIntoDTO(newCustomer);
    }

    private List<CustomerDTO> customerListIntoDTO(List<Customer> customerList) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for(int i = 0; i < customerList.size(); i ++) {
            customerDTOList.add(customerIntoDTO(customerList.get(i)));
        }
        return customerDTOList;
    }

    public List<CustomerDTO> findAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return customerListIntoDTO(customerList);
    }
}
