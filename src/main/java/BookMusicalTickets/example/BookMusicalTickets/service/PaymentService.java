package BookMusicalTickets.example.BookMusicalTickets.service;


import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import BookMusicalTickets.example.BookMusicalTickets.domain.payment.Payment;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.Ticket;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.TicketSeat;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Customer;
import BookMusicalTickets.example.BookMusicalTickets.dto.payment.PaymentDetailDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.payment.PaymentRegisterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.payment.PaymentShortDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.NonMemberDTO;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataExistsException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataNotExistsException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.InvalidAccessException;
import BookMusicalTickets.example.BookMusicalTickets.mapper.PaymentMapper;
import BookMusicalTickets.example.BookMusicalTickets.repository.CodeRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.CustomerRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.PaymentRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.TicketRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.TicketSeatRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final TicketSeatRepository ticketSeatRepository;
  private final TicketRepository ticketRepository;
  private final CodeRepository codeRepository;
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public PaymentDetailDTO register(PaymentRegisterDTO registerDTO) {
    Ticket ticket = ticketRepository.findById(registerDTO.getTicketId()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 티켓 ID 입니다.", "TICKET"));

    if(paymentRepository.existsByTicket(ticket)) {
      throw new DataExistsException("이미 결제된 티켓입니다.", "Payment");
    }
    List<TicketSeat> ticketSeats = ticketSeatRepository.findAllByTicketId(registerDTO.getTicketId());

    int totalPrice = ticketSeats.stream().mapToInt(ticketSeat -> ticketSeat.getSeat().getPrice()).sum();
    if(!(ticket.getSchedule().getDiscount() == null)) {
      String discount = ticket.getSchedule().getDiscount();
      char type = discount.charAt(discount.length() - 1);
      int scale = Integer.parseInt(discount.substring(0, discount.length()-1));
      switch (type) {
        case '%': totalPrice = totalPrice * (100-scale) / 100;
          break;
        case '\\': totalPrice = (totalPrice - scale) < 0 ? 0 : totalPrice - scale;
          break;
      }
    }

    Code code = codeRepository.findById(registerDTO.getCode()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 결제수단입니다.", "PAYMENT"));

    if(SecurityUtil.getCurrentUsername() == null && registerDTO.getCode().equals("PM000")) {
      throw new InvalidAccessException("비회원은 포인트로 결제할 수 없습니다.");
    }

    if(!(SecurityUtil.getCurrentUsername() == null)) {
      Customer customer = ticket.getCustomer();
      int currentPoint = customer.getPoint();
      if(registerDTO.getCode().equals("PM000")) {
        if(currentPoint < totalPrice) {
          throw new InvalidAccessException("보유한 포인트가 부족합니다.");
        }
        currentPoint -= totalPrice;
      }
      else {
        currentPoint += (int) totalPrice / 10;
        customerRepository.updatePoint(currentPoint, customer.getCustomerId());
      }
    }

    Payment payment = Payment.builder()
        .price(totalPrice)
        .method(code)
        .status(true)
        .ticket(ticket)
        .build();

    payment = paymentRepository.save(payment);

    return PaymentMapper.paymentToPaymentDetailDTO(payment);

  }

  @Transactional(readOnly = true)
  public List<Code> getMethodList() {
    return codeRepository.findAllByUpperCode(codeRepository.findPaymentMethodUpperCode());
  }

  @Transactional(readOnly = true)
  public List<PaymentShortDTO> getPaymentList(String loginId) {
    return paymentRepository.findAllByCustomerLoginId(loginId).stream().map(payment -> PaymentMapper.paymentToPaymentShortDTO(payment)).collect(
        Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PaymentDetailDTO getPaymentDetail(Long paymentId, String loginId) {
    Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 결제 ID 입니다.", "PAYMENT"));
    if(!payment.getTicket().getCustomer().getLoginId().equals(loginId)) {
      throw new InvalidAccessException("잘못된 사용자로 접근했습니다.");
    }

    return PaymentMapper.paymentToPaymentDetailDTO(payment);
  }

  @Transactional(readOnly = true)
  public PaymentDetailDTO getPaymentDetail(Long paymentId, NonMemberDTO nonMemberDTO) {
    Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 결제 ID 입니다.", "PAYMENT"));
    if(!passwordEncoder.matches(nonMemberDTO.getPassword(), payment.getTicket().getPassword())) {
      throw new InvalidAccessException("비밀번호가 틀렸습니다. 다시 입력해주세요.");
    }
    if(!payment.getTicket().getPhoneNo().equals(nonMemberDTO.getPhoneNo())) {
      throw new InvalidAccessException("잘못된 휴대전화 번호입니다. 다시 입력해주세요.");
    }

    return PaymentMapper.paymentToPaymentDetailDTO(payment);
  }
}
