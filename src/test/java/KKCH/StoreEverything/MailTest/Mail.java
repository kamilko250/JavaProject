package KKCH.StoreEverything.MailTest;

import KKCH.StoreEverything.EmailSender.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Properties;

public class Mail {


   @Test
   public void MailTest() throws MessagingException {
       JavaMailSenderImpl sender = new JavaMailSenderImpl();
       sender.setHost("smtp.gmail.com");
       sender.setPort(587);

       sender.setUsername("projekt.java123@gmail.com");
       sender.setPassword("ProjektJava123!");
       MailService mailService = new MailService(sender);

       Properties props = sender.getJavaMailProperties();
       props.put("mail.transport.protocol", "smtp");
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.debug", "true");

       String mail = "dedipe3228@nzaif.com";
        mailService.sendMail(mail, "Registration", "Link to registration", false);
   }
}
