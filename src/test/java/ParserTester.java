import app.server.parser.WhatsappDefaultMessageParser;
import java.io.File;
import org.junit.Test;

public class ParserTester {

  @Test
  public void testParser() throws Exception {
    WhatsappDefaultMessageParser parser = new WhatsappDefaultMessageParser();
    parser.parseFile(new File("/home/afsal/test_chat.txt"));
  }
}
