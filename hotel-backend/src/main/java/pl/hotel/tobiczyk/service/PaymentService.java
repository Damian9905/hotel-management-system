package pl.hotel.tobiczyk.service;

import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.model.Payment;
import pl.hotel.tobiczyk.domain.model.PaymentMethod;
import pl.hotel.tobiczyk.domain.model.PaymentStatus;
import pl.hotel.tobiczyk.domain.model.Reservation;
import pl.hotel.tobiczyk.repository.PaymentRepository;

@Service
public class PaymentService {
  private PaymentRepository paymentRepository;

  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Payment createPayment(final PaymentMethod paymentMethod, final Reservation reservation) {
    Payment payment = Payment.builder()
        .paymentStatus(PaymentStatus.UNCOMPLETED.getValue())
        .paymentMethod(paymentMethod.getValue())
        .build();
    return payment;
  }

  public Payment save(final Payment payment) {
    return paymentRepository.save(payment);
  }
}
