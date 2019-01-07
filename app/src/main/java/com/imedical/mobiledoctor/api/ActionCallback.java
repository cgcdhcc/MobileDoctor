package com.imedical.mobiledoctor.api;

/**
 * 回调使用
 *
 * @author sszvip
 */
public interface ActionCallback<T> {

    public void onSelected(T value);

    public void onDeleted(T value);

}
