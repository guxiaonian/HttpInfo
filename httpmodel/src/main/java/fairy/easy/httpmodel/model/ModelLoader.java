package fairy.easy.httpmodel.model;


public interface ModelLoader<T> {

    interface DataCallback<T> {

        void onDataReady( T data);


        void onLoadFailed( Exception e);
    }

    void loadData( DataCallback<? super T> callback);

    void cleanup();

    void cancel();

    ModelLoader<T> setHttpModel(HttpModel httpModel);


    ModelLoader<T> setTimeout(int timeout);

    Class<T> getDataClass();

}
