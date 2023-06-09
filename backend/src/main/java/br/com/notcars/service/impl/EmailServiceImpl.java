package br.com.notcars.service.impl;

import br.com.notcars.constant.EmailRegistrationConstant;
import br.com.notcars.constant.EmailReservationConstant;
import br.com.notcars.model.ReservationEntity;
import br.com.notcars.service.EmailService;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Future;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;


  @Async("taskExecutor")
  public Future<String> sendEmail(String addressee, String subject, String content) throws MessagingException {
    log.info("start send email async");
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setFrom("dh.notcars@gmail.com");
    helper.setTo(addressee);
    helper.setSubject(subject);
    helper.setText(content, true);
    mailSender.send(message);
    log.info("finish send email async");
    return new AsyncResult<>("Email enviado");
  }

  public String registrationEmail(String name) {
    String fileContent = EmailRegistrationConstant.registration;
    return fileContent.replace("{user}", name.toUpperCase());
  }


  public String reservationEmail(String name, ReservationEntity reservation) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fileContent = EmailReservationConstant.reservation;
    fileContent = fileContent.replace("{user}", name.toUpperCase());
    fileContent = fileContent.replace("{hour}", reservation.getHourStartReservation().toString());
    fileContent = fileContent.replace("{dateBegin}", reservation.getDateBegin().format(formatters));
    fileContent = fileContent.replace("{dateEnd}", reservation.getDateEnd().format(formatters));
    fileContent = fileContent.replace("{city}", reservation.getProduct().getCity().getName());
    fileContent = fileContent.replace("{product}", reservation.getProduct().getName());
    return fileContent;
  }


}
