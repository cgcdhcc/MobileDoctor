package com.imedical.mobiledoctor.util;
/**
 * 通用的回调接口
 * @author sszvip@qq.com
 * @since  2014-7-2
 * @param <T> 返回的参数类型
 */
public interface MyCallback<T> {

	void onCallback(T value);
}
