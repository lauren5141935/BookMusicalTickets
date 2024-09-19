package BookMusicalTickets.example.BookMusicalTickets.mapper;

import BookMusicalTickets.example.BookMusicalTickets.domain.user.Customer;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.CustomerDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.CustomerDTOPassword;
import java.sql.Date;

public class CustomerMapper {

  private CustomerMapper() {}

  public static Customer customerDTOToCustomer(CustomerDTO customerDTO) {
    return Customer.builder()
        .name(customerDTO.getName())
        .loginId(customerDTO.getLoginId())
        .password(customerDTO.getPassword())
        .nickname(customerDTO.getNickname())
        .birthdate(Date.valueOf(customerDTO.getBirthdate()))
        .gender(customerDTO.getGender())
        .phoneNo(customerDTO.getPhoneNo())
        .email(customerDTO.getEmail())
        .build();
  }

  public static CustomerDTOPassword customerToCustomerDTOPassword(Customer customer) {
    return CustomerDTOPassword.builder()
        .name(customer.getName())
        .email(customer.getEmail())
        .phoneNo(customer.getPhoneNo())
        .gender(customer.getGender())
        .birthdate(customer.getBirthdate())
        .loginId(customer.getLoginId())
        .nickname(customer.getNickname())
        .point(customer.getPoint())
        .build();
  }
}