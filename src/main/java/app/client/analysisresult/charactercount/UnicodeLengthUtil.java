package app.client.analysisresult.charactercount;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeLengthUtil {

  /**
   * This method calculate length of string with unicode code points. So no matter what language is used, the character count will be correct for that language
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static int getLength(String string) {
    if (string == null) {
      return 0;
    }
    Pattern pat = Pattern.compile("\\p{L}\\p{M}*");
    AtomicInteger count = new AtomicInteger();
    Matcher matcher = pat.matcher(string);
    while (matcher.find()) {
      matcher.group();
      count.incrementAndGet();
    }
    return count.get();
  }
}
