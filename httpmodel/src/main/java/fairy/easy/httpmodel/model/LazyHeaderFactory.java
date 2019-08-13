package fairy.easy.httpmodel.model;

import android.support.annotation.Nullable;


public interface LazyHeaderFactory {

  @Nullable
  String buildHeader();
}
