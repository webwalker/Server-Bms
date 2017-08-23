package bms.core.mail;

public interface MailSender {

    void addCc(String[] cc);

    void addBcc(String[] bcc);

    void addAttachment(String path);

    void send();
}
