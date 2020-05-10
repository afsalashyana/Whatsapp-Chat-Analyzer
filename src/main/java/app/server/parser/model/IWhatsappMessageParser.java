package app.server.parser.model;

import app.server.parser.exception.UnsupportedExportFileException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IWhatsappMessageParser {

  String getRegex();

  List<WhatsappMessage> parseFile(File file) throws IOException, UnsupportedExportFileException;
}
