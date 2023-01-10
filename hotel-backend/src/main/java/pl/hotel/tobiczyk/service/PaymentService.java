package pl.hotel.tobiczyk.service;

import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.model.Payment;
import pl.hotel.tobiczyk.domain.model.PaymentMethod;
import pl.hotel.tobiczyk.domain.model.PaymentStatus;
import pl.hotel.tobiczyk.repository.PaymentRepository;

@Service
public class PaymentService {
  private PaymentRepository paymentRepository;

  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Payment createPayment(final PaymentMethod paymentMethod) {
    return Payment.builder()
        .paymentStatus(PaymentStatus.UNCOMPLETED.getValue())
        .paymentMethod(paymentMethod.getValue())
        .build();
  }

  public Payment save(final Payment payment) {
    return paymentRepository.save(payment);
  }

  public void cancelPayment(Long id) {
    paymentRepository.deleteById(id);
  }
}
