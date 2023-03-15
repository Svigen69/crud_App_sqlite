package com.fassi.vorwerkApp.core;

public abstract class DialogResult<T> {
    public abstract void onAccept(T result );
    public abstract void onCancel();
}
