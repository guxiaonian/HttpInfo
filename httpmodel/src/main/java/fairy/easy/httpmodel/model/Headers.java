package fairy.easy.httpmodel.model;

import java.util.Collections;
import java.util.Map;


public interface Headers {

  Headers NONE = new Headers() {
      @Override
      public Map<String, String> getHeaders() {
          return Collections.emptyMap();
      }
  };


  Headers DEFAULT = new LazyHeaders.Builder().build();


  Map<String, String> getHeaders();
}
