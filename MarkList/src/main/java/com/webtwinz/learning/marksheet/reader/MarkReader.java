package com.webtwinz.learning.marksheet.reader;

import com.webtwinz.learning.marksheet.common.Subject;
import java.util.Map;

public interface MarkReader {

  Map<Subject, Float> read();
}

