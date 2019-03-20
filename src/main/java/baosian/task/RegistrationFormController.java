package baosian.task;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


@Slf4j
@Controller
@RequestMapping("/user")
public class RegistrationFormController {

    @GetMapping
    public String registrationForm(Model model){
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping
    public String processUser(@Valid User user, Errors errors) {
        if (errors.hasErrors()){
            return "/registration";
        }
        String refferenceForAuthentication = String.valueOf(user.getEmail().hashCode());
        user.setCreated_at(new Date());
        Date dateOfRegistration = user.getCreated_at();
        sendRefferenceToUserEmail(user.getEmail(), refferenceForAuthentication, dateOfRegistration);
        log.info("User is created" + user);
        return "redirect:/";
    }

    private void sendRefferenceToUserEmail(String email, String refferenceForAuthentication,
                                           Date dateOfRegistration) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateOfRegistration);
        c.add(Calendar.DATE, 1);
        //JavaMailSenderImpl sender = new JavaMailSenderImpl();


        Properties properties = System.getProperties();

        //properties.setProperty("mail.smtp.host", "localhost");
        //properties.setProperty("mail.smtp.port", "8080");
        properties.setProperty("mail.smtp.host", "aspmx.l.google.com");
        //properties.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");
        //properties.setProperty("mail.smtp.socketFactory.fallback", "true");
        //properties.put("mail.smtp.user", "a7500543@gmail.com");
        //properties.put("mail.smtp.password", "asdf17061981asdf");
        properties.setProperty("mail.smtp.port", "25");
        properties.setProperty("mail.smtp.socketFactory.port", "25");
        //properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.transport.protocol", "smtp");
        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);
        try {
            //message.setFrom(new InternetAddress("a7500543@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Подтверждение заявки на регистрацию");
            message.setText("Для завершения регистрации перейдите по ссылке: \n" + refferenceForAuthentication +
                                "\n Ссылка действительна до " + c.getTime());
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //MimeMessageHelper helper = new MimeMessageHelper(message);
        //try {
        //    helper.setTo(new );
        //    helper.setText("Для завершения регистрации перейдите по ссылке: \n" + refferenceForAuthentication +
        //            "\n Ссылка действительна до " + c.getTime());
        //
        //} catch (MessagingException e) {
        //    e.printStackTrace();
        //}

    }
}
