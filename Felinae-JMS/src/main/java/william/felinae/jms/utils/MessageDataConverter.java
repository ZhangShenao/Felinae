package william.felinae.jms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/11/13 10:05
 * @Description:
 */
public class MessageDataConverter {
    private static final Logger logger = LoggerFactory.getLogger(MessageDataConverter.class);

    public static String extractJsonDataFromStompFrame(Message message){
        try {
            if (message instanceof BytesMessage) {
                BytesMessage byteMessage = (BytesMessage)message;
                long messageBodyLength = byteMessage.getBodyLength();
                byte[] byteData = new byte[(int) messageBodyLength];
                byteMessage.readBytes(byteData);
                return new String(byteData);
            }

            if (message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;
                return textMessage.getText();
            }

            logger.error("Unsupported Message Type For Stomp Frame!!");
        } catch (Exception e) {
            logger.error("Extract Json Data From Stomp Frame Error!!", e);
        }
        return "";
    }
}
