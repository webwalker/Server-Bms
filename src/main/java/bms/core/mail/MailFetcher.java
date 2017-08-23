package bms.core.mail;

import java.util.List;

public interface MailFetcher {

    List<MailInfo> fetch(int count);

    MailInfo fetchLatest();
}
