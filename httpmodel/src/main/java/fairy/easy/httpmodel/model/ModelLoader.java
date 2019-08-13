package fairy.easy.httpmodel.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ModelLoader<T> {

    interface DataCallback<T> {

        void onDataReady(@Nullable T data);


        void onLoadFailed(@NonNull Exception e);
    }

    void loadData(@NonNull DataCallback<? super T> callback);

    void cleanup();

    void cancel();

    ModelLoader<T> setHttpModel(HttpModel httpModel);


    ModelLoader<T> setTimeout(int timeout);

    @NonNull
    Class<T> getDataClass();

}
