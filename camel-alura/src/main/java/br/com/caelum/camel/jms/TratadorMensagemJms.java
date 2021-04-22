package br.com.caelum.camel.jms;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Guilherme Alves Silveira
 */
public class TratadorMensagemJms implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            final Destination replyTo = message.getJMSReplyTo();
            final Enumeration enumeration = message.getPropertyNames();

            System.out.println("replyTo: " + replyTo);
            while (enumeration.hasMoreElements()) {
                Object element = enumeration.nextElement();
                System.out.println("element: " + element);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
